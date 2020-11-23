/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Keke
 */
public class Database {
    
    private final String DB_NAME;
    private final String USERNAME;
    private final String PASSWORD;
    
    /*No parameters, it auto-fills the fields */
    public Database(){
        DB_NAME="jdbc:mysql://82.124.134.253:3306/electionApplication";
        USERNAME="kebecks";
        PASSWORD="electionapplication";
    }
    
    /*Establishing a connection with the object fields, returns the connection if it was established, null if it wasn't*/
    
    public Connection establishConnection() throws SQLException, ClassNotFoundException
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_NAME, USERNAME, PASSWORD);
        }
        catch (ClassNotFoundException | SQLException e) { 
            e.printStackTrace();
        } 
        return null;             
    }
}
