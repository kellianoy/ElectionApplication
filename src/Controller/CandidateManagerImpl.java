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
    
    private PreparedStatement electionOpen ;
    private PreparedStatement getID ; 
    private PreparedStatement updateCandidate;
    
    
    public CandidateManagerImpl() throws SQLException, ClassNotFoundException
    {
        data = new Database() ; 
        Connection dbConnection = data.getCon();
        electionOpen = dbConnection.prepareStatement(QUERY_ELECTION_ISOPEN);
        getID = dbConnection.prepareStatement(QUERY_FINDID); 
        updateCandidate = dbConnection.prepareStatement(QUERY_UPDATE_CANDIDATE);
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
}
