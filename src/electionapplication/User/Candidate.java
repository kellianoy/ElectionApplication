/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionapplication.User;

import java.util.GregorianCalendar;

/**
 * Self-Explanatory.
 * @author Keke
 */



public class Candidate extends User {
    
    private String politicalParty;
    private String description;
   

    public Candidate(String email, String password, GregorianCalendar dateOfBirth, String firstName, String lastName, String politicalParty, String description) {
        super(email, password, dateOfBirth, firstName, lastName);
        this.politicalParty=politicalParty;
        this.description=description;
    }
    
    
    
}
