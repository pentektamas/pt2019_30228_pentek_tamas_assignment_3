package DatabaseProject.OrderManagement.Connection;

import java.sql.*;
import java.util.logging.Logger;

/**
 * 
 * @author Pentek Tamas
 * 
 *         In aceasta clasa se face conexiunea cu baza de date si alte operatii
 *         cu baza de date
 *
 */
public class DBConnection {
	private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/ordermanagementcompany?useSSL=false";
	// SET GLOBAL time_zone = '+2:00';
	private static final String USER = "root";
	private static final String PASSWORD = "password";

	private static DBConnection singleInstance = new DBConnection();

	private DBConnection() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("Error DBConnection constructor!");
		}
	}

	/**
	 * Metoda privata care creeaza o conexiune
	 * 
	 * @return conexiunea curenta
	 */
	private Connection createConnection() {
		Connection con = null;
		try {
			if (con == null)
				con = DriverManager.getConnection(DBURL, USER, PASSWORD);
		} catch (SQLException e) {
			System.out.println("Error! Can't connect to database!");
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * Metoda publica care se apeleaza din afara clasei pentru a accesa conexiunea
	 * facuta
	 * 
	 * @return conexiunea curenta
	 */
	public static Connection getConnection() {
		Connection con = singleInstance.createConnection();
		return con;
	}

	/**
	 * Metoda inchide conexiunea trimisa ca parametru
	 * 
	 * @param connection este conexiunea care dorim sa inchidem
	 */
	public static void close(Connection connection) {
		try {
			if (connection.isClosed() == false && connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Error! Can't close the connection!");
		}
	}

	/**
	 * Metoda inchide statementul trimis ca paramentru
	 * 
	 * @param statement este statementul care dorim sa inchidem
	 */
	public static void close(Statement statement) {
		try {
			if (statement.isClosed() == false && statement != null)
				statement.close();
		} catch (SQLException e) {
			System.out.println("Error! Can't close the statement!");
		}
	}

	/**
	 * Metoda inchide resultSetul trimis ca parametru
	 * 
	 * @param resultSet este resultSetul care dorim sa inchidem
	 */
	public static void close(ResultSet resultSet) {
		try {
			if (resultSet.isClosed() == false && resultSet != null)
				resultSet.close();
		} catch (SQLException e) {
			System.out.println("Error! Can't close the resultSet!");
		}
	}
}
