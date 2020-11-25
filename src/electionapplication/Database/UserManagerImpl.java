/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionapplication.Database;

import electionapplication.User.Candidate;
import electionapplication.User.Voter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class is used to do operations from an official, it connects to the database and requests / writes to the database freely using the methods provided.
 * @author Keke
 * 
 */
public class UserManagerImpl implements UserManager {
   
    private Database data;
    
    public UserManagerImpl(){
        data=new Database();
    }
    
    /** Using a voter, can create a user entry in the database, returns true if it worked, false if it didn't
     * @param v
     * @return 
     */
    
    @Override
    public boolean insertVoter(Voter v) {
        try (Connection con = data.establishConnection()) {
                Statement stm=con.createStatement();
                stm.executeUpdate("INSERT INTO user (email, UserID, password, dateOfBirth, firstName, lastName) "
                  + "VALUES ('"+v.getEmail()+"', NULL, '"+v.getPassword()+"', '"+v.getSQLdate()+"', '"+v.getFirstName()+"', '"+v.getLastName()+"')");
                
                ResultSet userID=stm.executeQuery("SELECT UserID FROM user WHERE email LIKE '" + v.getEmail() + "'"); 
                if (userID.next())
                {
                    stm.executeUpdate("INSERT INTO voter (VoterID, state, votedFor) VALUES ('" + userID.getInt(1) + "', \""+ v.getState() +"\", NULL)");
                }
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
        try (Connection con = data.establishConnection()) {
                Statement stm=con.createStatement();
                stm.executeUpdate("DELETE FROM user WHERE email LIKE '" + email + "'"); 
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
        
        try (Connection con = data.establishConnection()) {
                
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
    
    /** Using a candidate, can create a user entry in the database, returns true if it worked, false if it didn't
     * @param c
     * @return 
     */
    @Override
    public boolean insertCandidate(Candidate c) {
        try (Connection con = data.establishConnection()) {
                Statement stm=con.createStatement();
                stm.executeUpdate("INSERT INTO user (email, UserID, password, dateOfBirth, firstName, lastName) "
                  + "VALUES ('"+c.getEmail()+"', NULL, '"+c.getPassword()+"', '"+c.getSQLdate()+"', '"+c.getFirstName()+"', '"+c.getLastName()+"')");
                
                ResultSet userID=stm.executeQuery("SELECT UserID FROM user WHERE email LIKE '" + c.getEmail() + "'"); 
                if (userID.next())
                {
                    stm.executeUpdate("INSERT INTO candidate (CandidateID, politicalParty, description) VALUES ('" + userID.getInt(1) + "', \""+ c.getParty() +"\",'"+ c.getDescription() +"')");
                }
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
        try (Connection con = data.establishConnection()) {
                Statement stm=con.createStatement();
                stm.executeUpdate("UPDATE user SET email='" + v.getEmail() + "', password = '"+ v.getPassword() 
                        + "', dateOfBirth ='"+ v.getSQLdate() + "', firstName ='"+ v.getFirstName() + "', lastName ='"+ v.getLastName() +"' WHERE email='" + lastEmail + "'");
                ResultSet userID=stm.executeQuery("SELECT UserID FROM user WHERE email LIKE '" + v.getEmail() + "'"); 
                if (userID.next())
                {
                    stm.executeUpdate("UPDATE voter SET state =\""+ v.getState() + "\" WHERE VoterID =" + userID.getInt(1));
                }
                return true;
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
   
}
