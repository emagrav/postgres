package it.emagrav.postgres.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBManager {

	private static DBManager dbManager;
	private Properties queryProperties;
	private Properties dbProperties;
	private String postgresUrl;
	private String user;
	private String password;

	private DBManager() throws DBManagerException {

		try (InputStream dbPropFile = getClass().getResourceAsStream("db.properties");
				InputStream queryPropFile = getClass().getResourceAsStream("query.properties");) {

			queryProperties = new Properties();
			dbProperties = new Properties();

			dbProperties.load(dbPropFile);
			queryProperties.load(queryPropFile);

			postgresUrl = "jdbc:postgresql://" + dbProperties.getProperty("host") + ":"
					+ dbProperties.getProperty("port") + "/" + dbProperties.getProperty("dbName");

			user = dbProperties.getProperty("user");
			password = dbProperties.getProperty("password");

		} catch (IOException e) {
			throw new DBManagerException(e);
		}
	}

	public static DBManager getInstance() throws DBManagerException {

		if (dbManager == null)
			dbManager = new DBManager();

		return dbManager;
	}

	public Connection getConnection() throws DBManagerException {
		try {
			return DriverManager.getConnection(postgresUrl, user, password);
		} catch (SQLException e) {
			throw new DBManagerException(e);
		}
	}

	public String getQuery(String queryId) {
		return queryProperties.getProperty(queryId);
	}

	public void viewTable(Connection cn, String codQuery) throws DBManagerException {
		try (Statement stm = cn.createStatement();) {
			String query = this.getQuery(codQuery);
			
			if (query==null) {
				throw new DBManagerException("Codice query " + codQuery + " inesistente.");
			}
			
			ResultSet rs = stm.executeQuery(query);
			int i = 1;
			while (rs.next()) {
				System.out.println("Riga " + i + " : " + rs.getInt(1) + " " + rs.getString(2));
				i++;
			}
		} catch (SQLException e) {
			throw new DBManagerException(e);
		}
	}

	public void viewTable(String codQuery) throws DBManagerException {
		viewTable(this.getConnection(), codQuery);
	}

}