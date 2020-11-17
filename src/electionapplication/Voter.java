/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionapplication;

import java.util.GregorianCalendar;

/**
 *
 * @author Keke
 */
public class Voter extends User {
    
    private String state;
    private Candidate votedFor;

    public Voter(String email, String password, GregorianCalendar dateOfBirth, String firstName, String lastName, String state, Candidate votedFor) {
        super(email, password, dateOfBirth, firstName, lastName);
        this.state=state;
        this.votedFor=votedFor;        
    }
    
}
