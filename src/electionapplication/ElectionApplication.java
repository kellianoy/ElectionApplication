/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionapplication;

import electionapplication.GUI.GUI_Start;
import java.sql.SQLException;
import java.util.GregorianCalendar;

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
        // TODO code application logic here
    
        GUI_Start window = new GUI_Start();
        window.embeddedMain();
        
    }
    
    /** Take a SQL date as a parameter, converts it and returns it to GregorianCalender
     * @param SQLdate
     * @return  */
    public static GregorianCalendar convertSQLtoGregorian(String SQLdate){
      return new GregorianCalendar(Integer.parseInt(SQLdate.substring(0, 4)), Integer.parseInt(SQLdate.substring(5, 7)), Integer.parseInt(SQLdate.substring(8)));
    }
}
