package fx.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by kamilek on 30/11/2016.
 */
public class DBConnection {
    private final String url = "jdbc:h2:tcp://localhost/~/test";
    private final String uname = "Admin";
    private final String pass = "password";
    private final Connection conn = DriverManager.getConnection(url, uname, pass);

    public DBConnection() throws SQLException {

    }

    public Connection getConn() {
        return conn;
    }


}

