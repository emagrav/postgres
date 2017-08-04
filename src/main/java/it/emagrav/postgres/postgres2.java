package it.emagrav.postgres;

import it.emagrav.postgres.dao.DBManager;
import it.emagrav.postgres.dao.DBManagerException;

public class postgres2 {

	public static void main(String[] args) {

		try {
			DBManager dbm = DBManager.getInstance();
			dbm.viewTable("query1");
		} catch (DBManagerException e1) {
			e1.printStackTrace();
		}
	}
}
