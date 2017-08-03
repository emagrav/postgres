package it.emagrav.postgres;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
 
public class postgres {
 
    public static void main(String[] args) throws SQLException {
         
        String url = "jdbc:postgresql://localhost:5432/postgres";
        Properties props = new Properties();
        props.setProperty("user","ele");
        props.setProperty("password","ele");
 
        Connection conn = DriverManager.getConnection(url, props);
 
        System.out.println("Test di connessione avvenuto con successo");
         
        conn.close();
    }
 
}