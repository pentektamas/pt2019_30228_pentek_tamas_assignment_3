package DatabaseProject.OrderManagement.DataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DatabaseProject.OrderManagement.Connection.DBConnection;
import DatabaseProject.OrderManagement.Model.Client;

public class ClientDAC extends AbstractDAC<Client> {

	private String insertInto() {
		StringBuilder string = new StringBuilder();
		string.append("INSERT INTO ");
		string.append(getType().getSimpleName());
		string.append(" VALUES(?, ?, ?, ?, ?, ?, ?);");
		return string.toString();
	}
	
	public String getName() {
		StringBuilder string = new StringBuilder();
		string.append("SELECT first_name,last_name FROM ");
		string.append(getType().getSimpleName());
		string.append(";");
		return string.toString();
	}

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
			System.out.println("INSERT: " + result);
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error! Duplicate entry for PRIMARY KEY on INSERT");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
		}
		return ok;
	}

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
				System.out.println("NUME : " + name.toString());
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
		return clientsName;
	}

}
