package utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utils.GetDbConnProperties;

public class DbConnection {
	public static Connection connect() {

		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		
		String dbUrl = "";
		// Database credentials
		String user = "";
		String pass = "";

		try {
			String[] propertiesValues = GetDbConnProperties.getInstance().getPropValues();
			dbUrl = propertiesValues[0];
			user = propertiesValues[1];
			pass = propertiesValues[2];
		} catch (IOException e1) {
			e1.printStackTrace();
			//TODO Handle with a Logger
		}

		Connection conn = null;

		try {
			//Register JDBC driver
			Class.forName(JDBC_DRIVER);

			//Open a connection
			conn = DriverManager.getConnection(dbUrl, user, pass);

		} catch (SQLException se) {
			//TODO Handle errors for JDBC in a Logger
			se.printStackTrace();
		} catch (Exception e) {
			//TODO Handle errors for Class.forName in a Logger
			e.printStackTrace();
		}
		return conn;

	}

	public static void disconnect(Connection conn, ResultSet rs, PreparedStatement statement) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			/* ignored */ }
		disconnect(conn, statement);
	}
	
	public static void disconnect(Connection conn, PreparedStatement statement) {
		try {
			if(statement != null)
				statement.close();
		} catch (SQLException e) {
			/* ignored */ }
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			//Ignored
		}
	}
	
	public static void disconnect(Connection conn, Statement statement) {
		try {
			if(statement != null)
				statement.close();
		} catch (SQLException e) {
			/* ignored */ }
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			//Ignored
		}
	}
	
	public static void disconnect(Connection conn, ResultSet rs, Statement statement) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			/* ignored */ }
		disconnect(conn, statement);
	}
}
