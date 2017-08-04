package it.emagrav.postgres.dao;

public class DBManagerException extends Exception {

	private final static String ERRORE = "Errore durante elaborazione del DBManager";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DBManagerException(String string) {
		super(ERRORE + " : " + string);
	}

	public DBManagerException(Exception e) {
		this(ERRORE, e);
	}

	private DBManagerException(String string, Exception e) {
		super(string, e);
	}
}
