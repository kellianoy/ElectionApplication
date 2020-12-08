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
    
    private PreparedStatement electionOpen ;
    private PreparedStatement getID ; 
    private PreparedStatement updateCandidate;
    private PreparedStatement getAllCandidateInfos ; 
    
    
    public CandidateManagerImpl() throws SQLException, ClassNotFoundException
    {
        data = new Database() ; 
        Connection dbConnection = data.getCon();
        electionOpen = dbConnection.prepareStatement(QUERY_ELECTION_ISOPEN);
        getID = dbConnection.prepareStatement(QUERY_FINDID); 
        updateCandidate = dbConnection.prepareStatement(QUERY_UPDATE_CANDIDATE);
        getAllCandidateInfos = dbConnection.prepareStatement(QUERY_GET_ALL_CANDIDATE_INFOS);
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
}
