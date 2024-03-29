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
public interface OfficialManager {
    
    public boolean insertVoter(Voter v);
    public boolean insertCandidate(Candidate c);
    public boolean modifyVoter(Voter v, String lastEmail) ;
    public boolean modifyCandidate(Candidate c, String lastEmail) ;
    public boolean modifyOfficial(String[] info, String lastEmail) ;
    public boolean deleteUser(String email);
    public boolean uploadImage (String email, File file) throws FileNotFoundException, IOException;
    
    public boolean addElectionEntry(String status);
     public boolean setVotesToNull();
     
    public String[][] getAllVoters();
    public String[][] getAllCandidates();
    
    public String getLastStatus();
    
    public ArrayList<ArrayList<String>> getVotes();
    public ArrayList<String[]> getVotesByStates();
    
    public byte[] getPicture(String email);
    public ArrayList<ArrayList<String>> getWinner();
    
    
    
    
}
