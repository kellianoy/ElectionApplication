/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionapplication.User;

import electionapplication.Database.UserManagerImpl;
import java.util.GregorianCalendar;

/**
 * Class used as an administrator, has almost all accesses. 
 * @author Keke
 */
public class Official extends User {
    
    UserManagerImpl dataController;
    
    public Official(String email, String password, GregorianCalendar dateOfBirth, String firstName, String lastName) {
        super(email, password, dateOfBirth, firstName, lastName);
        dataController = new UserManagerImpl();
    }
    
    /** 
     * Add a voter to the database
     * @param v
     * @return 
     */
    public boolean addVoter(Voter v){
        return dataController.insertVoter(v);
    }

    public boolean addCandidate(Candidate c) {
        return dataController.insertCandidate(c);
    }
    
    public boolean removeUser(String email)
    {
        return dataController.deleteUser(email);
    }
    
    public String[][] getAllVoters(){
        return dataController.getAllVoters();
    }
    
    public boolean modifyVoter(Voter v, String lastEmail)
    {
        return dataController.modifyVoter(v, lastEmail);
    }
}
