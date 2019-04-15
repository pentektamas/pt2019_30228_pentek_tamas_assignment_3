package DatabaseProject.OrderManagement.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

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

	public void insert(int ID, int IDClient, int totalPrice, String date) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		String query = insertInto();
		connection = DBConnection.getConnection();
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, ID);
			statement.setInt(2, IDClient);
			statement.setInt(3, totalPrice);
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
		}
	}
}
