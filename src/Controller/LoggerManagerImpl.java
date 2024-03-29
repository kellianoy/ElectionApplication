/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Official;
import Model.Voter;
import Model.Candidate;
import Model.User;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class is used to do operations from the gui, it connects to the database and requests / writes to the database freely using the methods provided.
 * @author Keke
 * 
 */
public class LoggerManagerImpl implements LoggerManager {
    
    private Database data;
    
    public LoggerManagerImpl() throws SQLException, ClassNotFoundException{
        data=new Database();
    }
    
    /**  Takes as parameters 2 strings : email and password, and checks if they exist in the database. 
    If they do, it checks whether it's a candidate / voter / official and returns a new instance of the class
     * @param email
     * @param password
     * @return  */
    
    @Override
    public User loggingCheck(String email, String password){
        try (Connection con = data.getCon()) {
            
                PreparedStatement stm = con.prepareStatement("SELECT * FROM user where email=? AND password=?");
                stm.setString(1, email);
                stm.setString(2, password);
                
                try (ResultSet user = stm.executeQuery()) {
                    if (user.next())
                    {
                        int userID=user.getInt("UserID");
                        
                        User myUser=retrieveOfficial(userID);
                        if (myUser!=null)
                            return myUser;
                        
                        myUser=retrieveCandidate(userID);
                        if (myUser!=null)
                            return myUser;
                            
                        myUser=retrieveVoter(userID);
                        if (myUser!=null)
                            return myUser; 
                    }
                }
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(OfficialManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

    
    /** Has a int as parameter to look for a Candidate in the database and it creates and new instance of Candidate and returns it
     * @param userID
     * @return 
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException */
    @Override
    public Candidate retrieveCandidate(int userID) throws SQLException, ClassNotFoundException 
    {
        try (Connection con = data.getCon())
        {
            Statement stm=con.createStatement();
            try (ResultSet user = stm.executeQuery("SELECT * FROM user where UserID=" + userID)) 
            {
                        if (user.next())
                        {
                            String userEmail=user.getString("email"), userPassword=user.getString("password"), userFirstName=user.getString("firstName"), userLastName=user.getString("lastName");
                            String userDOB=user.getString("dateOfBirth");
                            try (ResultSet testCandidate = stm.executeQuery("SELECT politicalParty, description FROM candidate WHERE CandidateID="+ userID)) 
                            {
                                    if (testCandidate.next())
                                    {
                                        String userPoliticalParty=testCandidate.getString("politicalParty"), userDescription=testCandidate.getString("description");
                                        return new Candidate(userEmail, userPassword, userDOB, userFirstName, userLastName, userPoliticalParty, userDescription);   
                                    }

                             }    

                        }
            }
        }
        return null;
    }
    
    /** Has a int as parameter to look for a Voter in the database and it creates and new instance of Voter and returns it
     * @param userID
     * @return 
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException */
    @Override
    public Voter retrieveVoter(int userID) throws SQLException, ClassNotFoundException {

    try (Connection con = data.getCon()) {
            Statement stm=con.createStatement();
            try (ResultSet user = stm.executeQuery("SELECT * FROM user where UserID=" + userID)) {
                        if (user.next())
                        {
                            String userEmail=user.getString("email"), userPassword=user.getString("password"), userFirstName=user.getString("firstName"), userLastName=user.getString("lastName");
                            String userDOB=user.getString("dateOfBirth");
                            
                            try (ResultSet testVoter = stm.executeQuery("SELECT state, votedFor FROM voter WHERE VoterID="+ userID)) 
                            {
                                    if (testVoter.next())
                                    {
                                        String userState=testVoter.getString("state");
                                        return new Voter(userEmail, userPassword, userDOB, userFirstName, userLastName, userState, retrieveCandidate(testVoter.getInt(2)));   
                                    }

                             }    

                        }
            }
        }
        return null;
    }

     /** Has a int as parameter to look for an Official in the database and it creates and new instance of Official and returns it
     * @param userID
     * @return 
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException */
    @Override
    public Official retrieveOfficial(int userID) throws SQLException, ClassNotFoundException {
        try (Connection con = data.getCon()) 
         {
            Statement stm=con.createStatement();
            try (ResultSet user = stm.executeQuery("SELECT * FROM user where UserID=" + userID)) 
            {
                        if (user.next())
                        {
                            String userEmail=user.getString("email"), userPassword=user.getString("password"), userFirstName=user.getString("firstName"), userLastName=user.getString("lastName");
                            String userDOB=user.getString("dateOfBirth");
                            try (ResultSet testOfficial = stm.executeQuery("SELECT OfficialID FROM official WHERE OfficialID="+ userID)) 
                            {
                                    if (testOfficial.next())
                                    {
                                         return new Official(userEmail, userPassword, userDOB, userFirstName, userLastName); 
                                    }
                             }                                    
                        }
            }
        }
        return null;
    }
}