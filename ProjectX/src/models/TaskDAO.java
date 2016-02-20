package models;

import java.sql.*;
import utils.DbConnection;

public class TaskDAO {

	private static final TaskDAO INSTANCE = new TaskDAO();

	private TaskDAO() {

	}

	public static TaskDAO getInstance() {

		return INSTANCE;
	}

	public boolean addDeveloper(TaskBean task) {
		PreparedStatement statement = null;
		boolean updated = false;
		Connection currentConn = DbConnection.connect();
		String addDeveloperQuery = "UPDATE task SET idDeveloper = ? WHERE idTask = ?";

		if (currentConn != null) {
			try {
					statement = currentConn.prepareStatement(addDeveloperQuery);
					statement.setInt(1, task.getIdDeveloper());
					statement.setInt(2, task.getIdTask());
				
				statement.executeUpdate();
				updated = true;
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO Handle with a logger
			} finally {
				DbConnection.disconnect(currentConn, statement);
			}
		}

		return updated;
	}

	public int createTask(TaskBean task) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String addTaskQuery = "INSERT INTO task (idStage, startDay, "
					+ "finishDay) VALUES(?, ?, ?)";
			try {
				statement = currentConn.prepareStatement(addTaskQuery, Statement.RETURN_GENERATED_KEYS);

				statement.setInt(1, task.getIdStage());
				statement.setString(2, task.getStartDay());
				statement.setString(3, task.getFinishDay());

				statement.executeUpdate();
				rs = statement.getGeneratedKeys();
				while (rs.next())
					task.setIdTask(rs.getInt(1));

			} catch (SQLException e) {
				e.printStackTrace();
				// TODO Handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return task.getIdTask();
	}

}