/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import User.Candidate;
import User.Voter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class is used to do operations from an official, it connects to the database and requests / writes to the database freely using the methods provided.
 * @author Keke
 * 
 */
public class UserManagerImpl implements UserManager {
   
    private Database data;
    
    public UserManagerImpl() throws SQLException, ClassNotFoundException{
        data=new Database();
    }
    
    /** Using a voter, can create a user entry in the database, returns true if it worked, false if it didn't
     * @param v
     * @return 
     */
    
    @Override
    public boolean insertVoter(Voter v) {
        try (Connection con = data.getCon()) {
            
                //Preparing statement for user DB
                PreparedStatement userStm=con.prepareStatement("INSERT INTO user (email, UserID, password, dateOfBirth, firstName, lastName) VALUES (?, null, ?, ?, ?, ?)");
                userStm.setString(1, v.getEmail());
                userStm.setString(2, v.getPassword());
                userStm.setString(3, v.getSQLdate());
                userStm.setString(4, v.getFirstName());
                userStm.setString(5, v.getLastName());
                
                //Preparing statement for candidate DB
                PreparedStatement voterStm=con.prepareStatement("INSERT INTO voter (VoterID, state, votedFor) VALUES(LAST_INSERT_ID(), ?, null)");
                voterStm.setString(1, v.getState());
                
                //Doing updates in the DB
                userStm.executeUpdate();
                voterStm.executeUpdate();
                return true;
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }

    /** Takes a mail as parameter and delete an instance in the database no matter what type of user it is.
     * @param email
     * @return  */
    @Override
    public boolean deleteUser(String email) {
        try (Connection con = data.getCon()) {
                PreparedStatement stm=con.prepareStatement("DELETE FROM user WHERE email=?");
                stm.setString(1, email);
                stm.executeUpdate(); 
                return true;
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
        
    }
    
    /** Returns all voters of the database as a two-dimensional array of Strings containing every data about them
     * @return  */
    @Override
    public String[][] getAllVoters() {
        
        try (Connection con = data.getCon()) {
                
                int i=0;
                Statement stm=con.createStatement();
                
                ResultSet voter = stm.executeQuery("SELECT COUNT(*) FROM voter");
                if (voter.next())
                {
                    String[][] infos = new String[voter.getInt(1)][6];  

                    voter = stm.executeQuery("SELECT user.firstName, user.lastName, user.email, user.password, user.dateOfBirth, voter.state FROM user, voter WHERE UserID=VoterID");

                    while(voter.next())
                    {
                        for (int k=1 ; k < infos[i].length+1 ; k++)
                            infos[i][k-1]=voter.getString(k);
                        ++i;
                    }
                    return infos;
                }
                
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }
    
    
    /** Returns all candidates of the database as a two-dimensional array of Strings containing every data about them
     * @return  */
    @Override
    public String[][] getAllCandidates() {
        
        try (Connection con = data.getCon()) {
                
                int i=0;
                Statement stm=con.createStatement();
                
                ResultSet candidate = stm.executeQuery("SELECT COUNT(*) FROM candidate");
                if (candidate.next())
                {
                    String[][] infos = new String[candidate.getInt(1)][7];  

                    candidate = stm.executeQuery("SELECT user.firstName, user.lastName, user.email, user.password, user.dateOfBirth, candidate.politicalParty, candidate.description FROM user, candidate WHERE UserID=CandidateID");

                    while(candidate.next())
                    {
                        for (int k=1 ; k < infos[i].length+1 ; ++k)
                            infos[i][k-1]=candidate.getString(k);
                        ++i;
                    }
                    return infos;
                }
                
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }
    
    /** Using a candidate, can create a user entry in the database, returns true if it worked, false if it didn't
     * @param c
     * @return 
     */
    @Override
    public boolean insertCandidate(Candidate c) {
        try (Connection con = data.getCon()) {
            
                //Preparing statement for user DB
                PreparedStatement userStm=con.prepareStatement("INSERT INTO user (email, UserID, password, dateOfBirth, firstName, lastName) VALUES (?, null, ?, ?, ?, ?)");
                userStm.setString(1, c.getEmail());
                userStm.setString(2, c.getPassword());
                userStm.setString(3, c.getSQLdate());
                userStm.setString(4, c.getFirstName());
                userStm.setString(5, c.getLastName());
                
                //Preparing statement for candidate DB
                PreparedStatement candidateStm=con.prepareStatement("INSERT INTO candidate (CandidateID, politicalParty, description) VALUES(LAST_INSERT_ID(), ?, ?)");
                candidateStm.setString(1, c.getParty());
                candidateStm.setString(2, c.getDescription());
                
                //Doing updates in the DB
                userStm.executeUpdate();
                candidateStm.executeUpdate();
                return true;
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }

    /** Using a voter and the last known email, can modify a user entry in the database, returns true if it worked, false if it didn't
     * @param v
     * @param lastEmail
     * @return 
     */
    @Override
    public boolean modifyVoter(Voter v, String lastEmail) {
        try (Connection con = data.getCon()) {
            
                //Preparing statement for update
               PreparedStatement userStm=con.prepareStatement("UPDATE user, voter SET email=?, password=?, dateOfBirth=?, firstName=?, lastName=?, state = ? "
                        + "WHERE email=? AND UserID=VoterID");
                userStm.setString(1, v.getEmail());
                userStm.setString(2, v.getPassword());
                userStm.setString(3, v.getSQLdate());
                userStm.setString(4, v.getFirstName());
                userStm.setString(5, v.getLastName());
                userStm.setString(6, v.getState());
                userStm.setString(7, lastEmail);
                userStm.executeUpdate();
                return true;
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
    
    /** Using a candidate and the last known email, can modify a user entry in the database, returns true if it worked, false if it didn't
     * @param c
     * @param lastEmail
     * @return 
     */
    @Override
    public boolean modifyCandidate(Candidate c, String lastEmail) {
        try (Connection con = data.getCon()) {
                PreparedStatement userStm=con.prepareStatement("UPDATE user, candidate SET email=?, password=?, dateOfBirth=?, firstName=?, lastName=?, politicalParty = ? , description = ? "
                        + "WHERE user.email=? AND user.UserID=candidate.CandidateID");
                userStm.setString(1, c.getEmail());
                userStm.setString(2, c.getPassword());
                userStm.setString(3, c.getSQLdate());
                userStm.setString(4, c.getFirstName());
                userStm.setString(5, c.getLastName());
                userStm.setString(6, c.getParty());
                userStm.setString(7, c.getDescription());
                userStm.setString(8, lastEmail);
                userStm.executeUpdate();
                return true;
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
    
   /** 
    * Add an entry to the status table with a set status
     * @param status
     * @return 
    */
    @Override
    public boolean addElectionEntry(String status)
    {
        try (Connection con = data.getCon()) {

                Calendar calendar = Calendar.getInstance();
                java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
                PreparedStatement stm=con.prepareStatement("INSERT INTO status (StateID, Date, Status) VALUES (NULL, ?, ?)");
                stm.setString(1,""+ date);
                stm.setString(2, status);
                stm.executeUpdate();
                return true;
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
    
    /**
     *  Returns last status from the database in the form of a string
     * @return
     */
    @Override
    public String getLastStatus()
    {
        try (Connection con = data.getCon()) {
                Statement stm=con.createStatement();
                ResultSet set= stm.executeQuery("SELECT Status FROM status ORDER BY StateID DESC");
                if (set.next())
                    return set.getString(1);
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

    /**
     * Set all votedFor attributes to null in the database
     * @return 
     */
    @Override
    public boolean setVotesToNull() {
        try (Connection con = data.getCon()) {
                Statement stm=con.createStatement();
                stm.executeUpdate("UPDATE voter SET votedFor = null");
                return true;
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
    
    /**
     * We're getting the name and last name of each candidates and the number of votes that have been casted for them 
     * @return 
     */
    
    @Override
    public String[][] getVotes() {
        try (Connection con = data.getCon()) {
            
                String[][] retrievedData;
                int i=0;
                
                PreparedStatement countStm=con.prepareStatement("SELECT COUNT(CandidateID) FROM candidate"); //To get the number of candidates
                PreparedStatement userStm=con.prepareStatement("SELECT user.firstName, user.lastName, user.UserID FROM user, candidate WHERE UserID=CandidateID"); //To get the informations of the candidates
                PreparedStatement votesStm=con.prepareStatement("SELECT COUNT(VoterID) FROM voter, candidate WHERE votedFor=CandidateID AND CandidateID=?"); //To get the number of votes of a candidate
                
                //I get the number of rows to know how many candidates there is
                ResultSet numberOfRows=countStm.executeQuery();
                if (numberOfRows.next())
                {
                    //I create my retrievedData two dimensionnal array with the values it returned
                    retrievedData= new String[numberOfRows.getInt(1)][2];
                    ResultSet votesRetrieval;
                    
                    //Sending a request in order to retrieve the informations of the candidates : first name, last name, UserID
                    ResultSet candidateRetrieval=userStm.executeQuery();
                    while(candidateRetrieval.next())
                    {
                        //I set the first case of my array to firstname + lastname 
                        retrievedData[i][0]=candidateRetrieval.getString("firstName") + " " + candidateRetrieval.getString("lastName");
                        
                        //I look for all the votes of that candidate in the tables 
                        votesStm.setString(1, candidateRetrieval.getString("UserID"));
                        votesRetrieval=votesStm.executeQuery();
                        //I set the number of votes for a candidate inside of the second case of the array
                        if (votesRetrieval.next())
                            retrievedData[i][1]=votesRetrieval.getString(1);
                        //I go on to the next candidate
                        ++i;
                    }
                    candidateRetrieval.close();  
                    return retrievedData;
                }   
                numberOfRows.close();
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }
    
}
