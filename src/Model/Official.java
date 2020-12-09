/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.UserManagerImpl;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class used as an administrator, has almost all accesses to the database. 
 * @author Keke
 */
public class Official extends User {
    
    UserManagerImpl dataController;
    
    public Official(String email, String password, String dateOfBirth, String firstName, String lastName) throws SQLException, ClassNotFoundException {
        super(email, password, dateOfBirth, firstName, lastName);
        dataController = new UserManagerImpl();
    }
    
    /** 
     * Add a voter to the database
     * @param v
     * @return 
     */
    public boolean addVoter(Voter v){
        return dataController.insertVoter(v);
    }

     /** 
     * Add a candidate to the database
     * @param c
     * @return 
     */
    public boolean addCandidate(Candidate c) {
        return dataController.insertCandidate(c);
    }
    
     /** 
     * Remove a user from the database using its email
     * @param email
     * @return 
     */
    public boolean removeUser(String email)
    {
        return dataController.deleteUser(email);
    }
    
    /** 
     * Get all voters from the database
     * @return 
     */
    public String[][] getAllVoters(){
        return dataController.getAllVoters();
    }
    
    /** 
     * Get all candidates from the database
     * @return 
     */
    public String[][] getAllCandidates(){
        return dataController.getAllCandidates();
    }
    
    /** 
     * Modify an instance of Voter in the database
     * @param v
     * @param lastEmail
     * @return 
     */
    public boolean modifyVoter(Voter v, String lastEmail)
    {
        return dataController.modifyVoter(v, lastEmail);
    }
    
    /** 
     * Modify an instance of Candidate in the database
     * @param c
     * @param lastEmail
     * @return 
     */
    public boolean modifyCandidate(Candidate c, String lastEmail)
    {
        return dataController.modifyCandidate(c, lastEmail);
    }
    
    /** 
     * Add an election entry in the status database
     * @param status
     * @return 
     */
    public boolean addElectionEntry(String status)
    {
        return dataController.addElectionEntry(status);
    }
    
     /** 
     * Get last Status of election entry in the status database
     * @return 
     */
    public String getLastStatus()
    {
        return dataController.getLastStatus();
    }
    
    /** 
     * Set all votedFor attributes to null in the database
     * @return 
     */
    public boolean resetVotes(){
        return dataController.setVotesToNull();
    }
    
    /**
     * We're getting the name and last name of each candidates and the number of votes that have been casted for them 
     * @return 
     */
    public ArrayList<ArrayList<String>> getVotes(){
        return dataController.getVotes();
    }
    
    /**
     * We're getting the name and last name of each candidates and the number of votes that have been casted for them per state 
     * @return 
     */
    public ArrayList<String[]> getVotesByStates(){
        return dataController.getVotesByStates();
    }
    
     /**
     * Modify this official
     * @param info
     * @return 
     */
    public boolean modifySelf(String[] info) {
        return dataController.modifyOfficial(info, email);
        
    }
    
    /**
     * Upload an image on a candidate
     * @param email
     * @param file
     * @return 
     * @throws java.io.IOException 
     */
    public boolean uploadImage(String email, File file) throws IOException
    {
        return dataController.uploadImage(email, file);
    }
    
    /** 
     * Get a candidate's image as a byte[] 
     * @param email
     * @return 
     */
    public byte[] getPicture(String email)
    {
         return dataController.getPicture(email);
    }
    
    public ArrayList<ArrayList<String>> getWinner(){
        return dataController.getWinner();
    }
 
}

