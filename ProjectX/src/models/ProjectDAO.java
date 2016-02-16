package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import utils.DbConnection;

public class ProjectDAO {

	private static final ProjectDAO INSTANCE = new ProjectDAO();

	private ProjectDAO() {

	}

	public static ProjectDAO getInstance() {

		return INSTANCE;

	}

	public List<ProjectBean> getUserProjects(UserBean user) {
		List<ProjectBean> projectList = new ArrayList<ProjectBean>();
		Connection currentConn = DbConnection.connect();
		PreparedStatement statement = null;
		ResultSet rs = null;

		if (currentConn != null) {
			final String getProjectQuery = "SELECT P.idProject AS IdProject, P.name AS Name, U.name AS ProjectManager, C.name AS Client "
					+ "FROM Project AS P JOIN User AS U ON P.idProjectManager = U.idUser JOIN Client AS C ON P.idClient = C.idClient "
					+ "WHERE P.idProject =(SELECT P2.idProject AS IdProject2 "
					+ "FROM User AS U2 JOIN Stage AS S ON U2.idUser = S.idSupervisor JOIN Project AS P2 ON S.idProject = P2.idProject "
					+ "WHERE U2.idUser = ?) " + "UNION "
					+ "(SELECT P.idProject AS IdProject, P.name AS Name, U.name AS ProjectManager, C.name AS Client "
					+ "FROM Project AS P JOIN User AS U ON P.idProjectManager = U.idUser "
					+ "JOIN Client AS C ON P.idClient = C.idClient WHERE U.idUser = ?)";
			try {
				statement = currentConn.prepareStatement(getProjectQuery);
				int idUser = user.getIdUser();
				statement.setInt(1, idUser);
				statement.setInt(2, idUser);
				rs = statement.executeQuery();
				while (rs.next()) {
					ProjectBean project = new ProjectBean();
					project.setIdProject(rs.getInt("IdProject"));
					project.setName(rs.getString("Name"));
					project.setPmName(rs.getString("ProjectManager"));
					project.setClientName(rs.getString("Client"));
					projectList.add(project);
				}

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
		return projectList;
	}
}