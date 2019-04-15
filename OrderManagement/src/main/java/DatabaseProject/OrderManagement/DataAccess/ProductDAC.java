package DatabaseProject.OrderManagement.DataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DatabaseProject.OrderManagement.Connection.DBConnection;
import DatabaseProject.OrderManagement.Model.Product;

public class ProductDAC extends AbstractDAC<Product> {

	private String insertInto() {
		StringBuilder string = new StringBuilder();
		string.append("INSERT INTO ");
		string.append(getType().getSimpleName());
		string.append(" VALUES(?, ?, ?, ?);");
		return string.toString();
	}

	/*
	 * private String getProductsList() { StringBuilder string = new
	 * StringBuilder(); string.append("SELECT name, quantity FROM");
	 * string.append(getType().getSimpleName()); return string.toString(); }
	 */

	private String getName() {
		StringBuilder string = new StringBuilder();
		string.append("SELECT name FROM ");
		string.append(getType().getSimpleName());
		string.append(";");
		return string.toString();
	}

	private String getStock() {
		StringBuilder string = new StringBuilder();
		string.append("SELECT stock FROM ");
		string.append(getType().getSimpleName());
		string.append(" WHERE name= ");
		string.append("?");
		return string.toString();
	}

	private String updateStock() {
		StringBuilder string = new StringBuilder();
		string.append("UPDATE ");
		string.append(getType().getSimpleName());
		string.append(" SET stock= ?");
		string.append(" WHERE name= ? ;");
		return string.toString();
	}

	public boolean insert(int ID, String name, int price, int stock) {
		boolean ok = false;
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		String query = insertInto();
		connection = DBConnection.getConnection();
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, ID);
			statement.setString(2, name);
			statement.setInt(3, price);
			statement.setInt(4, stock);
			result = statement.executeUpdate();
			if (result == 1)
				ok = true;
			System.out.println("INSERT Product: " + result);
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error Product! Duplicate entry for PRIMARY KEY on INSERT");
		} catch (SQLException ex) {
			System.out.println("Error findByID");
			ex.printStackTrace();
		} finally {
			System.out.println("SFAFSFSAA");
			DBConnection.close(connection);
			DBConnection.close(statement);
		}
		return ok;
	}

	public List<String> getNames() {
		List<String> productsName = new ArrayList<String>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = getName();
		connection = DBConnection.getConnection();
		try {
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				productsName.add(name);
				// System.out.println("NUME : " + name.toString());
			}
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error! Duplicate entry for PRIMARY KEY on INSERT");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
			DBConnection.close(resultSet);
		}
		return productsName;
	}

	public int getStockNr(String name) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = getStock();
		connection = DBConnection.getConnection();
		int rez = 0;
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			System.out.println("SAS " + query);
			resultSet = statement.executeQuery();
			resultSet.next();
			rez = resultSet.getInt("stock");
			System.out.println("STOCK: " + rez);
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error! Duplicate entry for PRIMARY KEY on INSERT");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
			DBConnection.close(resultSet);
		}
		return rez;
	}

	public int stockUpdate(int newValue, String name) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = updateStock();
		connection = DBConnection.getConnection();
		int rezult = 0;
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, newValue);
			statement.setString(2, name);
			System.out.println("SAS " + query);
			rezult = statement.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error! Duplicate entry for PRIMARY KEY on INSERT");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
		}
		return rezult;
	}

	/*
	 * public List<Product> getProducts() throws SQLException { Connection
	 * connection = null; PreparedStatement statement = null; ResultSet resultSet =
	 * null; String query = getProductsList(); connection =
	 * DBConnection.getConnection(); List<Product> products = new
	 * ArrayList<Product>(); try { statement = connection.prepareStatement(query);
	 * resultSet = statement.executeQuery(); products = createObjects(resultSet); }
	 * catch (SQLException ex) { System.out.println("Error getProducts");
	 * ex.printStackTrace(); } finally { DBConnection.close(connection);
	 * DBConnection.close(statement); DBConnection.close(resultSet); } return
	 * products; }
	 */

}
