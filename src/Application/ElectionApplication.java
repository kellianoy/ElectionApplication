/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import GUI.GUI_Start;
import java.sql.SQLException;

/**
 *
 * @author Keke
 */



public class ElectionApplication {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        
        GUI_Start window = new GUI_Start();
        window.embeddedMain();
        
        
    }
}
