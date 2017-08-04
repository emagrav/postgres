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

	private static final String DB_PROP_FILE_NAME = "db.properties";
	private static final String QUERY_PROP_FILE_NAME = "query.properties";
	
	private static final String HOST = "host";
	private static final String DB_NAME = "dbName";
	private static final String PORT = "port";
	private static final String USER = "user";
	private static final String PASSWORD = "password";
	
	private Properties dbProperties;
	private Properties queryProperties;
	
	private static DBManager dbManager;

	public static DBManager getInstance() throws DBManagerException {
	
		if (dbManager == null)
			dbManager = new DBManager();
	
		return dbManager;
	}

	public void viewTable(String codQuery) throws DBManagerException {
		viewTable(this.getConnection(), codQuery);
	}

	private DBManager() throws DBManagerException {

		
		try (   InputStream dbPropFile = getClass().getResourceAsStream(DB_PROP_FILE_NAME);
				InputStream queryPropFile = getClass().getResourceAsStream(QUERY_PROP_FILE_NAME);) {

			if (dbPropFile==null) {
				throw new DBManagerException("File delle proprietà <" + DB_PROP_FILE_NAME + "> non trovato.");
			}
			
			if (queryPropFile==null) {
				throw new DBManagerException("File delle proprietà <" + QUERY_PROP_FILE_NAME + "> non trovato.");
			}
			
			dbProperties = getDbProperties(dbPropFile);
			
			queryProperties = getQueryProperties(queryPropFile);

		} catch (IOException e) {
			throw new DBManagerException(e);
		}
	}

//	private void getConnectionParameters(InputStream dbPropFile) throws IOException {
//		
//		if (postgresUrl != null) return;
//		
//		//Ricavo parametri connessione
//		Properties dbProperties = new Properties();
//
//		dbProperties.load(dbPropFile);
//		
//		//ricavo credenziali sempre dal file di property db
//		user = dbProperties.getProperty(USER);
//
//		
//	}

	private Properties getDbProperties(InputStream dbPropFile) throws IOException {
		if (dbProperties != null) return dbProperties;
		
		Properties dbProperties = new Properties();
		dbProperties.load(dbPropFile);
		
		return dbProperties;
	}

	private Properties getQueryProperties(InputStream queryPropFile) throws IOException {
		if (queryProperties != null) return queryProperties;
		
		Properties queryProperties = new Properties();
		queryProperties.load(queryPropFile);
		
		return queryProperties;
	}

	private void viewTable(Connection cn, String codQuery) throws DBManagerException {
		try (Statement stm = cn.createStatement();) {
			String query = this.getQuery(codQuery);
	
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

	private String getPostgresUrl() throws DBManagerException {
		return "jdbc:postgresql://" + getHost() + ":"
				+ getPort() + "/" + getDbName();
	}

	private String getHost() throws DBManagerException  {
		String host = dbProperties.getProperty(HOST);
		if (host==null) {
			throw new DBManagerException("Proprietà <" + HOST + "> inesistente all'interno del file delle proprietà " + DB_PROP_FILE_NAME + ".");
		}
		return host;
	}

	private String getPort() throws DBManagerException  {
		String port = dbProperties.getProperty(PORT);
		if (port==null) {
			throw new DBManagerException("Proprietà <" + PORT + "> inesistente all'interno del file delle proprietà " + DB_PROP_FILE_NAME + ".");
		}
		return port;
	}

	private String getDbName() throws DBManagerException {
		String dbName = dbProperties.getProperty(DB_NAME);
		if (dbName==null) {
			throw new DBManagerException("Proprietà <" + DB_NAME + "> inesistente all'interno del file delle proprietà " + DB_PROP_FILE_NAME + ".");
		}
		return dbName;
	}

	private String getUser() throws DBManagerException {
		String user = dbProperties.getProperty(USER);
		if (user==null) {
			throw new DBManagerException("Proprietà <" + USER + "> inesistente all'interno del file delle proprietà " + DB_PROP_FILE_NAME + ".");
		}
		return user;
	}

	private String getPassword() throws DBManagerException {
		String password = dbProperties.getProperty(PASSWORD);
		if (password==null) {
			throw new DBManagerException("Proprietà <" + PASSWORD + "> inesistente all'interno del file delle proprietà " + DB_PROP_FILE_NAME + ".");
		}
		return password;
	}

	private Connection getConnection() throws DBManagerException {
		try {
			return DriverManager.getConnection(getPostgresUrl(), getUser(), getPassword());
		} catch (SQLException e) {
			throw new DBManagerException(e);
		}
	}

	private String getQuery(String codQuery) throws DBManagerException {
		String query = queryProperties.getProperty(codQuery);
		if (query==null) {
			throw new DBManagerException("Codice query <" + codQuery + "> inesistente all'interno del file delle proprietà " + QUERY_PROP_FILE_NAME + ".");
		}
		return query;
	}

}