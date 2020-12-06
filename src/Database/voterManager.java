/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

/**
 *
 * @author rebec
 */
public interface voterManager {
    public boolean updateVote(String emailCandidate, String email); 
    public boolean updateVoter(String [] infos, String old_email); 
    public boolean electionIsOpen(); 

    public String[][] getAllCandidate(); 
}
