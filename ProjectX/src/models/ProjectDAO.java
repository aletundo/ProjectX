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

	public int addProject(ProjectBean project) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String addProjectQuery = "INSERT INTO project (name, budget, goals, requirements, subjectAreas, "
					+ "estimatedDuration, estimatedCosts, start, deadline, idProjectManager, idClient) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			try {
				statement = currentConn.prepareStatement(addProjectQuery, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, project.getName());
				statement.setDouble(2, project.getBudget());
				statement.setString(3, project.getGoals());
				statement.setString(4, project.getRequirements());
				statement.setString(5, project.getSubjectAreas());
				statement.setInt(6, project.getEstimatedDuration());
				statement.setDouble(7, project.getEstimatedCosts());
				statement.setString(8, project.getStart());
				statement.setString(9, project.getDeadline());
				statement.setInt(10, project.getIdProjectManager());
				statement.setInt(11, project.getIdClient());
				statement.executeUpdate();
				rs = statement.getGeneratedKeys();
				while (rs.next())
					project.setIdProject(rs.getInt(1));
				
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO Handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		return project.getIdProject();
	}

	public List<ProjectBean> getUserProjects(UserBean user) {
		List<ProjectBean> projectList = new ArrayList<ProjectBean>();
		Connection currentConn = DbConnection.connect();
		Statement statement = null;
		ResultSet rs = null;
		final String getUserProjectsQuery = "SELECT P.idProject AS IdProject, P.name AS Name, U.name AS ProjectManager, C.name AS client "
					+ "FROM project AS P JOIN user AS U ON P.idProjectManager = U.idUser "
					+ "JOIN client AS C ON P.idClient = C.idClient "
					+ "JOIN userprojects AS UP ON P.idProject = UP.idProject";
		final String dropView = "DROP VIEW userprojects";

		if (currentConn != null) {
			
			String createView = buildGetProjectsQueries(user.getType(), user.getIdUser());
			
			/*getUserProjectQuery = "SELECT P.idProject AS IdProject, P.name AS Name, U.name AS ProjectManager, C.name AS client "
					+ "FROM project AS P JOIN user AS U ON P.idProjectManager = U.idUser JOIN client AS C ON P.idClient = C.idClient "
					+ "WHERE P.idProject =(SELECT P2.idProject AS IdProject2 "
					+ "FROM user AS U2 JOIN stage AS S ON U2.idUser = S.idSupervisor JOIN project AS P2 ON S.idProject = P2.idProject "
					+ "WHERE U2.idUser = ?) " + "UNION "
					+ "(SELECT P.idProject AS IdProject, P.name AS Name, U.name AS ProjectManager, C.name AS client "
					+ "FROM project AS P JOIN user AS U ON P.idProjectManager = U.idUser "
					+ "JOIN client AS C ON P.idClient = C.idClient WHERE U.idUser = ?)";*/
			try {
				statement = currentConn.createStatement();
				statement.executeUpdate(createView);
				
				rs = statement.executeQuery(getUserProjectsQuery);
				while (rs.next()) {
					ProjectBean project = new ProjectBean();
					project.setIdProject(rs.getInt("IdProject"));
					project.setName(rs.getString("Name"));
					project.setPmName(rs.getString("ProjectManager"));
					project.setClientName(rs.getString("Client"));
					projectList.add(project);
				}
				statement.executeUpdate(dropView);
				
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO Handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		return projectList;
	}
	
	public List<ProjectBean> searchProjects(String subjectArea) {
		List<ProjectBean> projectList = new ArrayList<ProjectBean>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String getRelatedProjectsQuery = "SELECT P.idProject AS IdProject, P.name AS Name, U.name AS ProjectManager "
					+ "FROM project AS P JOIN user AS U ON P.idProjectManager = U.idUser "
					+ "WHERE P.subjectAreas LIKE ?";
			try {
				statement = currentConn.prepareStatement(getRelatedProjectsQuery);
				statement.setString(1, "%" + subjectArea + "%");
				rs = statement.executeQuery();
				while (rs.next()) {
					ProjectBean project = new ProjectBean();
					project.setIdProject(rs.getInt("IdProject"));
					project.setName(rs.getString("Name"));
					project.setPmName(rs.getString("ProjectManager"));
					projectList.add(project);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				// TODO Handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		return projectList;
	}
	
	private String buildGetProjectsQueries(String userType, int idUser){
		String view = "";
		if(userType.equals("ProjectManager")){
			view = "CREATE VIEW userprojects AS SELECT DISTINCT P.idProject AS idProject "
					+ "FROM user AS U JOIN stage AS S ON U.idUser = S.idSupervisor "
					+ "JOIN project AS P ON S.idProject = P.idProject  WHERE U.idUser = " + idUser
					+ " UNION SELECT P.idProject AS idProject "
					+ "FROM project AS P WHERE P.idProjectManager = " + idUser;
			
		}else if(userType.equals("Senior")){
			view = "CREATE VIEW userprojects AS SELECT DISTINCT P.idProject AS idProject "
					+ "FROM user AS U JOIN stage AS S ON U.idUser = S.idSupervisor "
					+ "JOIN project AS P ON S.idProject = P.idProject  WHERE U.idUser = " + idUser
					+ " UNION SELECT DISTINCT P.idProject AS idProject "
					+ "FROM user AS U JOIN taskdevelopment AS TD ON U.idUser = TD.idDeveloper "
					+ "JOIN task AS T ON TD.idTask = T.idTask "
					+ "JOIN stage AS S ON T.idStage = S.idStage "
					+ "JOIN project AS P ON S.idProject = P.idProject WHERE U.idUser = " + idUser;
				
			
		}else if(userType.equals("Junior")){
			view = "CREATE VIEW userprojects AS SELECT DISTINCT P.idProject AS idProject "
					+ "FROM user AS U JOIN taskdevelopment AS TD ON U.idUser = TD.idDeveloper "
					+ "JOIN task AS T ON TD.idTask = T.idTask "
					+ "JOIN stage AS S ON T.idStage = S.idStage "
					+ "JOIN project AS P ON S.idProject = P.idProject WHERE U.idUser = " + idUser;
		}
		return view;
	}
}