package DatabaseProject.OrderManagement.DataAccess;

import java.sql.*;

import DatabaseProject.OrderManagement.Connection.DBConnection;
import DatabaseProject.OrderManagement.Model.OrderList;

/**
 * @author Pentek Tamas
 * 
 *         Clasa extinde clasa AbstractDAC, este un Data Access Class, in
 *         aceasta clasa se afla metode folosite la tabelul Orderlist
 *
 */
public class OrderListDAC extends AbstractDAC<OrderList> {

	/**
	 * Metoda creeaza un String care va fi o inserare in tabelul Orderlist
	 * 
	 * @return un String care este o inserare
	 */
	private String insertInto() {
		StringBuilder string = new StringBuilder();
		string.append("INSERT INTO ");
		string.append(getType().getSimpleName());
		string.append("(IDOrder,IDProduct,product_price,quantity)");
		string.append(" VALUES(?, ?, ?, ?);");
		return string.toString();
	}

	/**
	 * Metoda creeaza un String care va fi o interogare folosite pentru a calcula
	 * suma totala a comenzii
	 * 
	 * @param ID este ID de comanda
	 * @return un String care este o interogare
	 */
	private String totalPrice(int ID) {
		StringBuilder string = new StringBuilder();
		string.append("SELECT SUM(total) FROM ");
		string.append(getType().getSimpleName());
		string.append(" WHERE IDOrder= ");
		string.append(ID);
		return string.toString();
	}

	/**
	 * Metoda se face o inserare in tabelul Orderlist
	 * 
	 * @param ID        este ID de comanda
	 * @param IDProduct este ID de produs
	 * @param price     este pretul produsului
	 * @param quantity  este cantitatea produsului
	 */
	public void insert(int ID, int IDProduct, int price, int quantity) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		String query = insertInto();
		connection = DBConnection.getConnection();
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, ID);
			statement.setInt(2, IDProduct);
			statement.setInt(3, price);
			statement.setInt(4, quantity);
			result = statement.executeUpdate();
		} catch (SQLException ex) {
			System.out.println("Error Insert OrderList");
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
		}
	}

	/**
	 * Metoda calculeaza suma totala a comenzii
	 * 
	 * @param ID este ID de comanda
	 * @return suma totala a comenzii
	 */
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
		} catch (SQLException e) {
			System.out.println("Error totalPriceForIDOrder");
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
			DBConnection.close(resultSet);
		}
		return result;
	}

}
