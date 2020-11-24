/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionapplication;

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
                    System.out.println(" USER ID : " + userID.getInt(1) + " State : " + v.getState());
                    stm.executeUpdate("INSERT INTO voter (VoterID, state, votedFor) VALUES ('" + userID.getInt(1) + "', \""+ v.getState() +"\", NULL)");
                }
                return true;
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }

    @Override
    public boolean deleteVoter(Voter v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
