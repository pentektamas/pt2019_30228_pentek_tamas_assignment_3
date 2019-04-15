package DatabaseProject.OrderManagement.DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import DatabaseProject.OrderManagement.Connection.DBConnection;

public class AbstractDAC<T> {

	private static final Logger LOGGER = Logger.getLogger(AbstractDAC.class.getName());

	private Class<T> type;

	@SuppressWarnings("unchecked")
	public AbstractDAC() {

		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		System.out.println(type.getSimpleName() + "!!!!!!!!!");
	}

	public Class<T> getType() {
		return type;
	}

	public void setType(Class<T> type) {
		this.type = type;
	}

	private String createQuery(String field) {
		StringBuilder string = new StringBuilder();
		string.append("SELECT * FROM ");
		string.append(type.getSimpleName());
		string.append(" WHERE " + field + " =?;");
		return string.toString();

	}

	private String selectAll() {
		StringBuilder string = new StringBuilder();
		string.append("SELECT * FROM ");
		string.append(type.getSimpleName());
		return string.toString();
	}

	/*
	 * private String insertInto(int a,String b,String c,String d,String e) {
	 * StringBuilder string = new StringBuilder(); string.append("INSERT INTO ");
	 * string.append(type.getSimpleName()); string.append(" VALUES(");
	 * string.append(a+", "+b+", "+c+", "+d+", "+e); string.append(");"); return
	 * string.toString(); }
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
		System.out.println("HULA:" + string);
		return string.toString();
	}

	private String deleteFrom(String condition) {
		StringBuilder string = new StringBuilder();
		string.append("DELETE FROM ");
		string.append(type.getSimpleName());
		string.append(" WHERE ");
		string.append(condition + ";");
		return string.toString();
	}

	public T findById(int ID, String field) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createQuery(field);
		connection = DBConnection.getConnection();
		T obj = null;
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, ID);
			resultSet = statement.executeQuery();
			obj = createObjects(resultSet).get(0);
		} catch (SQLException e) {
			System.out.println("Error findByID");
			e.printStackTrace();
		} finally {
			System.out.println("SFAFSFSAA");
			DBConnection.close(connection);
			DBConnection.close(statement);
			DBConnection.close(resultSet);
		}
		return obj;
	}

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
			System.out.println("Error findByID");
			e.printStackTrace();
		} finally {
			System.out.println("SFAFSFSAA");
			DBConnection.close(connection);
			DBConnection.close(statement);
			DBConnection.close(resultSet);
		}
		return obj;
	}

	/*
	 * public void insert(int a,String b,String c,String d,String e) throws
	 * SQLException { Connection connection = null; PreparedStatement statement =
	 * null; int result = 0; String query = insertInto(a,b,c,d,e); connection =
	 * DBConnection.getConnection(); try { statement =
	 * connection.prepareStatement(query); result = statement.executeUpdate(query);
	 * System.out.println("INSERT: "+result); } catch (SQLException ex) {
	 * System.out.println("Error findByID"); ex.printStackTrace(); } finally {
	 * System.out.println("SFAFSFSAA"); DBConnection.close(connection);
	 * DBConnection.close(statement); } }
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
			System.out.println("UPDATE: " + result);
			flag = 1;
		} catch (SQLIntegrityConstraintViolationException exception) {
			System.out.println("Error! Duplicate VALUE on UPDATE!");
		} catch (SQLException ex) {
			System.out.println("Error findByID");
			ex.printStackTrace();
		} finally {
			System.out.println("SFAFSFSAA");
			DBConnection.close(connection);
			DBConnection.close(statement);
		}
		if (flag == 1)
			return result;
		else
			return flag;
	}

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
			System.out.println("DELETE: " + result);
		} /*
			 * catch (SQLIntegrityConstraintViolationException exception) {
			 * System.out.println("Error! Duplicate VALUE on UPDATE!"); }
			 */catch (SQLException ex) {
			System.out.println("Error findByID");
			ex.printStackTrace();
		} finally {
			DBConnection.close(connection);
			DBConnection.close(statement);
		}
		if (flag == 1)
			return result;
		else
			return flag;
	}

	@SuppressWarnings("deprecation")
	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		try {
			while (resultSet.next()) {
				T instance = type.newInstance();
				System.out.println("HEYHO" + instance.getClass().getSimpleName());
				for (Field field : type.getDeclaredFields()) {
					System.out.println("FIELD" + field.getName());
					Object value = resultSet.getObject(field.getName());
					System.out.println("VALUE: " + value);
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
					Method method = propertyDescriptor.getWriteMethod();
					System.out.println("METODA" + method.getName());
					method.invoke(instance, value);
				}
				list.add(instance);
				System.out.println(instance.toString());
			}
			return list;
		}
		/*
		 * catch(InstantiationException e) { e.printStackTrace(); } catch
		 * (IllegalAccessException e) { e.printStackTrace(); } catch (SecurityException
		 * e) { e.printStackTrace(); } catch (IllegalArgumentException e) {
		 * e.printStackTrace(); } catch (InvocationTargetException e) {
		 * e.printStackTrace(); } catch (SQLException e) { e.printStackTrace(); } catch
		 * (IntrospectionException e) { e.printStackTrace(); }
		 */
		catch (Exception e) {
			System.out.println("Error! CreateObjects returns List<T>");
		}
		return null;
	}

	public List<String> retrieveProperties() {
		List<String> fieldsList = new ArrayList<String>();
		for (Field field : this.type.getDeclaredFields()) {
			fieldsList.add(field.getName());
			System.out.println("FIELD: " + field.getName());
		}
		System.out.println("THIS : " + this.type);
		return fieldsList;
	}

	public T retrievePropertiesValues(T object) {
		T value = null;
		for (Field field : this.type.getDeclaredFields()) {
			field.setAccessible(true);
			try {
				value = (T) field.get(object);
				System.out.println(field.getName() + " = " + value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return value;
	}
}
