/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Database.voterManagerImpl;
import java.util.GregorianCalendar;

/**
 * Self-Explanatory.
 * @author Keke
 */
public class Voter extends User {
    
    private String state;
    private int votedFor;
    private voterManagerImpl dataController ; 

    public Voter(String email, String password, GregorianCalendar dateOfBirth, String firstName, String lastName, String state, Candidate votedFor) {
        super(email, password, dateOfBirth, firstName, lastName);
        this.state=state;
        this.votedFor=0;        
    }
    
    public String getState() {
        return state;
    }
    
    public int getVotedFor() {
        return votedFor;
    }
    
    public String[][] getAllCandidate()
    {
        return dataController.getAllCandidate(); 
    }
    
    public boolean vote(int candidate)
    {
        if(votedFor == 0 && dataController.electionIsOpen())
        {
            if(dataController.updateVote(candidate, super.getEmail())); 
            {
                votedFor = candidate; 
                return true ; 
            }
        }
        return false ; 
    }
    
    public void updateProdile(String[] infos)
    {
        if(dataController.updateVoter(infos, super.getEmail()))
        {
            state = infos[0];
            email = infos[1]; 
            password = infos[2];
        }
    }
    
}
