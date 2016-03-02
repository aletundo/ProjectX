package utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.GetDbConnProperties;

public class DbConnection {

    private static final Logger LOGGER = Logger.getLogger(DbConnection.class.getName());

    private DbConnection() {

    }

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
            LOGGER.log(Level.SEVERE, "Something went wrong during getting db properties values", e1);
        }

        Connection conn = null;

        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            conn = DriverManager.getConnection(dbUrl, user, pass);

        } catch (SQLException se) {
            LOGGER.log(Level.SEVERE, "Something went wrong during getting connection", se);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during getting Class JDBC_DRIVER", e);
        }
        return conn;

    }

    public static void disconnect(Connection conn, ResultSet rs, PreparedStatement statement) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during getting connection", e);
        }
        disconnect(conn, statement);
    }

    public static void disconnect(Connection conn, PreparedStatement statement) {
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during getting connection", e);
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException se) {
            LOGGER.log(Level.SEVERE, "Something went wrong during getting connection", se);
        }
    }

    public static void disconnect(Connection conn, Statement statement) {
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during getting connection", e);
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException se) {
            LOGGER.log(Level.SEVERE, "Something went wrong during getting connection", se);
        }
    }

    public static void disconnect(Connection conn, ResultSet rs, Statement statement) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during getting connection", e);
        }
        disconnect(conn, statement);
    }
}
