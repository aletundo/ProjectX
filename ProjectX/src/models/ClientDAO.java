package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DbConnection;

public class ClientDAO {

	private static final ClientDAO INSTANCE = new ClientDAO();
	private static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());

	private ClientDAO() {

	}

	public static ClientDAO getInstance() {

		return INSTANCE;

	}
	
	public String getClientMail(int idProject){
		
		PreparedStatement statement = null;
		ResultSet rs = null;
		String mail = "";
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String getUserMailQuery = "SELECT C.mail AS Mail "
					+ "FROM client AS C JOIN project AS P ON C.idClient = P.idClient WHERE P.idProject = ?";
			try {
				statement = currentConn.prepareStatement(getUserMailQuery);
				statement.setInt(1, idProject);
				rs = statement.executeQuery();
				while (rs.next()) {
					mail = rs.getString("Mail");
				}
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Get client mail of project " + idProject + " fail.", e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return mail;
	}

	public List<ClientBean> getRelatedClients(String subjectArea) {
		List<ClientBean> clients = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String getRelatedClientsQuery = "SELECT C.idClient AS IdClient, C.name AS Name, C.mail As Mail "
					+ "FROM client AS C "
					+ "JOIN project AS P ON C.idClient = P.idClient WHERE P.subjectAreas LIKE ?";
			try {
				statement = currentConn.prepareStatement(getRelatedClientsQuery);
				statement.setString(1, "%" + subjectArea + "%");
				rs = statement.executeQuery();

				while (rs.next()) {
					ClientBean client = new ClientBean();
					client.setIdClient(rs.getInt("IdClient"));
					client.setName(rs.getString("Name"));
					client.setMail(rs.getString("Mail"));
					clients.add(client);
				}
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Get related clients to '" + subjectArea + "' fails.", e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return clients;
	}
	
	public int getClientByName(String clientName){
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();
		int idClient = Integer.MIN_VALUE;
		if (currentConn != null) {
			final String addClientQuery = "SELECT C.idClient AS IdClient FROM client AS C WHERE C.name LIKE ?";
			try {
				statement = currentConn.prepareStatement(addClientQuery, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, clientName);
				rs = statement.executeQuery();
				while (rs.next())
					idClient = rs.getInt("IdClient");
					
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Get client " + clientName + " fails.", e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		return idClient;
		
	}

	public int addClient(ClientBean client) {
	
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();
		if (currentConn != null) {
			final String addClientQuery = "INSERT INTO client (name, mail) VALUES(?, ?)";
			try {
				statement = currentConn.prepareStatement(addClientQuery, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, client.getName());
				statement.setString(2, client.getMail());
				statement.executeUpdate();
				rs = statement.getGeneratedKeys();
				while (rs.next())
					client.setIdClient(rs.getInt(1));
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Add client " + client.toString() + " fail.", e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		return client.getIdClient();
	}

}
