/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author rebec
 */
public class voterManagerImpl implements voterManager{
    
    private Database data ; 
    
    private static final String QUERY_GET_CANDIDATE = "SELECT user.email, user.firstName, user.lastName, candidate.politicalParty, candidate.description FROM user, candidate WHERE UserID = CandidateID";
    private static final String QUERY_UPDATE_VOTER = "UPDATE voter, user SET voter.state = ? , user.email = ? , user.password = ? WHERE VoterID = UserID AND VoterId = ?"; 
    private static final String QUERY_FINDID = "SELECT UserID FROM user WHERE email = ?"; 
    private static final String QUERY_UPDATE_VOTE = "UPDATE voter SET votedFor = ? WHERE VoterID = ?"; 
    private static final String QUERY_ELECTION_ISOPEN = "SELECT Status, MAX(StateID) FROM status"; 
    private static final String QUERY_NUMBER_CANDIDATE  = "SELECT COUNT(*) FROM candidate"; 
    private static final String QUERY_IMAGE_CANDIDATE = "SELECT picture FROM candidate, user WHERE CandidateID=UserID AND email=?";
    
    private PreparedStatement getCandidate ; 
    private PreparedStatement getID ; 
    private PreparedStatement updateVoter;
    private PreparedStatement updateVote ; 
    private PreparedStatement electionOpen ; 
    private PreparedStatement numberOfCandidate;
    private PreparedStatement imageOfCandidate;
    
    public voterManagerImpl() throws SQLException, ClassNotFoundException
    {
        data = new Database() ; 
        Connection dbConnection = data.getCon();
        getCandidate = dbConnection.prepareStatement(QUERY_GET_CANDIDATE); 
        getID = dbConnection.prepareStatement(QUERY_FINDID); 
        updateVoter = dbConnection.prepareStatement(QUERY_UPDATE_VOTER);
        updateVote = dbConnection.prepareStatement(QUERY_UPDATE_VOTE);
        electionOpen = dbConnection.prepareStatement(QUERY_ELECTION_ISOPEN);
        numberOfCandidate = dbConnection.prepareStatement(QUERY_NUMBER_CANDIDATE);
        imageOfCandidate = dbConnection.prepareStatement(QUERY_IMAGE_CANDIDATE);
    }
    
    public static GregorianCalendar convertSQLtoGregorian(String SQLdate){
      return new GregorianCalendar(Integer.parseInt(SQLdate.substring(0, 4)), Integer.parseInt(SQLdate.substring(5, 7)), Integer.parseInt(SQLdate.substring(8)));
    }
    
    @Override
    public String[][] getAllCandidate()
    {
        try {
            ResultSet numberOfCandidates = numberOfCandidate.executeQuery(); 
            
            if(numberOfCandidates.next())
            {
                int nbCandidate = numberOfCandidates.getInt(1); 
                
                String [][] infosCandidates = new String[nbCandidate][5]; 
                ResultSet infos = getCandidate.executeQuery(); 
                int i = 0 ;
                while(infos.next())
                {
                    for (int k=1 ; k < infosCandidates[i].length+1 ; ++k)
                        infosCandidates[i][k-1]=infos.getString(k);
                    ++i;
                }
                    return infosCandidates;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(voterManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }
    
    /**
     * Used to get the picture of a candidate in the database using his email
     * @param email
     * @return 
     */
    @Override
    public byte[] getPicture(String email)
    {
        try {
            imageOfCandidate.setString(1, email);
            ResultSet i = imageOfCandidate.executeQuery(); 
            
            if(i.next())
            {
                 byte[] img = i.getBytes("picture");
                 return img;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(voterManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }
    
    /**
     *
     * @param emailCandidate
     * @param email   * @return
     * @return 
     */
    @Override
    public boolean updateVote(String emailCandidate, String email)
    {
        try {
            getID.setString(1, emailCandidate); 
            ResultSet candidateID = getID.executeQuery(); 
            if(candidateID.next())
            { 
                updateVote.setInt(1, candidateID.getInt(1));
                
                getID.setString(1, email);
                ResultSet userID = getID.executeQuery();
                if(userID.next())
                {
                    updateVote.setInt(2, userID.getInt(1));
                    
                    updateVote.executeUpdate(); 
                    return true; 
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(voterManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false ; 
    }
    
    @Override
    public boolean updateVoter(String [] infos, String old_email)
    {
        
       try {
            getID.setString(1, old_email);
            ResultSet userID = getID.executeQuery(); 
            if(userID.next())
            {
                
                updateVoter.setString(1, infos[0]);
                updateVoter.setString(2, infos[1]);
                updateVoter.setString(3, infos[2]);
                updateVoter.setString(4, userID.getString(1));
                updateVoter.executeUpdate(); 
                return true; 
            }
        } catch (SQLException ex) {
            Logger.getLogger(voterManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false ; 
    }
     
    @Override
    public boolean electionIsOpen()
    {
        try 
        {
            ResultSet status = electionOpen.executeQuery(); 
            if(status.next())
                return status.getString("Status").equals("Active"); 
        } catch (SQLException ex) {
            Logger.getLogger(voterManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false ; 
    }
}
