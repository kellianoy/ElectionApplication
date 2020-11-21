/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        try{
            String user = "kebecks";
            String mdp = "electionapplication";
            String database = "electionApplication";
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://82.124.134.253:3306/" + database, user, mdp)) {
                Statement stm=con.createStatement();
                ResultSet res=stm.executeQuery("select * from user");

                while (res.next()){
                    System.out.println("email : "+res.getString(1)+ " | id : " + res.getString(2)  + " | password : "+res.getString(3));
                }
            } 
        }
        catch (ClassNotFoundException | SQLException e) { 
            e.printStackTrace();
        } 
    
    }
}
