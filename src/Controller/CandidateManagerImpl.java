/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class is used to do operations from a candidate, it connects to the database and requests / writes to the database freely using the methods provided.
 * @author rebec
 */
public class CandidateManagerImpl implements CandidateManager{
    
    private Database data ; 
    
    private static final String QUERY_ELECTION_ISOPEN = "SELECT Status FROM status ORDER BY StateID DESC"; 
    private static final String QUERY_UPDATE_CANDIDATE = "UPDATE candidate, user SET user.email = ? , user.password = ? WHERE CandidateID = UserID AND UserId = ?"; 
    private static final String QUERY_FINDID = "SELECT UserID FROM user WHERE email = ?"; 
    private static final String QUERY_GET_ALL_CANDIDATE_INFOS = "SELECT user.firstName, user.lastName, user.email FROM candidate, user WHERE CandidateID = UserID"; 
    private static final String QUERY_GET_VOTES_CANDIDATE = "SELECT state, count(votedFor) FROM user JOIN candidate ON UserID = CandidateID JOIN voter ON votedFor = CandidateID WHERE email = ? GROUP BY votedFor, state"; 
    private static final String QUERY_IMAGE_CANDIDATE = "SELECT picture FROM candidate, user WHERE CandidateID=UserID AND email=?";
    
    private PreparedStatement electionOpen ;
    private PreparedStatement getID ; 
    private PreparedStatement updateCandidate;
    private PreparedStatement getAllCandidateInfos ; 
    private PreparedStatement getVotesCandidate ; 
    private PreparedStatement imageOfCandidate;
    
    
    public CandidateManagerImpl() throws SQLException, ClassNotFoundException
    {
        data = new Database() ; 
        Connection dbConnection = data.getCon();
        electionOpen = dbConnection.prepareStatement(QUERY_ELECTION_ISOPEN);
        getID = dbConnection.prepareStatement(QUERY_FINDID); 
        updateCandidate = dbConnection.prepareStatement(QUERY_UPDATE_CANDIDATE);
        getAllCandidateInfos = dbConnection.prepareStatement(QUERY_GET_ALL_CANDIDATE_INFOS);
        getVotesCandidate = dbConnection.prepareStatement(QUERY_GET_VOTES_CANDIDATE);
        imageOfCandidate = dbConnection.prepareStatement(QUERY_IMAGE_CANDIDATE);
    }
    
    /** 
     * Get the last state of the election. 
     * @return 
     */
    @Override
    public String electionIsOpen()
    {
        try 
        {
            ResultSet status = electionOpen.executeQuery(); 
            if(status.next())
                return status.getString(1); 
        } 
        catch (SQLException ex) {
            Logger.getLogger(voterManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return " " ;
    }
    
    /** 
     * Update in the database with new informations
     * @param infos
     * @param email
     * @return 
     */
    @Override
    public boolean updateCandidate(String[] infos, String email)
    {
        try {
            getID.setString(1, email);
            ResultSet userID = getID.executeQuery(); 
            if(userID.next())
            {
                updateCandidate.setString(1, infos[0]);
                updateCandidate.setString(2, infos[1]);
                updateCandidate.setString(3, userID.getString(1));
                updateCandidate.executeUpdate(); 
                return true; 
            }
        } catch (SQLException ex) {
            Logger.getLogger(voterManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false ;
    }
    
    /**
     * Return the name and email of all candidates, unless the candidate login. 
     * @param logEmail
     * @return 
     */
    @Override
    public ArrayList<String[]> getAllCandidates(String logEmail)
    {
        try
        {
            ArrayList<String[]> candidatesInfos = new ArrayList();
            String [] buf = new String[2];
            buf[0] = " "; 
            buf[1] = " "; 
            candidatesInfos.add(buf); 
            ResultSet infos = getAllCandidateInfos.executeQuery(); 
            while(infos.next())
            {
                String [] buffer = new String[2] ; 
                if(!infos.getString("user.email").equals(logEmail))
                {
                    buffer[0] = infos.getString("user.firstName") + " " + infos.getString("user.lastName"); 
                    buffer[1] = infos.getString("user.email"); 
                    candidatesInfos.add(buffer);
                }
            }
            return candidatesInfos ; 
        } catch (SQLException ex) {
            Logger.getLogger(voterManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Return all the votes per states for the candidate log in and the candidate he wants to compare with
     * @param logEmail
     * @param compareEmail
     * @return 
     */
    @Override
    @SuppressWarnings("empty-statement")
    public String[][] getVotesByStates(String logEmail, String compareEmail)
    {
        try
        {
            String [][] infos = new String[][] {{""+0,""+0,""+0,""+0,""+0,""+0,""+0,""+0},{""+0,""+0,""+0,""+0,""+0,""+0,""+0,""+0}};
            
            getVotesCandidate.setString(1, logEmail); 
            ResultSet infosCandidate = getVotesCandidate.executeQuery(); 
            
            while(infosCandidate.next())
            {
                String count = infosCandidate.getString("count(votedFor)");
                switch (infosCandidate.getString("state")) {
                    case "Padokea":
                        infos[0][0] = count;
                        break;
                    case "Heaven's Arena":
                        infos[0][1] = count;
                        break;
                    case "Kukan'yu":
                        infos[0][2] = count;
                        break;
                    case "Saherta":
                        infos[0][3] = count;
                        break;
                    case "Yorbia":
                        infos[0][4] = count;
                        break;
                    case "Begerosse":
                        infos[0][5] = count;
                        break;
                    case "Kakin":
                        infos[0][6] = count;
                        break;
                    case "Ochima":
                        infos[0][7] = count;
                        break;
                }
            }
            
            getVotesCandidate.setString(1, compareEmail); 
            infosCandidate = getVotesCandidate.executeQuery(); 
            
            while (infosCandidate.next()) {
                String count = infosCandidate.getString("count(votedFor)");
                switch (infosCandidate.getString("state")) {
                    case "Padokea":
                        infos[1][0] = count;
                        break;
                    case "Heaven's Arena":
                        infos[1][1] = count;
                        break;
                    case "Kukan'yu":
                        infos[1][2] = count;
                        break;
                    case "Saherta":
                        infos[1][3] = count;
                        break;
                    case "Yorbia":
                        infos[1][4] = count;
                        break;
                    case "Begerosse":
                        infos[1][5] = count;
                        break;
                    case "Kakin":
                        infos[1][6] = count;
                        break;
                    case "Ochima":
                        infos[1][7] = count;
                        break;
                }
            }
            
            return infos; 
        }
        catch (SQLException ex) {
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
}
