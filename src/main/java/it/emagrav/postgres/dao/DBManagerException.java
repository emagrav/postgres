package it.emagrav.postgres.dao;

public class DBManagerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DBManagerException(Exception e) {
		this("Errore durante elaborazione del DBManager!", e);
	}

	public DBManagerException(String string, Exception e) {
		super(string,e);
	}

	public DBManagerException(String string) {
		super (string);
	}
}
