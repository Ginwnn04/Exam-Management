package exam.management.Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConnectDB {
    private static ConnectDB instance;
    private String connectionUrl = "jdbc:postgresql://localhost:5432/manager-restaurant?user=postgres&password=123456";
    private Connection con;
    
    private ConnectDB() {
    }
    
    public static ConnectDB getInstance() {
        if (instance == null) {
            instance = new ConnectDB();
        }
        return instance;
    }
    
    public void openConnect() {
        try {
            this.con = DriverManager.getConnection(connectionUrl);
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return con;
    }
    
    
}
