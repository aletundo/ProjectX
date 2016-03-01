package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.DbConnection;

public class TaskDAO {

	private static final TaskDAO INSTANCE = new TaskDAO();

	private static final Logger LOGGER = Logger.getLogger(TaskDAO.class.getName());

	private TaskDAO() {

	}

	public static TaskDAO getInstance() {

		return INSTANCE;
	}
	
	public void updateTask(TaskBean task, Map<String, Object> attributes){
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();
		String query = "UPDATE task AS P SET";
		
		for(Map.Entry<String, Object> pair : attributes.entrySet()){
			if(pair.getValue() != ""){
				query = query + " P." + pair.getKey() + " = ?,";
			}
		}
		
		if(query.charAt(query.length() - 1) == ','){
			query = query.substring(0,query.length() - 1);
		}
		
		query = query + " WHERE P.idTask = ?";

		if (currentConn != null) {
			final String updateProjectQuery = query;
			try {
				statement = currentConn.prepareStatement(updateProjectQuery);
				int i = 1;
				for(Map.Entry<String, Object> pair : attributes.entrySet()){
					if(pair.getValue() != ""){
						statement.setObject(i, pair.getValue());
						++i;
					}
				}
				statement.setInt(i, task.getIdTask());
				statement.executeUpdate();

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE,
						"Something went wrong during updating task " + task.getIdTask(), e);
			} finally {
				DbConnection.disconnect(currentConn, statement);
			}
		}
	}
	
	public void removeTasksWhenOutsourcing(int idStage){
		
		List<TaskBean> tasksToRemove = getTasksByStageId(idStage);
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String deleteTaskDevelopment = "DELETE FROM taskdevelopment WHERE idTask = ?";
			final String deleteTask = "DELETE FROM task WHERE idTask = ?";
			try {
				for(TaskBean task : tasksToRemove){
					statement = currentConn.prepareStatement(deleteTaskDevelopment);
					statement.setInt(1, task.getIdTask());
					statement.executeUpdate();
					statement.close();
				}
				
				for(TaskBean task : tasksToRemove){
					statement = currentConn.prepareStatement(deleteTask);
					statement.setInt(1, task.getIdTask());
					statement.executeUpdate();
					statement.close();
				}
				
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE,
						"Something went wrong during deleting work tasks for stage " + idStage, e);
			} finally {
				DbConnection.disconnect(currentConn, statement);
			}
		}
		
	}

	public void setPieceWorkCompleted(TaskBean task) {
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String setTasksWeight = "UPDATE taskdevelopment AS TD SET TD.workCompleted = 'True' WHERE TD.idTask = ? AND TD.idDeveloper = ?";
			try {
				statement = currentConn.prepareStatement(setTasksWeight);
				statement.setInt(1, task.getIdTask());
				statement.setInt(2, task.getIdDeveloper());
				statement.executeUpdate();

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE,
						"Something went wrong during updating work completed of task " + task.toString(), e);
			} finally {
				DbConnection.disconnect(currentConn, statement);
			}
		}

	}

	public List<UserBean> getAllDevelopersByIdTask(int idTask) {
		List<UserBean> developers = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String getAllDevelopersQuery = "SELECT U.idUser AS IdUser, U.fullname AS Fullname, U.type AS Type "
					+ "FROM user AS U JOIN taskdevelopment AS TD ON U.idUser = TD.idDeveloper WHERE TD.idTask = ?";
			try {
				statement = currentConn.prepareStatement(getAllDevelopersQuery);
				statement.setInt(1, idTask);
				rs = statement.executeQuery();

				while (rs.next()) {
					UserBean developer = new UserBean();
					developer.setIdUser(rs.getInt("IdUser"));
					developer.setFullname(rs.getString("Fullname"));
					developer.setType(rs.getString("Type"));
					developers.add(developer);
				}

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during getting all developers of task " + idTask, e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return developers;
	}

	public TaskBean getTaskInfo(int idTask) {
		TaskBean task = new TaskBean();
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String getTaskInfoQuery = "SELECT T.idTask AS IdTask, T.idStage As IdStage, T.name AS Name, "
					+ "T.startDay AS StartDay, T.finishDay AS FinishDay, T.completed AS Completed "
					+ "FROM task AS T WHERE T.idTask = ?";
			try {
				statement = currentConn.prepareStatement(getTaskInfoQuery);
				statement.setInt(1, idTask);
				rs = statement.executeQuery();

				while (rs.next()) {
					task.setIdTask(rs.getInt("IdTask"));
					task.setIdStage(rs.getInt("idStage"));
					task.setName(rs.getString("Name"));
					task.setStartDay(rs.getString("StartDay"));
					task.setFinishDay(rs.getString("FinishDay"));
					task.setCompleted(rs.getString("Completed"));
				}

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during getting details of task " + idTask, e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return task;

	}

	public TaskBean getRelativeWeight(int idTask) {
		TaskBean task = new TaskBean();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();
		if (currentConn != null) {
			final String getRelativeWeightQuery = "SELECT T.idStage AS IdStage, T.relativeWeight AS RelativeWeight FROM task AS T WHERE T.idTask = ?";
			try {
				statement = currentConn.prepareStatement(getRelativeWeightQuery);
				statement.setInt(1, idTask);
				rs = statement.executeQuery();
				while (rs.next()) {
					task.setIdStage(rs.getInt("IdStage"));
					task.setRelativeWeight(rs.getFloat("RelativeWeight"));
				}
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during getting relative weight of task " + idTask, e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}

		}
		return task;
	}

	public void setTaskCompleted(int idTask) {
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String setTasksWeight = "UPDATE task AS T SET T.completed = 'True' WHERE T.idTask = ?";
			try {
				statement = currentConn.prepareStatement(setTasksWeight);
				statement.setInt(1, idTask);
				statement.executeUpdate();

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during setting completed on task " + idTask, e);
			} finally {
				DbConnection.disconnect(currentConn, statement);
			}
		}
	}

	public List<String> checkDevelopersWork(int idTask) {
		List<String> workCompletedState = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();
		if (currentConn != null) {
			final String checkWorkQuery = "SELECT TD.workCompleted AS WorkCompleted FROM taskdevelopment AS TD WHERE TD.idTask = ?";
			try {
				statement = currentConn.prepareStatement(checkWorkQuery);
				statement.setInt(1, idTask);
				rs = statement.executeQuery();
				while (rs.next()) {
					workCompletedState.add(rs.getString("WorkCompleted"));
				}
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during getting completion status of task " + idTask, e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}

		}

		return workCompletedState;
	}

	public void setTasksWeight(List<TaskBean> tasks) {
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String setTasksWeight = "UPDATE task AS T SET T.relativeWeight = ? WHERE T.idTask = ?";
			try {
				for (TaskBean task : tasks) {
					statement = currentConn.prepareStatement(setTasksWeight);
					statement.setFloat(1, task.getRelativeWeight());
					statement.setInt(2, task.getIdTask());
					statement.executeUpdate();
					statement.close();
				}

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE,
						"Something went wrong during setting realative weights of tasks " + tasks.toString(), e);
			} finally {
				DbConnection.disconnect(currentConn, statement);
			}
		}
	}

	public List<TaskBean> getTasksByStageId(int idStage) {
		List<TaskBean> tasks = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String getTasksQuery = "SELECT T.idTask AS IdTask, T.name AS Name, "
					+ "T.startDay AS StartDay, T.finishDay AS FinishDay, T.completed AS Completed "
					+ "FROM task AS T WHERE T.idStage = ?";
			try {
				statement = currentConn.prepareStatement(getTasksQuery);
				statement.setInt(1, idStage);
				rs = statement.executeQuery();

				while (rs.next()) {
					TaskBean task = new TaskBean();
					task.setIdTask(rs.getInt("IdTask"));
					task.setName(rs.getString("Name"));
					task.setStartDay(rs.getString("StartDay"));
					task.setFinishDay(rs.getString("FinishDay"));
					task.setCompleted(rs.getString("Completed"));
					tasks.add(task);
				}

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during getting tasks of stage " + idStage, e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return tasks;

	}

	public boolean addDeveloper(TaskBean task, long hoursRequired) {
		PreparedStatement statement = null;
		boolean updated = false;
		Connection currentConn = DbConnection.connect();
		final String addDeveloperQuery = "INSERT INTO taskdevelopment (idDeveloper, idTask, hoursRequired) VALUES (?, ?, ?)";
		if (currentConn != null) {
			try {
				statement = currentConn.prepareStatement(addDeveloperQuery);
				statement.setInt(1, task.getIdDeveloper());
				statement.setInt(2, task.getIdTask());
				statement.setInt(3, (int) hoursRequired);

				statement.executeUpdate();
				updated = true;
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during setting developer of task " + task.toString(), e);
			} finally {
				DbConnection.disconnect(currentConn, statement);
			}
		}

		return updated;
	}

	public int createTask(TaskBean task) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		int idTask = Integer.MIN_VALUE;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String addTaskQuery = "INSERT INTO task (idStage, name, startDay, " + "finishDay) VALUES(?, ?, ?, ?)";
			try {
				statement = currentConn.prepareStatement(addTaskQuery, Statement.RETURN_GENERATED_KEYS);

				statement.setInt(1, task.getIdStage());
				statement.setString(2, task.getName());
				statement.setString(3, task.getStartDay());
				statement.setString(4, task.getFinishDay());

				statement.executeUpdate();
				rs = statement.getGeneratedKeys();
				while (rs.next())
					idTask = rs.getInt(1);

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during creation of " + task.toString(), e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return idTask;
	}

	public long getTaskHourRequested(Map.Entry<Integer, List<Object>> pair, long hourWork, TaskBean workTask) {
		long hourWorkTemp = hourWork;
		Connection currentConn = DbConnection.connect();
		ResultSet rs = null;
		PreparedStatement statement = null;

		if (currentConn != null) {
			final String getTaskHourQuery = "SELECT TD.hoursRequired AS HoursRequired "
					+ "FROM taskdevelopment AS TD JOIN user AS U ON TD.idDeveloper = U.idUser "
					+ "WHERE U.idUser = ? AND TD.idTask = ?";
			try {
				statement = currentConn.prepareStatement(getTaskHourQuery);
				statement.setInt(1, pair.getKey());
				statement.setInt(2, workTask.getIdTask());
				rs = statement.executeQuery();
				while (rs.next()) {
					hourWorkTemp = rs.getLong("HoursRequired");
				}
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during getting work hours of task " + workTask.toString()
						+ " assigned to " + pair.getKey(), e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		return hourWorkTemp;
	}

}