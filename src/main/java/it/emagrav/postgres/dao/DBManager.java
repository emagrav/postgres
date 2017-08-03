package it.emagrav.postgres.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager implements AutoCloseable{
	 
    private static DBManager dbManager;
    private Properties queryProperties;
    private Properties dbProperties;
    private String postgresUrl;
    private String user;
    private String password;
 
    private DBManager() throws DBManagerException  {
 
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
 
    public static DBManager getInstance() throws DBManagerException  {
 
        if (dbManager == null)
            dbManager = new DBManager();
 
        return dbManager;
    }
 
    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(postgresUrl, user, password);
    }
 
    public String getQuery(String queryId) {
        return queryProperties.getProperty(queryId);
    }

	@Override
	public void close() throws Exception {
		if (dbManager != null)
			dbManager = null;
	}
 
}