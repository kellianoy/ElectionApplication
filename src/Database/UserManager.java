/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import User.Candidate;
import User.Voter;

/**
 * DB Interface for the Official Class, can access tables, delete, modify, add on the DB
 * @author Keke
 */
public interface UserManager {
    
    public boolean insertVoter(Voter v);
    public boolean insertCandidate(Candidate c);
    
    public boolean modifyVoter(Voter v, String lastEmail) ;
    public boolean modifyCandidate(Candidate c, String lastEmail) ;
    
    public boolean setVotesToNull();
    public String[][] getAllVoters();
    public String[][] getAllCandidates();
    
    public boolean deleteUser(String email);
    
    public boolean addElectionEntry(String status);
    public String getLastStatus();
    public String[][] getVotes();
}
