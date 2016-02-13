package utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import utils.GetDbConnProperties;

public class DbConnection {
	public static Connection connect() {

		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		
		String dbUrl = "";
		// Database credentials
		String user = "";
		String pass = "";

		// Get Properties
		GetDbConnProperties properties = new GetDbConnProperties();

		try {
			String[] propertiesValues = properties.getPropValues();
			dbUrl = propertiesValues[0];
			user = propertiesValues[1];
			pass = propertiesValues[2];
		} catch (IOException e1) {
			e1.printStackTrace();
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

	public static void disconnect(Connection conn) {

		try {
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			//TODO Handles errors in a Logger
			se.printStackTrace();
		}
	}
}
