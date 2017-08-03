package it.emagrav.postgres;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import it.emagrav.postgres.dao.DBManager;
import it.emagrav.postgres.dao.DBManagerException;

public class postgres2 {

	public static void main(String[] args) {
		
		try (DBManager dbm = DBManager.getInstance();){
		
			Connection cn = dbm.getConnection();
			
			Statement stm = cn.createStatement();
			ResultSet rs = stm.executeQuery(dbm.getQuery("query1"));
			
			int i=1;
			while (rs.next()) {
				System.out.println("Riga " + i + " : " + rs.getInt(1) + " " + rs.getString(2));
				i++;
			}
		} catch (DBManagerException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

}