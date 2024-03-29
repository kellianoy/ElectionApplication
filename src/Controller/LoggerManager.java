/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Voter;
import Model.Official;
import Model.Candidate;
import Model.User;
import java.sql.SQLException;


/**
 * DB Interface for the GUI_Start
 * @author Keke
 */
public interface LoggerManager {
    
    public User loggingCheck(String email, String password);
    public Official retrieveOfficial(int userID) throws SQLException, ClassNotFoundException;
    public Candidate retrieveCandidate(int userID) throws SQLException, ClassNotFoundException;
    public Voter retrieveVoter(int userID) throws SQLException, ClassNotFoundException;
    
}
