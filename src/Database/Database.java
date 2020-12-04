/*
  * 
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class provide access to the database using already specified informations.
 * @author Keke
 */
public class Database {
    
    private final String DB_NAME;
    private final String USERNAME;
    private final String PASSWORD;
    private Connection myCon;
    
    /** 
     * No parameters, it auto-fills the fields 
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public Database() throws SQLException, ClassNotFoundException{
        DB_NAME="jdbc:mysql://82.124.134.253:3306/electionApplication";
        USERNAME="kebecks";
        PASSWORD="electionapplication";
        establishConnection();
    }
    
    /** Establishing a connection with the object fields, returns the connection if it was established, null if it wasn'
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException*/
    
    public void establishConnection() throws SQLException, ClassNotFoundException
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            myCon = DriverManager.getConnection(DB_NAME, USERNAME, PASSWORD);
        }
        catch (ClassNotFoundException | SQLException e) { 
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }           
    }
    
    public Connection getCon() throws SQLException, ClassNotFoundException
    {
        
        if (myCon.isClosed())
            establishConnection();
        return myCon;
    }
}
