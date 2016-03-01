package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.DbConnection;

public class ProjectDAO {

	private static final ProjectDAO INSTANCE = new ProjectDAO();
	private static final Logger LOGGER = Logger.getLogger(ProjectDAO.class.getName());

	private ProjectDAO() {

	}

	public static ProjectDAO getInstance() {

		return INSTANCE;

	}
	
	public void updateProject(ProjectBean project){
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String updateProjectQuery = "UPDATE project AS P SET P.name = ?, P.budget = ?, P.goals = ?, P.requirements = ?, "
					+ "P.subjectAreas = ?, P.estimatedCosts = ?, P.start = ?, P.deadline = ?, P.idClient = ? "
					+ "WHERE P.idProject = ? ";
			try {
				statement = currentConn.prepareStatement(updateProjectQuery);
				statement.setString(1, project.getName());
				statement.setDouble(2, project.getBudget());
				statement.setString(3, project.getGoals());
				statement.setString(4, project.getRequirements());
				statement.setString(5, project.getSubjectAreas());
				statement.setDouble(6, project.getEstimatedCosts());
				statement.setString(7, project.getStart());
				statement.setString(8, project.getDeadline());
				statement.setInt(9, project.getIdClient());
				statement.setInt(10, project.getIdProject());
				statement.executeUpdate();

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE,
						"Something went wrong during updating project " + project.getIdProject(), e);
			} finally {
				DbConnection.disconnect(currentConn, statement);
			}
		}
	}

	public float getRateWorkCompleted(int idProject) {
		float rateWorkCompleted = 0;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String getRateworkCompletedQuery = "SELECT P.rateWorkCompleted AS RateWorkCompleted FROM project AS P "
					+ "WHERE P.idProject = ? ";
			try {
				statement = currentConn.prepareStatement(getRateworkCompletedQuery);
				statement.setInt(1, idProject);
				rs = statement.executeQuery();
				while (rs.next()) {
					rateWorkCompleted = rs.getFloat("RateWorkCompleted");
				}

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE,
						"Something went wrong during getting rate work completed of project " + idProject, e);
			} finally {
				DbConnection.disconnect(currentConn, statement);
			}
		}

		return rateWorkCompleted;
	}

	public void setRateWorkCompleted(int idProject, float rate) {
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String setRateWorkCompletedQuery = "UPDATE project AS P SET P.rateWorkCompleted = ? "
					+ "WHERE P.idProject = ? ";
			try {
				statement = currentConn.prepareStatement(setRateWorkCompletedQuery);
				statement.setFloat(1, rate);
				statement.setInt(2, idProject);
				statement.executeUpdate();

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE,
						"Something went wrong during setting rate work completed of project " + idProject, e);
			} finally {
				DbConnection.disconnect(currentConn, statement);
			}
		}
	}

	public int getProjectManagerId(int idProject) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		int idProjectManager = 0;
		Connection currentConn = DbConnection.connect();
		if (currentConn != null) {
			final String getProjectManagerQuery = "SELECT P.idProjectManager AS IdProjectManager "
					+ "FROM project AS P WHERE P.idProject = ?";
			try {
				statement = currentConn.prepareStatement(getProjectManagerQuery);
				statement.setInt(1, idProject);
				rs = statement.executeQuery();
				while (rs.next()) {
					idProjectManager = rs.getInt("IdProjectManager");
				}
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE,
						"Something went wrong during getting project manager of project " + idProject, e);
			}finally{
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		return idProjectManager;
	}

	public ProjectBean getProjectInfo(int idProject) {
		ProjectBean projectInfo = new ProjectBean();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String getProjectInfoQuery = "SELECT P.idProject as IdProject, P.name AS ProjectName,"
					+ " P.rateWorkCompleted AS RateWorkCompleted, P.start AS Start, P.deadline AS Deadline, "
					+ "P.goals AS Goals, P.requirements AS Requirements, P.budget AS Budget, P.estimatedCosts AS EstimatedCosts, P.subjectAreas AS SubjectAreas, C.name AS ClientName "
					+ "FROM project AS P JOIN client AS C ON P.idClient = C.idClient  WHERE P.idProject = ?";
			try {
				statement = currentConn.prepareStatement(getProjectInfoQuery);
				statement.setInt(1, idProject);

				rs = statement.executeQuery();
				while (rs.next()) {
					projectInfo.setIdProject(Integer.parseInt(rs.getString("IdProject")));
					projectInfo.setName(rs.getString("ProjectName"));
					projectInfo.setStart(rs.getString("Start"));
					projectInfo.setRateWorkCompleted(rs.getFloat("RateWorkCompleted"));
					projectInfo.setDeadline(rs.getString("Deadline"));
					projectInfo.setGoals(rs.getString("Goals"));
					projectInfo.setRequirements(rs.getString("Requirements"));
					projectInfo.setBudget(rs.getDouble("Budget"));
					projectInfo.setEstimatedCosts(rs.getDouble("EstimatedCosts"));
					projectInfo.setSubjectAreas(rs.getString("SubjectAreas"));
					projectInfo.setClientName(rs.getString("ClientName"));
				}

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE,
						"Something went wrong during getting details of project " + idProject, e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		return projectInfo;
	}
	
	public boolean checkNameAlreadyExist(String name){
		PreparedStatement statement = null;
		ResultSet rs = null;
		boolean exist = false;
		Connection currentConn = DbConnection.connect();
		
		if(currentConn != null){
			final String checkNameQuery = "SELECT COUNT(*) FROM project AS P WHERE P.name LIKE ?";
			try{
				statement = currentConn.prepareStatement(checkNameQuery);
				statement.setString(1, name);
				rs = statement.executeQuery();
				rs.last();
				if(rs.getInt("COUNT(*)") > 0){
					exist = true;
				}		
			}catch(SQLException e){
				LOGGER.log(Level.SEVERE,
						"Something went wrong during checking if " + name + " project already exist", e);
			}finally{
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		
		return exist;
	}

	public int addProject(ProjectBean project) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();
		int idProject = Integer.MIN_VALUE;

		if (currentConn != null) {
			final String addProjectQuery = "INSERT INTO project (name, budget, goals, requirements, subjectAreas, "
					+ "estimatedCosts, start, deadline, idProjectManager, idClient) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			try {
				statement = currentConn.prepareStatement(addProjectQuery, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, project.getName());
				statement.setDouble(2, project.getBudget());
				statement.setString(3, project.getGoals());
				statement.setString(4, project.getRequirements());
				statement.setString(5, project.getSubjectAreas());
				statement.setDouble(6, project.getEstimatedCosts());
				statement.setString(7, project.getStart());
				statement.setString(8, project.getDeadline());
				statement.setInt(9, project.getIdProjectManager());
				statement.setInt(10, project.getIdClient());
				statement.executeUpdate();
				rs = statement.getGeneratedKeys();
				while (rs.next())
					idProject = rs.getInt(1);
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE,
						"Something went wrong during creation of project " + project.toString(), e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		return idProject;
	}

	public List<ProjectBean> getUserProjects(UserBean user) {
		List<ProjectBean> projectList = new ArrayList<>();
		Connection currentConn = DbConnection.connect();
		Statement statement = null;
		ResultSet rs = null;
		final String getUserProjectsQuery = "SELECT P.idProject AS IdProject, P.name AS Name, U.fullname AS ProjectManager, C.name AS client "
				+ "FROM project AS P JOIN user AS U ON P.idProjectManager = U.idUser "
				+ "JOIN client AS C ON P.idClient = C.idClient "
				+ "JOIN userprojects AS UP ON P.idProject = UP.idProject";
		final String dropView = "DROP VIEW userprojects";

		if (currentConn != null) {

			String createView = buildGetProjectsQueries(user.getType(), user.getIdUser());

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
				LOGGER.log(Level.SEVERE,
						"Something went wrong during getting all project which " + user.toString() + " is involved in", e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		return projectList;
	}

	public List<ProjectBean> searchProjects(String subjectArea) {
		List<ProjectBean> projectList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String getRelatedProjectsQuery = "SELECT P.idProject AS IdProject, P.name AS Name, U.fullname AS ProjectManager "
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
				LOGGER.log(Level.SEVERE,
						"Something went wrong during getting project related to " + subjectArea, e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		return projectList;
	}

	private static String buildGetProjectsQueries(String userType, int idUser) {
		String view = "";
		if ("ProjectManager".equals(userType)) {
			view = "CREATE VIEW userprojects AS SELECT DISTINCT P.idProject AS idProject "
					+ "FROM user AS U JOIN stage AS S ON U.idUser = S.idSupervisor "
					+ "JOIN project AS P ON S.idProject = P.idProject  WHERE U.idUser = " + idUser
					+ " UNION SELECT P.idProject AS idProject " + "FROM project AS P WHERE P.idProjectManager = "
					+ idUser;

		} else if ("Senior".equals(userType)) {
			view = "CREATE VIEW userprojects AS SELECT DISTINCT P.idProject AS idProject "
					+ "FROM user AS U JOIN stage AS S ON U.idUser = S.idSupervisor "
					+ "JOIN project AS P ON S.idProject = P.idProject  WHERE U.idUser = " + idUser
					+ " UNION SELECT DISTINCT P.idProject AS idProject "
					+ "FROM user AS U JOIN taskdevelopment AS TD ON U.idUser = TD.idDeveloper "
					+ "JOIN task AS T ON TD.idTask = T.idTask " + "JOIN stage AS S ON T.idStage = S.idStage "
					+ "JOIN project AS P ON S.idProject = P.idProject WHERE U.idUser = " + idUser;

		} else if ("Junior".equals(userType)) {
			view = "CREATE VIEW userprojects AS SELECT DISTINCT P.idProject AS idProject "
					+ "FROM user AS U JOIN taskdevelopment AS TD ON U.idUser = TD.idDeveloper "
					+ "JOIN task AS T ON TD.idTask = T.idTask " + "JOIN stage AS S ON T.idStage = S.idStage "
					+ "JOIN project AS P ON S.idProject = P.idProject WHERE U.idUser = " + idUser;
		}
		return view;
	}
}