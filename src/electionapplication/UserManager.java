/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionapplication;

/**
 *
 * @author Keke
 */
public interface UserManager {
    
    public boolean insertVoter(Voter v);
    public boolean deleteVoter(Voter v);
    
}
