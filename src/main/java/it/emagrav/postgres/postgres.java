package it.emagrav.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class postgres {

	public static void main(String[] args) {

		String url = "jdbc:postgresql://localhost:5432/postgres";
		Properties props = new Properties();
		props.setProperty("user", "test");
		props.setProperty("password", "tesat");

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, props);
			System.out.println("Test di connessione avvenuto con successo");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}