package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.UserBean;
import utils.DbConnection;

public class UserDAO {

	private static final UserDAO INSTANCE = new UserDAO();

	private UserDAO() {

	}

	public static UserDAO getInstance() {

		return INSTANCE;
	}
	
	public List<Integer> getAllUsersInvolvedByProject(int idProject){
		List<Integer> usersInvolved = new ArrayList<Integer>();
		
		usersInvolved.add(ProjectDAO.getInstance().getProjectManagerId(idProject));
		
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();
		
		if (currentConn != null) {
			final String getSupervisorsId = "SELECT DISTINCT S.idSupervisor AS IdSupervisor "
					+ "FROM stage AS S JOIN project AS P ON S.idProject = ?";
			
			final String getDevelopersId = "SELECT DISTINCT TD.idDeveloper AS IdDeveloper "
					+ "FROM taskdevelopment AS TD JOIN task AS T ON TD.idTask = T.idTask JOIN stage AS S ON T.idStage = S.idStage "
					+ "JOIN project AS P ON S.idProject = ?";
			
			try {
				statement = currentConn.prepareStatement(getSupervisorsId);
				statement.setInt(1, idProject);
				rs = statement.executeQuery();
				while (rs.next()) {
					usersInvolved.add(rs.getInt("IdSupervisor"));
				}
				rs.close();
				statement.close();

				statement = currentConn.prepareStatement(getDevelopersId);
				statement.setInt(1, idProject);
				rs = statement.executeQuery();
				while (rs.next()) {
					usersInvolved.add(rs.getInt("IdDeveloper"));
				}
				rs.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO Handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		return usersInvolved;
	}
	
	public List<Integer> getAllUsersInvolvedByStage(int idStage){
		int idProject = 0;
		List<Integer> usersInvolved = new ArrayList<Integer>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();
	
		if (currentConn != null) {
			final String getIdProject = "SELECT P.idProject AS IdProject "
					+ "FROM stage AS S JOIN project AS P ON S.idProject  = P.idProject " + "WHERE S.idStage = ?";

			try {
				statement = currentConn.prepareStatement(getIdProject);
				statement.setInt(1, idStage);
				rs = statement.executeQuery();
				while (rs.next()) {
					idProject = rs.getInt("IdProject");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO Handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		usersInvolved = getAllUsersInvolvedByProject(idProject);
		return usersInvolved;
	}
	
	public List<Integer> getAllUsersInvolvedByTask(int idTask){
		int idProject = 0;
		List<Integer> usersInvolved = new ArrayList<Integer>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();
	
		if (currentConn != null) {
			final String getIdProject = "SELECT P.idProject AS IdProject "
					+ "FROM task AS T JOIN stage AS S ON T.idStage = S.idStage JOIN project AS P ON S.idProject  = P.idProject "
					+ "WHERE T.idTask = ?";

			try {
				statement = currentConn.prepareStatement(getIdProject);
				statement.setInt(1, idTask);
				rs = statement.executeQuery();
				while (rs.next()) {
					idProject = rs.getInt("IdProject");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO Handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		usersInvolved = getAllUsersInvolvedByProject(idProject);
		return usersInvolved;
	}
	
	public String getProjectManagerMailByIdStage(int idStage){
		String mail = "";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();
		
		if (currentConn != null) {
			final String getUserMailQuery = "SELECT U.mail AS Mail FROM user AS U JOIN project AS P ON U.idUser = P.idProjectManager JOIN stage AS S ON S.idProject = P.idProject WHERE S.idStage = ?";
			try {
				statement = currentConn.prepareStatement(getUserMailQuery);
				statement.setInt(1, idStage);
				rs = statement.executeQuery();
				while (rs.next()) {
					mail = rs.getString("Mail");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO Handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		
		return mail;
	}

	public List<String> getAllDevelopersMail(int idStage) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> developersMail = new ArrayList<String>();
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String getUserMailQuery = "SELECT U.mail AS Mail "
					+ "FROM user AS U JOIN taskdevelopment AS TD ON U.idUser = TD.idDeveloper "
					+ "JOIN task AS T ON TD.idTask = T.idTask WHERE T.idStage = ?";
			try {
				statement = currentConn.prepareStatement(getUserMailQuery);
				statement.setInt(1, idStage);
				rs = statement.executeQuery();
				while (rs.next()) {
					developersMail.add(rs.getString("Mail"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO Handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return developersMail;

	}

	public List<String> getAllSupervisorsMail(int idProject) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> supervisorsMail = new ArrayList<String>();
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String getUserMailQuery = "SELECT U.mail AS Mail "
					+ "FROM user AS U JOIN stage AS S ON U.idUser = S.idSupervisor "
					+ "JOIN project AS P ON S.idProject = P.idProject WHERE P.idProject = ?";
			try {
				statement = currentConn.prepareStatement(getUserMailQuery);
				statement.setInt(1, idProject);
				rs = statement.executeQuery();
				while (rs.next()) {
					supervisorsMail.add(rs.getString("Mail"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO Handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return supervisorsMail;

	}

	public String getGenericUserMailById(int idUser) {

		PreparedStatement statement = null;
		ResultSet rs = null;
		String mail = "";
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String getUserMailQuery = "SELECT U.mail AS Mail " + "FROM user AS U WHERE U.idUser = ?";
			try {
				statement = currentConn.prepareStatement(getUserMailQuery);
				statement.setInt(1, idUser);
				rs = statement.executeQuery();
				while (rs.next()) {
					mail = rs.getString("Mail");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO Handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return mail;

	}

	public List<UserBean> getUsersInfo(List<UserBean> candidates) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String getUsersInfoQuery = "SELECT U.idUser AS IdUser, U.fullname AS Fullname, U.type AS Type " + "FROM user AS U WHERE U.idUser = ?";
			try {
				for (UserBean u : candidates) {
					statement = currentConn.prepareStatement(getUsersInfoQuery);
					statement.setInt(1, u.getIdUser());
					rs = statement.executeQuery();
					while (rs.next()) {
						u.setIdUser(rs.getInt("IdUser"));
						u.setFullname(rs.getString("Fullname"));
						u.setType(rs.getString("Type"));
					}
					rs.close();
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO Handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return candidates;
	}

	public Map<Integer, List<Object>> getCandidateSupervisors() {
		Map<Integer, List<Object>> workMap = new HashMap<Integer, List<Object>>();
		ResultSet rs = null;
		Statement statement = null;
		Connection currentConn = DbConnection.connect();
		if (currentConn != null) {
			final String getUsers = "SELECT idUser AS IdUser FROM user " + "WHERE type NOT LIKE 'Junior'";
			final String getActiveProjects = "SELECT U.idUser AS IdUser, P.idProject AS IdProject, P.start AS Start, P.deadline AS Deadline "
					+ "FROM user AS U JOIN project AS P " + "ON U.idUser = P.idProjectManager "
							+ "WHERE  P.rateWorkCompleted < 100";
			final String getActiveStages = "SELECT U.idUser AS IdUser, S.idStage AS IdStage, S.startDay AS StartDay, S.finishDay AS FinishDay "
					+ "FROM user AS U JOIN stage AS S " + "ON U.idUser = S.idSupervisor "
							+ "WHERE S.rateWorkCompleted < 100 AND S.outsourcing LIKE 'False'";
			final String getActiveTasks = "SELECT U.idUser AS IdUser, TD.idTask AS IdTask, T.startDay AS StartDay, T.finishDay AS FinishDay "
					+ "FROM user AS U JOIN taskdevelopment AS TD ON U.idUser = TD.idDeveloper "
					+ "JOIN task AS T ON TD.idTask = T.idTask "
					+ "WHERE TD.workCompleted LIKE 'False' AND U.type NOT LIKE 'Junior'";

			try {
				statement = currentConn.createStatement();
				rs = statement.executeQuery(getUsers);
				while (rs.next()) {
					List<Object> works = new ArrayList<Object>();
					Integer user = new Integer(rs.getInt("IdUser"));
					workMap.put(user, works);
				}
				rs.close();

				rs = statement.executeQuery(getActiveProjects);
				while (rs.next()) {
					ProjectBean project = new ProjectBean();
					project.setIdProject(rs.getInt("IdProject"));
					project.setStart(rs.getString("Start"));
					project.setDeadline(rs.getString("Deadline"));

					Integer user = new Integer(rs.getInt("IdUser"));

					List<Object> updatedValue = workMap.get(user);
					updatedValue.add(project);
					workMap.put(user, updatedValue);
				}
				rs.close();
				rs = statement.executeQuery(getActiveStages);
				while (rs.next()) {
					StageBean stage = new StageBean();
					stage.setIdStage(rs.getInt("IdStage"));
					stage.setStartDay(rs.getString("StartDay"));
					stage.setFinishDay(rs.getString("FinishDay"));

					Integer user = new Integer(rs.getInt("IdUser"));

					List<Object> updatedValue = workMap.get(user);
					updatedValue.add(stage);
					workMap.put(user, updatedValue);
				}
				rs.close();
				rs = statement.executeQuery(getActiveTasks);
				while (rs.next()) {
					TaskBean task = new TaskBean();
					task.setIdTask(rs.getInt("IdTask"));
					task.setStartDay(rs.getString("StartDay"));
					task.setFinishDay(rs.getString("FinishDay"));

					Integer user = new Integer(rs.getInt("IdUser"));

					List<Object> updatedValue = workMap.get(user);
					updatedValue.add(task);
					workMap.put(user, updatedValue);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				// TODO handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		return workMap;
	}


	public Map<Integer, List<Object>> getCandidateDevelopers() {

		Map<Integer, List<Object>> workMap = new HashMap<Integer, List<Object>>();
		ResultSet rs = null;
		Statement statement = null;
		Connection currentConn = DbConnection.connect();
		if (currentConn != null) {
			final String getUsers = "SELECT idUser AS IdUser FROM user " + "WHERE type NOT LIKE 'ProjectManager'";
			final String getActiveStages = "SELECT U.idUser AS IdUser, S.idStage AS IdStage, S.startDay AS StartDay, S.finishDay AS FinishDay "
					+ "FROM user AS U JOIN stage AS S "
					+ "ON U.idUser = S.idSupervisor WHERE U.type NOT LIKE 'ProjectManager' AND S.rateWorkCompleted < 100";
			final String getActiveTasks = "SELECT U.idUser AS IdUser, TD.idTask AS IdTask, T.startDay AS StartDay, T.finishDay AS FinishDay "
					+ "FROM user AS U JOIN taskdevelopment AS TD ON U.idUser = TD.idDeveloper "
					+ "JOIN task AS T ON TD.idTask = T.idTask "
					+ "WHERE TD.workCompleted LIKE 'False'";

			try {
				statement = currentConn.createStatement();
				rs = statement.executeQuery(getUsers);
				while (rs.next()) {
					List<Object> works = new ArrayList<Object>();
					Integer user = new Integer(rs.getInt("IdUser"));
					workMap.put(user, works);
				}
				rs.close();
				
				rs = statement.executeQuery(getActiveStages);
				while (rs.next()) {
					StageBean stage = new StageBean();
					stage.setIdStage(rs.getInt("IdStage"));
					stage.setStartDay(rs.getString("StartDay"));
					stage.setFinishDay(rs.getString("FinishDay"));

					Integer user = new Integer(rs.getInt("IdUser"));

					List<Object> updatedValue = workMap.get(user);
					updatedValue.add(stage);
					workMap.put(user, updatedValue);
				}
				rs.close();
				rs = statement.executeQuery(getActiveTasks);
				while (rs.next()) {
					TaskBean task = new TaskBean();
					task.setIdTask(rs.getInt("IdTask"));
					task.setStartDay(rs.getString("StartDay"));
					task.setFinishDay(rs.getString("FinishDay"));

					Integer user = new Integer(rs.getInt("IdUser"));

					List<Object> updatedValue = workMap.get(user);
					updatedValue.add(task);
					workMap.put(user, updatedValue);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				// TODO handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		return workMap;
	
	}

	public boolean signUpUser(UserBean user) {
		boolean stored = false;
		SecureRandom sr = new SecureRandom();
		Long salt = sr.nextLong();
		String toStoreSalt = salt.toString();
		String saltedPassword = toStoreSalt + user.getPw();
		String hashedPassword = generateHash(saltedPassword);

		user.setSalt(toStoreSalt);
		user.setHashPw(hashedPassword);

		Connection currentConn = DbConnection.connect();
		// PreparedStatement statement;
		Statement statement = null;

		if (currentConn != null) {
			// String signUpQuery = "INSERT INTO User(username, salt, hashpw)
			// VALUES(?, ?, ?)";
			final String signUpQuery = "INSERT INTO user(username, fullname, mail, skills, salt, hashpw, type) VALUES('"
					+ user.getUsername() + "','" + user.getFullname() + "','" + user.getMail() + "','"
					+ user.getSkills() + "','" + user.getSalt() + "','" + user.getHashPw() + "','" + user.getType()
					+ "')";
			try {
				/*
				 * statement = currentConn.prepareStatement(signUpQuery);
				 * statement.setString(1, user.getUsername());
				 * statement.setString(2, user.getSalt());
				 * statement.setString(3, user.getHashPw());
				 */
				statement = currentConn.prepareStatement(signUpQuery);

				statement.executeUpdate(signUpQuery);
				stored = true;
			} catch (SQLException e) {
				e.printStackTrace(); // TODO Handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, statement);
			}
		}

		return stored;
	}

	/**
	 * Validate a user comparing his stored hash pw with an hash calculated at
	 * runtime with the user's pw and the stored random salt
	 * 
	 * @param user,
	 *            a bean which contains user's username and pw to be validated
	 * @return isAuthenticated
	 */
	public boolean validateUser(UserBean user) {

		Connection currentConn = DbConnection.connect();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Boolean isAuthenticated = false;

		if (currentConn != null) {
			final String validateQuery = "SELECT U.idUser AS IdUser, U.salt as Salt, U.hashPw AS HashPW, U.type As Type FROM user AS U WHERE U.username LIKE ?";
			try {
				statement = currentConn.prepareStatement(validateQuery);
				String username = user.getUsername();
				statement.setString(1, username);
				rs = statement.executeQuery();

				while (rs.next()) {
					user.setIdUser(rs.getInt("IdUser"));
					user.setSalt(rs.getString("Salt"));
					user.setHashPw(rs.getString("HashPw"));
					user.setType(rs.getString("Type"));
				}
			} catch (SQLException e) {
				e.printStackTrace(); // TODO Handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}

		}

		String saltedPassword = user.getSalt() + user.getPw();
		String hashedPassword = generateHash(saltedPassword);
		String storedPasswordHash = user.getHashPw();

		if (hashedPassword.equals(storedPasswordHash)) {
			isAuthenticated = true;
		} else {
			isAuthenticated = false;
		}
		return isAuthenticated;
	}

	/**
	 * Generate a SHA-256 hash
	 * 
	 * @param saltedPw,
	 *            a string composed by a random salt and user's pw
	 * @return hash, a string hashed with SHA-256
	 */
	private static String generateHash(String saltedPw) {
		StringBuilder hash = new StringBuilder();

		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = sha256.digest(saltedPw.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			for (int idx = 0; idx < hashedBytes.length; ++idx) {
				byte b = hashedBytes[idx];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Handle with a Logger
		}

		return hash.toString();
	}

}
