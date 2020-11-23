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
public class Official extends User {
    
    UserManagerImpl dataController;
    
    public Official(String email, String password, GregorianCalendar dateOfBirth, String firstName, String lastName) {
        super(email, password, dateOfBirth, firstName, lastName);
        dataController = new UserManagerImpl();
    }
    
    public boolean addVoter(Voter v){
        return dataController.insertVoter(v);
    }
    
}
