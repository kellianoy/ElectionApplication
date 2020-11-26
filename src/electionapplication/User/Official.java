/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionapplication.User;

import electionapplication.Database.UserManagerImpl;
import java.util.GregorianCalendar;

/**
 * Class used as an administrator, has almost all accesses to the database. 
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

     /** 
     * Add a candidate to the database
     * @param c
     * @return 
     */
    public boolean addCandidate(Candidate c) {
        return dataController.insertCandidate(c);
    }
    
     /** 
     * Remove a user from the database using its email
     * @param email
     * @return 
     */
    public boolean removeUser(String email)
    {
        return dataController.deleteUser(email);
    }
    
    /** 
     * Get all voters from the database
     * @return 
     */
    public String[][] getAllVoters(){
        return dataController.getAllVoters();
    }
    
    /** 
     * Get all candidates from the database
     * @return 
     */
    public String[][] getAllCandidates(){
        return dataController.getAllCandidates();
    }
    
    /** 
     * Modify an instance of Voter in the database
     * @param v
     * @param lastEmail
     * @return 
     */
    public boolean modifyVoter(Voter v, String lastEmail)
    {
        return dataController.modifyVoter(v, lastEmail);
    }
    
    /** 
     * Modify an instance of Candidate in the database
     * @param c
     * @param lastEmail
     * @return 
     */
    public boolean modifyCandidate(Candidate c, String lastEmail)
    {
        return dataController.modifyCandidate(c, lastEmail);
    }
}
