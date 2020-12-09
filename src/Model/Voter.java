/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.voterManagerImpl;
import java.sql.SQLException;

/**
 * Self-Explanatory.
 * @author Keke
 */
public class Voter extends User {
    
    private String state;
    private boolean votedFor;
    private voterManagerImpl dataController ; 

    public Voter(String email, String password, String dateOfBirth, String firstName, String lastName, String state, Candidate votedFor) {
        super(email, password, dateOfBirth, firstName, lastName);
        this.state=state;
        if(votedFor != null)
            this.votedFor = true ;
        else this.votedFor = false ; 
    }
    
    /**
     * Set the data controller of Candidate, made possible the link with a database. 
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public void setDataController() throws SQLException, ClassNotFoundException
    {
        this.dataController = new voterManagerImpl(); 
    }
    
    /**
     * Return the state of the voter
     * @return 
     */
    public String getState() {
        return state;
    }
    
    /**
     * Return true if the voter have already voted, false in other case
     * @return 
     */
    public boolean getVotedFor() {
        return votedFor;
    }
    
    /**
     * Return the infos of all the candidates
     * @return 
     */
    public String[][] getAllCandidate()
    {
        return dataController.getAllCandidate(); 
    }
    
    /**
     * Set the vote of the voter on a candidate in the database, and modify the instance
     * @param emailCandidate
     * @return 
     */
    public boolean vote(String emailCandidate)
    {
        if(votedFor == false || dataController.electionIsOpen())
        {
            if(dataController.updateVote(emailCandidate, super.getEmail())); 
            {
                votedFor = true; 
                return true ; 
            }
        }
        return false ; 
    }
    
    /**
     * Modify the profile in the database and here
     * @param infos
     * @return 
     */
    public boolean updateProfile(String[] infos)
    {
        if(dataController.updateVoter(infos, email))
        {
            this.state = infos[0];
            this.email = infos[1]; 
            this.password = infos[2];
            return true; 
        }
        return false ; 
    }
    
    /**
     * Return the image of an instance of the database
     * @param email
     * @return 
     */
    public byte[] getImage(String email)
    {
        return dataController.getPicture(email);
    }
    
    /**
     * Return true if the election open, false in other case
     * @return 
     */
    public boolean isElectionOpen()
    {
        return dataController.electionIsOpen();
    }
}
