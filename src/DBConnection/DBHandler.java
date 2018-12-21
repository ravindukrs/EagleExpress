package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHandler extends Configs {
    Connection conn = null;
    public Connection getConnection(){
        try{
            conn = DriverManager.getConnection(CONN_STRING,USERNAME,PASSWORD);
            System.out.println("Connected!");
        } catch(SQLException e){
            System.err.println(e);
            return null;
        }

        return conn;
    }
    public void closeConnection() throws SQLException {
        if(conn != null){
            conn.close();
        }
    }
}
