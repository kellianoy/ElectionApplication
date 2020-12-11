/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.CandidateManagerImpl;
import Controller.VoterManagerImpl;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Class used as an administrator, can modify his data and access the database.
 * @author rebec
 */
public class Candidate extends User {
    
    private String politicalParty;
    private String description;
    private CandidateManagerImpl dataController ; 
   

    public Candidate(String email, String password, String dateOfBirth, String firstName, String lastName, String politicalParty, String description) {
        super(email, password, dateOfBirth, firstName, lastName);
        this.politicalParty=politicalParty;
        this.description=description;
    }
    
    /** 
     * Set the data controller of Candidate, made possible the link with a database. 
     */
    public void setDataController() throws SQLException, ClassNotFoundException
    {
        this.dataController = new CandidateManagerImpl(); 
    }

    public String getParty() {
        return politicalParty;
    }
    
    public String getDescription() {
        return description;
    }
    
    /** 
     * Get the state of election.
     * @return 
     */
    public String getStatusElection()
    {
        return dataController.electionIsOpen() ; 
    }
    
    /** 
     * Modify the informations of the profile, take an array first box email, second box password.
     * @param infos
     * @return 
     */
    public boolean updateProdile(String [] infos)
    {
        if(infos[0].equals(""))
            infos[0] = this.email ; 
        if(infos[1].equals(""))
            infos[1] = this.password ; 
        if(dataController.updateCandidate(infos, email))
        {
            this.email = infos[0]; 
            this.password = infos[1];
            return true; 
        }
        return false ; 
    }
    
    /**
     * Get all the candidates from the database unless the actual candidate login. 
     * @return 
     */
    public ArrayList<String[]> getAllCandidatesInfos()
    {
        return dataController.getAllCandidates(email); 
    }
    
    /**
     * We're getting the number of votes that have been casted for the candidate and the compareCandidate.
     * @param compareEmail
     * @return 
     */
    public String[][] getVotesByStates(String compareEmail)
    {
        return dataController.getVotesByStates(email, compareEmail);
    }
    
    /**
     * Return the image of an instance of the database
     * @param email
     * @return 
     */
    public byte[] getImage()
    {
        return dataController.getPicture(email);
    }
    
    /**
     * Upload an image on a candidate
     * @return 
     */
    public boolean uploadImage(File file) throws IOException
    {
        return dataController.uploadImage(email, file);
    }
}
