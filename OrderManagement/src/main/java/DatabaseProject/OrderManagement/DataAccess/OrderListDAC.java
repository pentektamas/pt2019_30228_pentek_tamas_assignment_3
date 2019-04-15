package DatabaseProject.OrderManagement.DataAccess;

import java.sql.*;

import DatabaseProject.OrderManagement.Connection.DBConnection;
import DatabaseProject.OrderManagement.Model.OrderList;

public class OrderListDAC extends AbstractDAC<OrderList> {

	private String insertInto() {
		StringBuilder string = new StringBuilder();
		string.append("INSERT INTO ");
		string.append(getType().getSimpleName());
		string.append(" VALUES(?, ?, ?, ?);");
		return string.toString();
	}

	private String totalPrice(int ID) {
		StringBuilder string = new StringBuilder();
		string.append("SELECT SUM(total) FROM ");
		string.append(getType().getSimpleName());
		string.append(" WHERE IDOrder= ");
		string.append(ID);
		return string.toString();
	}

	public void insert(int ID, int IDProduct, int quantity, int price) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		String query = insertInto();
		connection = DBConnection.getConnection();
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, ID);
			statement.setInt(2, IDProduct);
			statement.setInt(3, quantity);
			statement.setInt(4, price);
			result = statement.executeUpdate();
			System.out.println("INSERT OrderList: " + result);
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error OrderList! Duplicate entry for PRIMARY KEY on INSERT");
		} catch (SQLException ex) {
			System.out.println("Error findByID");
			ex.printStackTrace();
		} finally {
			System.out.println("SFAFSFSAA");
			DBConnection.close(connection);
			DBConnection.close(statement);
		}
	}

	public int totalPriceForIDOrder(int ID) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = totalPrice(ID);
		int result = 0;
		connection = DBConnection.getConnection();
		try {
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			resultSet.next();
			result = resultSet.getInt(1);
			System.out.println("Rez is " + result);
		} catch (SQLException e) {
			System.out.println("Error totalSUM");
			e.printStackTrace();
			e.getMessage();
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
			DBConnection.close(resultSet);
		}
		return result;
	}

}
