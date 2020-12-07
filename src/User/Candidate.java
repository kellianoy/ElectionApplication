/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

/**
 * Self-Explanatory.
 * @author Keke
 */



public class Candidate extends User {
    
    private String politicalParty;
    private String description;
   

    public Candidate(String email, String password, String dateOfBirth, String firstName, String lastName, String politicalParty, String description) {
        super(email, password, dateOfBirth, firstName, lastName);
        this.politicalParty=politicalParty;
        this.description=description;
    }

    public String getParty() {
        return politicalParty;
    }
    
    public String getDescription() {
        return description;
    }
    
    
}
