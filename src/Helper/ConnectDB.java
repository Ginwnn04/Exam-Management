package Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConnectDB {
    private static ConnectDB instance;
//    private String host = "dpg-crgm10aj1k6c739jcud0-a.singapore-postgres.render.com";
//    private String dbName = "testing_restaurant_manager";
//    private String username = "testing_restaurant_manager_user";
//    private String password = "7p3LCOi2nW7LAE9kxlfwSFq3yGKnL1gK";
//    private String sslMode = "&ssl=true";
    
//    private String connectionUrl = "jdbc:postgresql://" + host + ":5432/" + dbName + "?user=" + username + "&password=" + password + sslMode;
    
    private String connectionUrl = "jdbc:postgresql://localhost:5432/testing?user=postgres&password=123456";
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
