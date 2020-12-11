/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author rebec
 */
public interface VoterManager {
    public boolean updateVote(String emailCandidate, String email); 
    public boolean updateVoter(String [] infos, String old_email); 
    public byte[] getPicture(String email);
    public boolean electionIsOpen(); 

    public String[][] getAllCandidate(); 
}
