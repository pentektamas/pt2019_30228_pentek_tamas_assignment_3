package DatabaseProject.OrderManagement.DataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DatabaseProject.OrderManagement.Connection.DBConnection;
import DatabaseProject.OrderManagement.Model.CompanyOrder;

/**
 * @author Pentek Tamas
 * 
 *         Clasa extinde clasa AbstractDAC, este un Data Access Class, in
 *         aceasta clasa se afla metode folosite la tabelul Companyorder
 *
 */
public class CompanyOrderDAC extends AbstractDAC<CompanyOrder> {

	/**
	 * Metoda creeaza un String care va fi o inserare in tabelul Companyorder
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
	 * Metoda creeaza un String care va fi o interogare folosite pentru a accesa ID
	 * de comanda
	 * 
	 * @return un String care este o interogare
	 */
	private String getID() {
		StringBuilder string = new StringBuilder();
		string.append("SELECT IDOrder FROM ");
		string.append(getType().getSimpleName());
		return string.toString();
	}

	/**
	 * Metoda se face o inserare in tabelul Companyorder
	 * 
	 * @param ID           este ID de comanda
	 * @param IDClient     este ID de client
	 * @param payment_type este tipul de plata
	 * @param date         este data comenzii
	 */
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
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error CompanyOrder! Duplicate entry for PRIMARY KEY on INSERT");
		} catch (SQLException ex) {
			System.out.println("Error CompanyOrder INSERT!");
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
		}
	}

	/**
	 * Metoda salveaza intr-o lista ID de comenzi
	 * 
	 * @return lista cu ID de comenzi
	 */
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
			System.out.println("Error CompanyOrder! getIDOrder");
		} catch (SQLException ex) {
			System.out.println("Error CompanyOrder! getIDOrder");
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
			DBConnection.close(resultSet);
		}
		return IDs;
	}

	/**
	 * Metoda gaseste data curenta din baza de date
	 * 
	 * @return un String cu data curenta
	 */
	public String getDate() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = "SELECT current_date;";
		String date = null;
		connection = DBConnection.getConnection();
		try {
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			resultSet.next();
			date = resultSet.getString(1);
		} catch (SQLException ex) {
			System.out.println("Error DATE");
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
			DBConnection.close(resultSet);
		}
		return date;
	}
}
