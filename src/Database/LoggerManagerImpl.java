/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import User.Official;
import User.Voter;
import User.Candidate;
import User.User;
import static Application.ElectionApplication.convertSQLtoGregorian;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class is used to do operations from the gui, it connects to the database and requests / writes to the database freely using the methods provided.
 * @author Keke
 * 
 */
public class LoggerManagerImpl implements LoggerManager {
    
    private Database data;
    
    public LoggerManagerImpl(){
        data=new Database();
    }
    
    /**  Takes as parameters 2 strings : email and password, and checks if they exist in the database. 
    If they do, it checks whether it's a candidate / voter / official and returns a new instance of the class
     * @param email
     * @param password
     * @return  */
    
    @Override
    public User loggingCheck(String email, String password){
        try (Connection con = data.establishConnection()) {
            
                PreparedStatement stm = con.prepareStatement("SELECT * FROM user where email=? AND password=?");
                stm.setString(1, email);
                stm.setString(2, password);
                
                try (ResultSet user = stm.executeQuery()) {
                    if (user.next())
                    {
                        int userID=user.getInt(2);
                        
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
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
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
        try (Connection con = data.establishConnection())
        {
            Statement stm=con.createStatement();
            try (ResultSet user = stm.executeQuery("SELECT * FROM user where UserID=" + userID)) 
            {
                        if (user.next())
                        {
                            String userEmail=user.getString(1), userPassword=user.getString(3), userFirstName=user.getString(5), userLastName=user.getString(6);
                            GregorianCalendar userDOB=convertSQLtoGregorian(user.getString(4));
                            try (ResultSet testCandidate = stm.executeQuery("SELECT politicalParty, description FROM candidate WHERE CandidateID="+ userID)) 
                            {
                                    if (testCandidate.next())
                                    {
                                        String userPoliticalParty=testCandidate.getString(1), userDescription=testCandidate.getString(2);
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

    try (Connection con = data.establishConnection()) {
            Statement stm=con.createStatement();
            try (ResultSet user = stm.executeQuery("SELECT * FROM user where UserID=" + userID)) {
                        if (user.next())
                        {
                            String userEmail=user.getString(1), userPassword=user.getString(3), userFirstName=user.getString(5), userLastName=user.getString(6);
                            GregorianCalendar userDOB=convertSQLtoGregorian(user.getString(4));
                            
                            try (ResultSet testVoter = stm.executeQuery("SELECT state, votedFor FROM voter WHERE VoterID="+ userID)) 
                            {
                                    if (testVoter.next())
                                    {
                                        String userState=testVoter.getString(1);
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
        try (Connection con = data.establishConnection()) 
         {
            Statement stm=con.createStatement();
            try (ResultSet user = stm.executeQuery("SELECT * FROM user where UserID=" + userID)) 
            {
                        if (user.next())
                        {
                            String userEmail=user.getString(1), userPassword=user.getString(3), userFirstName=user.getString(5), userLastName=user.getString(6);
                            GregorianCalendar userDOB=convertSQLtoGregorian(user.getString(4));
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