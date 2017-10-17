# postgres
Progetto di prova per testare DB postgres

# particolarità
- gestione delle eccezioni: 
	- utilizzo eccezione personalizzata (classe DBManagerException)
	- sempre specificata la causa delle'eccezione

- incapsulamento gestione accessi al DB (classe DBManager)

- uso del try-with-resources di Java 8

- uso delle Property

# Per lanciare il DB server (da riga di comando)
D:\Dati\tools\pgsql\PostgresStart.bat

# Per fermarlo
D:\Dati\tools\pgsql\PostgresStop.bat