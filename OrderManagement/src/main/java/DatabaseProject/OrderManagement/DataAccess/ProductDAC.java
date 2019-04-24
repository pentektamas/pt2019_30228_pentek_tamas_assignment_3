package DatabaseProject.OrderManagement.DataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DatabaseProject.OrderManagement.Connection.DBConnection;
import DatabaseProject.OrderManagement.Model.Product;

/**
 * @author Pentek Tamas
 * 
 *         Clasa extinde clasa AbstractDAC, este un Data Access Class, in
 *         aceasta clasa se afla metode folosite la tabelul Product
 *
 */
public class ProductDAC extends AbstractDAC<Product> {

	/**
	 * Metoda creeaza un String care va fi o inserare in tabelul Product
	 * 
	 * @return un String care este o inserare
	 */
	private String insertInto() {
		StringBuilder string = new StringBuilder();
		string.append("INSERT INTO ");
		string.append(getType().getSimpleName());
		string.append(" VALUES(?, ?, ?, ?);");
		return string.toString();
	}

	/**
	 * Metoda creeaza un String care va fi o interogare folosite pentru a accesa
	 * numele de produse
	 * 
	 * @return un String care este o interogare
	 */
	private String getName() {
		StringBuilder string = new StringBuilder();
		string.append("SELECT name FROM ");
		string.append(getType().getSimpleName());
		string.append(";");
		return string.toString();
	}

	/**
	 * Metoda creeaza un String care va fi o interogare folosite pentru a accesa ID
	 * de produs dupa nume de produs
	 * 
	 * @return un String care este o interogare
	 */
	private String getByNameID() {
		StringBuilder string = new StringBuilder();
		string.append("SELECT IDProduct FROM ");
		string.append(getType().getSimpleName());
		string.append(" WHERE name= ?");
		return string.toString();
	}

	/**
	 * Metoda creeaza un String care va fi o interogare folosite pentru a accesa
	 * pretul dupa nume de produs
	 * 
	 * @return un String care este o interogare
	 */
	private String getByNamesPrice() {
		StringBuilder string = new StringBuilder();
		string.append("SELECT price FROM ");
		string.append(getType().getSimpleName());
		string.append(" WHERE name= ?");
		return string.toString();
	}

	/**
	 * Metoda creeaza un String care va fi o interogare folosite pentru a accesa
	 * numarul de produse dupa nume de produs
	 * 
	 * @return un String care este o interogare
	 */
	private String getStock() {
		StringBuilder string = new StringBuilder();
		string.append("SELECT stock FROM ");
		string.append(getType().getSimpleName());
		string.append(" WHERE name= ");
		string.append("?");
		return string.toString();
	}

	/**
	 * Metoda creeaza un String care va fi o actualizare a tabelului Product
	 * 
	 * @return un String care este o actualizare
	 */
	private String updateStock() {
		StringBuilder string = new StringBuilder();
		string.append("UPDATE ");
		string.append(getType().getSimpleName());
		string.append(" SET stock= ?");
		string.append(" WHERE name= ? ;");
		return string.toString();
	}

	/**
	 * Metoda se face o inserare in tabelul Product
	 * 
	 * @param ID    este ID de product
	 * @param name  este numele produsului
	 * @param price este pretul produsului
	 * @param stock este cantitatea produsului
	 * @return true, daca inserarea s-a efectuat cu succes, false altfel
	 */
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
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error Product! Duplicate entry for PRIMARY KEY on INSERT");
		} catch (SQLException ex) {
			System.out.println("Error INSERT Product!");
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
		}
		return ok;
	}

	/**
	 * Metoda salveaza intr-o lista numele produselor
	 * 
	 * @return lista cu numele produselor
	 */
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
			}
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error! GetNames");
		} catch (SQLException ex) {
			System.out.println("Error! GetNames");
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
			DBConnection.close(resultSet);
		}
		return productsName;
	}

	/**
	 * Metoda cauta ID de produs dupa nume de produs
	 * 
	 * @param name este numele produsului
	 * @return ID de produs
	 */
	public int getByNamesID(String name) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = getByNameID();
		int ID = 0;
		connection = DBConnection.getConnection();
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			resultSet = statement.executeQuery();
			resultSet.next();
			ID = resultSet.getInt("IDProduct");
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error! GetByNamesID");
		} catch (SQLException ex) {
			System.out.println("Error! GetByNamesID");
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
			DBConnection.close(resultSet);
		}
		return ID;
	}

	/**
	 * Metoda cauta pretul dupa nume de produs
	 * 
	 * @param name este numele produsului
	 * @return pretul produsului
	 */
	public int getByNamesPrice(String name) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = getByNamesPrice();
		int price = 0;
		connection = DBConnection.getConnection();
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			resultSet = statement.executeQuery();
			resultSet.next();
			price = resultSet.getInt("price");
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error! getByNamesPrice");
		} catch (SQLException ex) {
			System.out.println("Error! getByNamesPrice");
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
			DBConnection.close(resultSet);
		}
		return price;
	}

	/**
	 * Metoda cauta cantitatea de produs dupa nume de produs
	 * 
	 * @param name este numele produsului
	 * @return cantitatea de produs
	 */
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
			resultSet = statement.executeQuery();
			resultSet.next();
			rez = resultSet.getInt("stock");
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error! getStockNr");
		} catch (SQLException ex) {
			System.out.println("Error! getStockNr");
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
			DBConnection.close(resultSet);
		}
		return rez;
	}

	/**
	 * Metoda face un update pe cantitatea produselor dupa o comanda
	 * 
	 * @param newValue este valoarea noua a cantitatii
	 * @param name     este numele produsului
	 * @return numarul de randuri in care s-a facut update
	 */
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
			rezult = statement.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error! StockUpdate");
		} catch (SQLException ex) {
			System.out.println("Error! StockUpdate");
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
		}
		return rezult;
	}
}
