package lk.bhanuka.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * Created by bhanuka on 12/10/16.
 */
public class MySQLDataAdapter implements DataAdapter {

	private String databaseName = "epidemic";
	private String username = "databaseProject";
	private String hostName = "localhost";
	private String password = "P@ssword123";
	private int portNumber = 3306;
	private Connection databaseConnection;

	public MySQLDataAdapter() {

		try {

			Class.forName("com.mysql.jdbc.Driver");

			this.databaseConnection = DriverManager.getConnection(
					"jdbc:mysql://" + this.hostName + ":" + Integer.toString(this.portNumber) + "/" + this.databaseName,
					this.username, this.password);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Class not found ");
		}
	}

	public List query(String query) {
		try {
			Statement statement = this.databaseConnection.createStatement();

			System.out.println(query);

			return this.resultsToList(statement.executeQuery(query));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List action(String action, String table, ArrayList<String> conditions) {

		String processedConditions = "";

		for (String condition : conditions) {
			processedConditions = condition + " AND ";
		}

		processedConditions = processedConditions + " '1' = '1' ";

		return this.query(action + " From " + table + " where " + processedConditions);
	}

	public List get(String table, ArrayList<String> conditions) {

		return this.action(" select * ", table, conditions);

	}

	public List insert(String table, HashMap values) {

		// TODO - Refactor to return auto incrment fields with the state of the
		// query in result set - use database approach
		List returnList = null;

		System.out.println("Data adapter insert invoked");

		String query = "Insert into " + table + "(";

		String data = " ) values (";

		Object[] keys = values.keySet().toArray();

		for (int i = 0; i < keys.length; i++) {

			query = query + keys[i].toString();

			if (values.get(keys[i]) != null) {
				if (values.get(keys[i]).getClass().equals(String.class)) {
					data = data + "'" + values.get(keys[i]) + "'";
				} else {
					data = data + values.get(keys[i]);
				}
			} else {
				data = data + values.get(keys[i]);
			}

			if (i != (keys.length - 1)) {

				query = query + " , ";

				data = data + " , ";
			}

		}

		query = query + data + " ) ";

		System.out.println(query);

		try {
			Statement statement = this.databaseConnection.createStatement();

			statement.executeUpdate(query, statement.RETURN_GENERATED_KEYS);

			ResultSet results = statement.getResultSet();

			returnList = resultsToList(results);

		}

		catch (MySQLIntegrityConstraintViolationException e) {
			ArrayList<String> errors = new ArrayList<String>();

			errors.add("Duplicate Key");
			// System.out.println(errors);
			returnList = errors;
		}

		catch (SQLException e) {
			e.printStackTrace();

		}
		return returnList;

	}

	private List resultsToList(ResultSet results) throws SQLException {

		if (results != null) {
			ResultSetMetaData md = results.getMetaData();

			int columns = md.getColumnCount();

			ArrayList list = new ArrayList();

			while (results.next()) {

				HashMap row = new HashMap(columns);

				for (int i = 1; i <= columns; ++i) {

					row.put(md.getColumnName(i), results.getObject(i));

				}

				list.add(row);
			}

			return list;
		}
		return null;
	}

}
