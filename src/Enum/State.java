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
public enum State{ 
    
    P("Padokea"), 
    HA("Heaven's Arena"), 
    KY("Kukan'yu"),
    S("Saherta"),
    Y("Yorbia"),
    B("Begerosse"), 
    K("Kakin"),
    O("Ochima");
    
    private String state;
 
    State(String state) {
        this.state = state;
    }
    
    public String toString() {
       return this.state;
    }
    
};