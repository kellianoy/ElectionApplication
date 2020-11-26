/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enum;

/**
 *
 * @author Keke
 */
public enum Party{ 
    
    D("Democrats"), 
    C("Communists"), 
    LP("Labour Party"),
    R("Republicans"),
    GP("Green Party"),
    LBP("Libertarian Party"), 
    PP("Party Party");
    
    private String party;
 
    Party(String party) {
        this.party = party;
    }
    
    public String toString() {
       return this.party;
    }
    
};
