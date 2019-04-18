package DatabaseProject.OrderManagement.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import DatabaseProject.OrderManagement.Connection.DBConnection;
import DatabaseProject.OrderManagement.Model.CompanyOrder;

public class CompanyOrderDAC extends AbstractDAC<CompanyOrder> {

	private String insertInto() {
		StringBuilder string = new StringBuilder();
		string.append("INSERT INTO ");
		string.append(getType().getSimpleName());
		string.append(" VALUES(?, ?, ?, ?);");
		return string.toString();
	}

	private String getID() {
		StringBuilder string = new StringBuilder();
		string.append("SELECT IDOrder FROM ");
		string.append(getType().getSimpleName());
		return string.toString();
	}

	public void insert(int ID, int IDClient, String payment_type, String date) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		String query = insertInto();
		connection = DBConnection.getConnection();
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, ID);
			statement.setInt(2, IDClient);
			statement.setString(3, payment_type);
			statement.setString(4, date);
			result = statement.executeUpdate();
			System.out.println("INSERT CompanyOrder: " + result);
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error CompanyOrder! Duplicate entry for PRIMARY KEY on INSERT");
		} catch (SQLException ex) {
			System.out.println("Error CompanyOrder!");
			ex.printStackTrace();
		} finally {
			System.out.println("SFAFSFSAA");
			DBConnection.close(connection);
			DBConnection.close(statement);
		//	DBConnection.close(resultSet);
		}
	}

	public List<Integer> getIDOrder() {
		List<Integer> IDs = new ArrayList<Integer>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = getID();
		connection = DBConnection.getConnection();
		try {
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				IDs.add(resultSet.getInt("IDOrder"));
			}
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error CompanyOrder! Duplicate entry for PRIMARY KEY on INSERT");
		} catch (SQLException ex) {
			System.out.println("Error CompanyOrder!");
			ex.printStackTrace();
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
			DBConnection.close(resultSet);
		}
		return IDs;
	}
}
