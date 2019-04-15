package DatabaseProject.OrderManagement.Connection;

import java.sql.*;
import java.util.logging.Logger;

public class DBConnection {
	private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/ordermanagementcompany?useSSL=false";
	//SET GLOBAL time_zone = '+2:00';
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

	private Connection createConnection() {
		Connection con = null;
		try {
			if (con == null)
				con = DriverManager.getConnection(DBURL, USER, PASSWORD);
			System.out.println("Database is OPENED!");
		} catch (SQLException e) {
			System.out.println("Error! Can't connect to database!");
			e.printStackTrace();
		}
		return con;
	}

	public static Connection getConnection() {
		Connection con = singleInstance.createConnection();
		return con;
	}

	public static void close(Connection connection) {
		try {
			if (connection.isClosed() == false && connection!=null)
				connection.close();
			System.out.println("Database is CLOSED!");
		} catch (SQLException e) {
			System.out.println("Error! Can't close the connection!");
		}
	}

	public static void close(Statement statement) {
		try {
			if (statement.isClosed() == false && statement!=null)
				statement.close();
			System.out.println("Statement CLOSED!");
		} catch (SQLException e) {
			System.out.println("Error! Can't close the statement!");
		}
	}

	public static void close(ResultSet resultSet) {
		try {
			if (resultSet.isClosed() == false && resultSet!=null)
				resultSet.close();
			System.out.println("RESULTSET COLSED!");
		} catch (SQLException e) {
			System.out.println("Error! Can't close the resultSet!");
		}
	}
}
