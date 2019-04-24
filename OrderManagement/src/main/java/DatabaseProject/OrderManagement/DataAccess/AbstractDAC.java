package DatabaseProject.OrderManagement.DataAccess;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DatabaseProject.OrderManagement.Connection.DBConnection;

/**
 * 
 * @author Pentek Tamas
 *
 *         Aceasta clasa este un Data Access Class care salveaza date din baza
 *         de date
 *
 * @param T este o clasa din pachetul Model
 */
public class AbstractDAC<T> {

	private Class<T> type;

	@SuppressWarnings("unchecked")
	public AbstractDAC() {

		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void setType(Class<T> type) {
		this.type = type;
	}

	public Class<T> getType() {
		return type;
	}

	/**
	 * Metoda creeaza un String care va fi o interogare folosita pentru a accesa
	 * toate datele din tabel
	 * 
	 * @return un String care este o interogare
	 */
	private String selectAll() {
		StringBuilder string = new StringBuilder();
		string.append("SELECT * FROM ");
		string.append(type.getSimpleName());
		return string.toString();
	}

	/**
	 * Metoda creeaza un String care va fi o actualizare in tabel
	 * 
	 * @param column    este coloana care vrem sa actualizam
	 * @param condition este conditia actualizarii
	 * @return un String care este o actualizare
	 */
	private String updateRows(String column, String condition) {
		StringBuilder string = new StringBuilder();
		string.append("UPDATE ");
		string.append(type.getSimpleName());
		string.append(" SET ");
		string.append(column);
		string.append(" =? ");
		string.append(" WHERE ");
		string.append(condition + ";");
		return string.toString();
	}

	/**
	 * Metoda creeaza un String care va fi o stergere din tabel
	 * 
	 * @param condition este conditia stergerii
	 * @return un Strring care este o stergere
	 */
	private String deleteFrom(String condition) {
		StringBuilder string = new StringBuilder();
		string.append("DELETE FROM ");
		string.append(type.getSimpleName());
		string.append(" WHERE ");
		string.append(condition + ";");
		return string.toString();
	}

	/**
	 * Metoda salveaza intr-o lista toate datele dintr-un tabel
	 * 
	 * @return o lista cu datele din tabel
	 */
	public List<T> findAll() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = selectAll();
		connection = DBConnection.getConnection();
		List<T> obj = new ArrayList<T>();
		try {
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			obj = createObjects(resultSet);
		} catch (SQLException e) {
			System.out.println("Error findAll");
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
			DBConnection.close(resultSet);
		}
		return obj;
	}

	/**
	 * Metoda face un update intr-un tabel
	 * 
	 * @param column    este coloana care vrem sa actualizam
	 * @param value     este valoarea cu care vrem sa actualizam
	 * @param condition este conditia actualizarii
	 * @return numarul de randuri actualizate in caz de succes, altfel -1
	 */
	public int update(String column, String value, String condition) {
		Connection connection = null;
		PreparedStatement statement = null;
		int result = 0;
		int flag = -1;
		String query = updateRows(column, condition);
		connection = DBConnection.getConnection();
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, value);
			result = statement.executeUpdate();
			flag = 1;
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error! Duplicate VALUE on UPDATE!");
		} catch (SQLException ex) {
			System.out.println("Error on UPDATE!");
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
		}
		if (flag == 1)
			return result;
		else
			return flag;
	}

	/**
	 * Metoda face stergerea dintr-un tabel
	 * 
	 * @param condition este conditia stergerii
	 * @return numarul de randuri la care s-a facut delete in caz de succes, altfel
	 *         -1
	 */
	public int delete(String condition) {
		Connection connection = null;
		PreparedStatement statement = null;
		int flag = -1;
		int result = 0;
		String query = deleteFrom(condition);
		connection = DBConnection.getConnection();
		try {
			statement = connection.prepareStatement(query);
			result = statement.executeUpdate();
			flag = 1;
		} catch (SQLException ex) {
			System.out.println("Error on DELETE!");
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
		}
		if (flag == 1)
			return result;
		else
			return flag;
	}

	/**
	 * Metoda creeaza obiecte din datele aflate in tabel
	 * 
	 * @param resultSet sunt datele extrase din tabel
	 * @return o lista cu obiecte de tip T care contine toate datele din tabel
	 */
	@SuppressWarnings("deprecation")
	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		try {
			while (resultSet.next()) {
				T instance = type.newInstance();
				for (Field field : type.getDeclaredFields()) {
					Object value = resultSet.getObject(field.getName());
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
			return list;
		} catch (Exception e) {
			System.out.println("Error! CreateObjects returns List<T>");
		}
		return null;
	}

	/**
	 * Metoda salveaza intr-o lista numele de coloane a tabelului
	 * 
	 * @return lista cu numele de coloane
	 */
	public List<String> retrieveProperties() {
		List<String> fieldsList = new ArrayList<String>();
		for (Field field : this.type.getDeclaredFields()) {
			fieldsList.add(field.getName());
		}
		return fieldsList;
	}

	/**
	 * Metoda salveaza proprietatile a unui obiect intr-o lista de Stringuri
	 * 
	 * @param object este obiectul la care vrem sa aflam proprietatile
	 * @return o lista de Stringuri care contine proprietatile obiectului
	 */
	public List<String> retrievePropertiesValues(T object) {
		T value = null;
		List<String> val = new ArrayList<String>();
		for (Field field : object.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try {
				value = (T) field.get(object);
				val.add(value + "");

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return val;
	}
}
