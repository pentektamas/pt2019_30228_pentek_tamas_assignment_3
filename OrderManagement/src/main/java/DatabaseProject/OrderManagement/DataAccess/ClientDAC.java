package DatabaseProject.OrderManagement.DataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DatabaseProject.OrderManagement.Connection.DBConnection;
import DatabaseProject.OrderManagement.Model.Client;

/**
 * @author Pentek Tamas
 * 
 *         Clasa extinde clasa AbstractDAC, este un Data Access Class, in
 *         aceasta clasa se afla metode folosite la tabelul Client
 *
 */
public class ClientDAC extends AbstractDAC<Client> {

	/**
	 * Metoda creeaza un String care va fi o inserare in tabelul Client
	 * 
	 * @return un String care este o inserare
	 */
	private String insertInto() {
		StringBuilder string = new StringBuilder();
		string.append("INSERT INTO ");
		string.append(getType().getSimpleName());
		string.append(" VALUES(?, ?, ?, ?, ?, ?, ?);");
		return string.toString();
	}

	/**
	 * Metoda creeaza un String care va fi o interogare folosite pentru a accesa
	 * numele clientilor
	 * 
	 * @return un String care este o interogare
	 */
	private String getName() {
		StringBuilder string = new StringBuilder();
		string.append("SELECT first_name,last_name FROM ");
		string.append(getType().getSimpleName());
		string.append(";");
		return string.toString();
	}

	/**
	 * Metoda creeaza un String care va fi o interogare folosite pentru a accesa ID
	 * de client
	 * 
	 * @return un String care este o interogare
	 */
	private String getID() {
		StringBuilder string = new StringBuilder();
		string.append("SELECT IDClient FROM ");
		string.append(getType().getSimpleName());
		string.append(" WHERE first_name=?");
		return string.toString();
	}

	/**
	 * Metoda se face o inserare in tabelul Client
	 * 
	 * @param ID        este ID de client
	 * @param firstName este numele clientului
	 * @param lastName  este prenumele clientului
	 * @param address   este adresa clientului
	 * @param number    este numarul de telefon a clientului
	 * @param iban      este contul clientului
	 * @param email     este adresa de e-mail a clientului
	 * @return true dace inserarea s-a efectuat cu succes, fale altfel
	 */
	public boolean insert(int ID, String firstName, String lastName, String address, String number, String iban,
			String email) {
		boolean ok = false;
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		String query = insertInto();
		connection = DBConnection.getConnection();
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, ID);
			statement.setString(2, firstName);
			statement.setString(3, lastName);
			statement.setString(4, address);
			statement.setString(5, number);
			statement.setString(6, iban);
			statement.setString(7, email);
			result = statement.executeUpdate();
			if (result == 1)
				ok = true;
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error! Duplicate entry for PRIMARY KEY on INSERT");
		} catch (SQLException ex) {
			System.out.println("Error! Insert Client");
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
		}
		return ok;
	}

	/**
	 * Metoda salveaza numele clientilor intr-o lista
	 * 
	 * @return lista cu numele clientilor
	 */
	public List<String> getNames() {
		List<String> clientsName = new ArrayList<String>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = getName();
		connection = DBConnection.getConnection();
		try {
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				StringBuilder name = new StringBuilder();
				name.append(firstName);
				name.append(" ");
				name.append(lastName);
				clientsName.add(name.toString());
			}
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error! getNames()");
		} catch (SQLException ex) {
			System.out.println("Error! getNames()");
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
			DBConnection.close(resultSet);
		}
		return clientsName;
	}

	/**
	 * Metoda cauta ID de client dupa numele clientului
	 * 
	 * @param name este numele clientului
	 * @return ID de client
	 */
	public int getClientID(String name) {
		int ID = 0;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = getID();
		connection = DBConnection.getConnection();
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			resultSet = statement.executeQuery();
			resultSet.next();
			ID = resultSet.getInt("IDClient");
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error! GetClientID ");
		} catch (SQLException ex) {
			System.out.println("Error! GetClientID ");
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
			DBConnection.close(resultSet);
		}
		return ID;
	}
}
