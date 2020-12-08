/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.*;

/**
 * Base class for the 3 others.
 * @author Keke
 */

public abstract class User {
    
    protected String email;
    protected String password;
    protected String dateOfBirth;
    protected String firstName;
    protected String lastName;
    
    public User(String email, String password, String dateOfBirth, String firstName, String lastName){
        
        this.email=email;
        this.password=password;
        this.dateOfBirth=dateOfBirth;
        this.firstName=firstName;
        this.lastName=lastName;
        
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public String getPassword()
    {
        return password;
    }
    public String getDOB()
    {
        return dateOfBirth;
    }
    
    
    public String getFirstName()
    {
        return firstName;
    }
    
    public String getLastName()
    {
        return lastName;
    }
    
    
  
            
}
