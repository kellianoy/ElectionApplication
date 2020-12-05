/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Database.voterManagerImpl;
import java.sql.SQLException;
import java.util.GregorianCalendar;

/**
 * Self-Explanatory.
 * @author Keke
 */
public class Voter extends User {
    
    private String state;
    private boolean votedFor;
    private voterManagerImpl dataController ; 

    public Voter(String email, String password, GregorianCalendar dateOfBirth, String firstName, String lastName, String state, Candidate votedFor) {
        super(email, password, dateOfBirth, firstName, lastName);
        this.state=state;
        if(votedFor != null)
            this.votedFor = true ;
        else this.votedFor = false ; 
    }
    
    public void setDataController() throws SQLException, ClassNotFoundException
    {
        this.dataController = new voterManagerImpl(); 
    }
    
    public String getState() {
        return state;
    }
    
    public boolean getVotedFor() {
        return votedFor;
    }
    
    public String[][] getAllCandidate()
    {
        return dataController.getAllCandidate(); 
    }
    
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
    
    public void updateProdile(String[] infos)
    {
        if(dataController.updateVoter(infos, email))
        {
            this.state = infos[0];
            this.email = infos[1]; 
            this.password = infos[2];
        }
    }
    
}
