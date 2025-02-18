package Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConnectDB {
    private static ConnectDB instance;
    private String url = "jdbc:mysql://exammanagement.c1m26ocsabbv.ap-southeast-1.rds.amazonaws.com/exammanagement";
    private String username = "admin";
    private String password = "12345678";
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
            this.con = DriverManager.getConnection(url, username, password);
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return con;
    }
    
    
}
