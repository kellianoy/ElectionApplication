/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionapplication.Database;

import electionapplication.User.*;

/**
 * DB Interface for the Official Class
 * @author Keke
 */
public interface UserManager {
    
    public boolean insertVoter(Voter v);
    public boolean deleteVoter(Voter v);
    
    public boolean insertCandidate(Candidate c);
    
}
