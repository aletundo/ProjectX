package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.DbConnection;

public class PhysicalResourceDAO {
	private static final PhysicalResourceDAO INSTANCE = new PhysicalResourceDAO();
	private static final Logger LOGGER = Logger.getLogger(PhysicalResourceDAO.class.getName());

	private PhysicalResourceDAO() {

	}

	public static PhysicalResourceDAO getInstance() {

		return INSTANCE;

	}

	public List<PhysicalResourceBean> getAvailableResources() {
		List<PhysicalResourceBean> availableResources = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String getResourcesQuery = "SELECT PR.idPhysicalResource AS IdResource, PR.type AS Type, PR.quantity AS Quantity FROM physicalresource AS PR WHERE PR.quantity > 0";
			try {
				statement = currentConn.prepareStatement(getResourcesQuery);
				rs = statement.executeQuery();
				while(rs.next()){
					PhysicalResourceBean resource = new PhysicalResourceBean();
					resource.setIdPhysicalResource(rs.getInt("IdResource"));
					resource.setType(rs.getString("Type"));
					resource.setQuantity(rs.getInt("Quantity"));
					availableResources.add(resource);
				}

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during getting available resources", e);
			} finally {
				DbConnection.disconnect(currentConn, statement);
			}
		}

		return availableResources;
	}

}