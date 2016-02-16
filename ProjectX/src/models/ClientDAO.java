package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utils.DbConnection;

public class ClientDAO {

	private static final ClientDAO INSTANCE = new ClientDAO();

	private ClientDAO() {

	}

	public static ClientDAO getInstance() {

		return INSTANCE;

	}

	public int addClient(ClientBean client) {
		Connection currentConn = DbConnection.connect();
		PreparedStatement statement = null;
		ResultSet rs = null;

		if (currentConn != null) {
			final String addClientQuery = "INSERT INTO Client (name, mail) VALUES(?, ?)";
			try {
				statement = currentConn.prepareStatement(addClientQuery, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, client.getName());
				statement.setString(2, client.getMail());
				statement.executeUpdate();
				rs = statement.getGeneratedKeys();
				while (rs.next())
					client.setIdClient(rs.getInt(1));
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO Handle with a Logger
			} finally {
				try {
					rs.close();
				} catch (Exception e) {
					/* ignored */ }
				try {
					statement.close();
				} catch (Exception e) {
					/* ignored */ }
				DbConnection.disconnect(currentConn);
			}
		}
		System.out.println(client.getIdClient());
		return client.getIdClient();
	}

}
