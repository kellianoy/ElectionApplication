/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Candidate;
import Model.Voter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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
    public ArrayList<ArrayList<String>> getVotes();
    public ArrayList<String[]> getVotesByStates();
    public boolean modifyOfficial(String[] info, String lastEmail) ;
    public boolean uploadImage (String email, File file) throws FileNotFoundException, IOException;
    public byte[] getPicture(String email);
    public ArrayList<ArrayList<String>> getWinner();
    
    
    
    
}
