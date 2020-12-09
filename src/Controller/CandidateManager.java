/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.ArrayList;

/**
 *  DB Interface for the Candidate Class, can access tables, modify his data on the DB
 * @author rebec
 */
public interface CandidateManager {
    public String electionIsOpen(); 
    public boolean updateCandidate(String[] infos, String email); 
    
    public ArrayList<String[]> getAllCandidates(String logEmail); 
    public String[][] getVotesByStates(String logEmail, String compareEmail);
}
