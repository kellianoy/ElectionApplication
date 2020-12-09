/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Candidate;
import Model.Voter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class is used to do operations from an official, it connects to the database and requests / writes to the database freely using the methods provided.
 * @author Keke
 * 
 */
public class UserManagerImpl implements UserManager {
   
    private Database data;
    
    public UserManagerImpl() throws SQLException, ClassNotFoundException{
        data=new Database();
    }
    
    /** Using a voter, can create a user entry in the database, returns true if it worked, false if it didn't
     * @param v
     * @return 
     */
    
    @Override
    public boolean insertVoter(Voter v) {
        try (Connection con = data.getCon()) {
            
                //Preparing statement for user DB
                PreparedStatement userStm=con.prepareStatement("INSERT INTO user (email, UserID, password, dateOfBirth, firstName, lastName) VALUES (?, null, ?, ?, ?, ?)");
                userStm.setString(1, v.getEmail());
                userStm.setString(2, v.getPassword());
                userStm.setString(3, v.getDOB());
                userStm.setString(4, v.getFirstName());
                userStm.setString(5, v.getLastName());
                
                //Preparing statement for candidate DB
                PreparedStatement voterStm=con.prepareStatement("INSERT INTO voter (VoterID, state, votedFor) VALUES(LAST_INSERT_ID(), ?, null)");
                voterStm.setString(1, v.getState());
                
                //Doing updates in the DB
                userStm.executeUpdate();
                voterStm.executeUpdate();
                return true;
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }

    /** Takes a mail as parameter and delete an instance in the database no matter what type of user it is.
     * @param email
     * @return  */
    @Override
    public boolean deleteUser(String email) {
        try (Connection con = data.getCon()) {
                PreparedStatement stm=con.prepareStatement("DELETE FROM user WHERE email=?");
                stm.setString(1, email);
                stm.executeUpdate(); 
                return true;
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
        
    }
    
    /** Returns all voters of the database as a two-dimensional array of Strings containing every data about them
     * @return  */
    @Override
    public String[][] getAllVoters() {
        
        try (Connection con = data.getCon()) {
                
                int i=0;
                Statement stm=con.createStatement();
                
                ResultSet voter = stm.executeQuery("SELECT COUNT(*) FROM voter");
                if (voter.next())
                {
                    String[][] infos = new String[voter.getInt(1)][6];  

                    voter = stm.executeQuery("SELECT user.firstName, user.lastName, user.email, user.password, user.dateOfBirth, voter.state FROM user, voter WHERE UserID=VoterID");

                    while(voter.next())
                    {
                        for (int k=1 ; k < infos[i].length+1 ; k++)
                            infos[i][k-1]=voter.getString(k);
                        ++i;
                    }
                    return infos;
                }
                
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }
    
    
    /** Returns all candidates of the database as a two-dimensional array of Strings containing every data about them
     * @return  */
    @Override
    public String[][] getAllCandidates() {
        
        try (Connection con = data.getCon()) {
                
                int i=0;
                Statement stm=con.createStatement();
                
                ResultSet candidate = stm.executeQuery("SELECT COUNT(*) FROM candidate");
                if (candidate.next())
                {
                    String[][] infos = new String[candidate.getInt(1)][7];  

                    candidate = stm.executeQuery("SELECT user.firstName, user.lastName, user.email, user.password, user.dateOfBirth, candidate.politicalParty, candidate.description FROM user, candidate WHERE UserID=CandidateID");

                    while(candidate.next())
                    {
                        for (int k=1 ; k < infos[i].length+1 ; ++k)
                            infos[i][k-1]=candidate.getString(k);
                        ++i;
                    }
                    return infos;
                }
                
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }
    
    /** Using a candidate, can create a user entry in the database, returns true if it worked, false if it didn't
     * @param c
     * @return 
     */
    @Override
    public boolean insertCandidate(Candidate c) {
        try (Connection con = data.getCon()) {
            
                //Preparing statement for user DB
                PreparedStatement userStm=con.prepareStatement("INSERT INTO user (email, UserID, password, dateOfBirth, firstName, lastName) VALUES (?, null, ?, ?, ?, ?)");
                userStm.setString(1, c.getEmail());
                userStm.setString(2, c.getPassword());
                userStm.setString(3, c.getDOB());
                userStm.setString(4, c.getFirstName());
                userStm.setString(5, c.getLastName());
                
                //Preparing statement for candidate DB
                PreparedStatement candidateStm=con.prepareStatement("INSERT INTO candidate (CandidateID, politicalParty, description) VALUES(LAST_INSERT_ID(), ?, ?)");
                candidateStm.setString(1, c.getParty());
                candidateStm.setString(2, c.getDescription());
                
                //Doing updates in the DB
                userStm.executeUpdate();
                candidateStm.executeUpdate();
                return true;
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }

    /** Using a voter and the last known email, can modify a user entry in the database, returns true if it worked, false if it didn't
     * @param v
     * @param lastEmail
     * @return 
     */
    @Override
    public boolean modifyVoter(Voter v, String lastEmail) {
        try (Connection con = data.getCon()) {
            
                //Preparing statement for update
               PreparedStatement userStm=con.prepareStatement("UPDATE user, voter SET email=?, password=?, dateOfBirth=?, firstName=?, lastName=?, state = ? "
                        + "WHERE email=? AND UserID=VoterID");
                userStm.setString(1, v.getEmail());
                userStm.setString(2, v.getPassword());
                userStm.setString(3, v.getDOB());
                userStm.setString(4, v.getFirstName());
                userStm.setString(5, v.getLastName());
                userStm.setString(6, v.getState());
                userStm.setString(7, lastEmail);
                userStm.executeUpdate();
                return true;
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
    
    /** Using a candidate and the last known email, can modify a user entry in the database, returns true if it worked, false if it didn't
     * @param c
     * @param lastEmail
     * @return 
     */
    @Override
    public boolean modifyCandidate(Candidate c, String lastEmail) {
        try (Connection con = data.getCon()) {
                PreparedStatement userStm=con.prepareStatement("UPDATE user, candidate SET email=?, password=?, dateOfBirth=?, firstName=?, lastName=?, politicalParty = ? , description = ? "
                        + "WHERE user.email=? AND user.UserID=candidate.CandidateID");
                userStm.setString(1, c.getEmail());
                userStm.setString(2, c.getPassword());
                userStm.setString(3, c.getDOB());
                userStm.setString(4, c.getFirstName());
                userStm.setString(5, c.getLastName());
                userStm.setString(6, c.getParty());
                userStm.setString(7, c.getDescription());
                userStm.setString(8, lastEmail);
                userStm.executeUpdate();
                return true;
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
    
   /** 
    * Add an entry to the status table with a set status
     * @param status
     * @return 
    */
    @Override
    public boolean addElectionEntry(String status)
    {
        try (Connection con = data.getCon()) {

                Calendar calendar = Calendar.getInstance();
                java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
                PreparedStatement stm=con.prepareStatement("INSERT INTO status (StateID, Date, Status) VALUES (NULL, ?, ?)");
                stm.setString(1,""+ date);
                stm.setString(2, status);
                stm.executeUpdate();
                return true;
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
    
    /**
     *  Returns last status from the database in the form of a string
     * @return
     */
    @Override
    public String getLastStatus()
    {
        try (Connection con = data.getCon()) {
                Statement stm=con.createStatement();
                ResultSet set= stm.executeQuery("SELECT Status FROM status ORDER BY StateID DESC");
                if (set.next())
                    return set.getString(1);
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

    /**
     * Set all votedFor attributes to null in the database
     * @return 
     */
    @Override
    public boolean setVotesToNull() {
        try (Connection con = data.getCon()) {
                Statement stm=con.createStatement();
                stm.executeUpdate("UPDATE voter SET votedFor = null");
                return true;
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
    
    /**
     * We're getting the name and last name of each candidates and the number of votes that have been casted for them 
     * @return 
     */
    
    @Override
    public ArrayList<ArrayList<String>> getVotes() {
        try (Connection con = data.getCon()) {

                
                ArrayList<ArrayList<String>> retrievedData= new ArrayList();
                
                PreparedStatement stm=con.prepareStatement("SELECT firstName, lastName, count(votedFor) FROM user JOIN candidate ON UserID = CandidateID JOIN voter ON votedFor = CandidateID GROUP BY votedFor");
                ResultSet candidateRetrieval=stm.executeQuery();
                
                while(candidateRetrieval.next())
                {
                        //I set the first case of my array to firstname + lastname
                        ArrayList<String> temp=new ArrayList();
                        temp.add(candidateRetrieval.getString("firstName") + " " + candidateRetrieval.getString("lastName"));
                        temp.add(candidateRetrieval.getString("count(votedFor)"));
                        retrievedData.add(temp);
                        
                }
                candidateRetrieval.close();                 
                return retrievedData;
        } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }
    
    /**
     * Return an arraylist of all possible winner with the name, email and the number of votes
     * @return
     */
    @Override
    public ArrayList<ArrayList<String>> getWinner(){
        try (Connection con = data.getCon()) {

                
                ArrayList<ArrayList<String>> retrievedData= new ArrayList();
                
                PreparedStatement stm=con.prepareStatement("SELECT firstName, lastName, email,  count(votedFor) "
                        + "FROM user JOIN candidate ON UserID = CandidateID JOIN voter ON votedFor = CandidateID "
                        + "GROUP BY votedFor "
                        + "HAVING count(votedFor) >= ALL (SELECT count(votedFor) "
                        + "FROM user JOIN candidate ON UserID = CandidateID JOIN voter ON votedFor = CandidateID "
                        + "GROUP BY votedFor)");
                
                ResultSet candidateRetrieval=stm.executeQuery();
                
                while(candidateRetrieval.next())
                {
                        //I set the first case of my array to firstname + lastname
                        ArrayList<String> temp=new ArrayList();
                        temp.add(candidateRetrieval.getString("firstName") + " " + candidateRetrieval.getString("lastName"));
                        temp.add(candidateRetrieval.getString("email"));
                        temp.add(candidateRetrieval.getString("count(votedFor)"));
                        retrievedData.add(temp);
                        
                }
                candidateRetrieval.close();                 
                return retrievedData;
        } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }
    
    /**
     * Return all the votes per states for each candidates in the form of an arraylist of string arrays
     * @return
     */
    @Override
    public ArrayList<String[]> getVotesByStates() {
        try (Connection con = data.getCon()) {

                ArrayList<String[]> retrievedData= new ArrayList();
                
                PreparedStatement stm=con.prepareStatement("SELECT firstName, lastName, state, count(votedFor) FROM user JOIN candidate ON UserID = CandidateID JOIN voter ON votedFor = CandidateID GROUP BY votedFor, state");
                ResultSet candidateRetrieval=stm.executeQuery();
                String[] temp= new String[] { "name" , ""+0, "" +0 , ""+0,  "" +0 , ""+0,  "" +0 , ""+0, ""+0 };
                while(candidateRetrieval.next())
                {
                        if (!temp[0].equals(candidateRetrieval.getString("firstName") + " " + candidateRetrieval.getString("lastName")))
                        {
                            if (!temp[0].equals("name"))
                            {
                                retrievedData.add(temp);
                            }
                            temp = new String[] { ""+0 , ""+0, "" +0 , ""+0,  "" +0 , ""+0,  "" +0 , ""+0, ""+0 };
                            temp[0]=candidateRetrieval.getString("firstName") + " " + candidateRetrieval.getString("lastName");
                            
                        }
                        String count=candidateRetrieval.getString("count(votedFor)");
                        switch ( candidateRetrieval.getString("state"))
                        {
                            case "Padokea" :
                                temp[1]=count;
                                break;
                            case "Heaven's Arena" :
                                temp[2]=count;
                                break;
                            case "Kukan'yu" :
                                temp[3]=count;
                                break;
                            case "Saherta" :
                                temp[4]=count;
                                break;
                            case "Yorbia" :
                                temp[5]=count;
                                break;
                            case "Begerosse" :
                                temp[6]=count;
                                break;
                            case "Kakin" :
                                temp[7]=count;
                                break;
                            case "Ochima" :
                                temp[8]=count;
                                break;   
                        }    
                        
                }
                retrievedData.add(temp);
                candidateRetrieval.close();           
                return retrievedData;
        } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

    /**
     * Modify the official that's calling the method ie the one that's currently logged
     * @param info
     * @param lastEmail
     * @return 
     */
    @Override
    public boolean modifyOfficial(String[] info, String lastEmail) {
        try (Connection con = data.getCon()) {
                PreparedStatement userStm=con.prepareStatement("UPDATE user SET email=?, password=?, firstName=?, lastName=?, dateOfBirth=? WHERE email=?");
                userStm.setString(1, info[0]);
                userStm.setString(2, info[1]);
                userStm.setString(3, info[2]);
                userStm.setString(4, info[3]);
                userStm.setString(5, info[4]);
                userStm.setString(6, lastEmail);
                userStm.executeUpdate();
                return true;
            } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
    
    /**
     * Update an image on a candidate using its email and a file. Returns true if it worked.
     * @param email
     * @param file
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    @Override
    public boolean uploadImage (String email, File file) throws FileNotFoundException, IOException {
        try (Connection con = data.getCon()) {
                PreparedStatement userStm=con.prepareStatement("UPDATE candidate, user SET picture=? WHERE user.email=? AND CandidateID=UserID");
                
                FileInputStream imageIn = new FileInputStream(file);
                userStm.setBinaryStream(1, imageIn, imageIn.getChannel().size());
                userStm.setString(2, email);
                userStm.executeUpdate();
                return true;
            } 
        catch (SQLException | ClassNotFoundException ex)
        {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
        
     /**
     * Used to get the picture of a candidate in the database using his email
     * @param email
     * @return 
     */
    @Override
    public byte[] getPicture(String email)
    {
        try (Connection con = data.getCon())  
        {
            PreparedStatement imageOfCandidate = con.prepareStatement("SELECT picture FROM candidate, user WHERE CandidateID=UserID AND email=?");
            imageOfCandidate.setString(1, email);
            ResultSet i = imageOfCandidate.executeQuery(); 
            if(i.next())
            {
                 byte[] img = i.getBytes("picture");
                 return img;
            }
        } 
        catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
        
}

