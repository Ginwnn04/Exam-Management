package Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// import java.util.logging.Level;
// import java.util.logging.Logger;


public class ConnectDB {
    private static ConnectDB instance;
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DB_NAME = "QLDT";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345678";

    private static final String CONNECTION_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?useSSL=false&serverTimezone=UTC";
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        } 
        catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return con;
    }
    
    
}
