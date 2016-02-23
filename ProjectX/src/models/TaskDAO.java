package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import utils.DbConnection;

public class TaskDAO {

	private static final TaskDAO INSTANCE = new TaskDAO();

	private TaskDAO() {

	}

	public static TaskDAO getInstance() {

		return INSTANCE;
	}
	
	public TaskBean getRelativeWeight(int idTask){
		TaskBean task = new TaskBean();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();
		if(currentConn != null){
			final String getRelativeWeightQuery= "SELECT T.idStage AS IdStage, T.relativeWeight AS RelativeWeight WHERE T.idTask = ?";
			try{
				statement = currentConn.prepareStatement(getRelativeWeightQuery);
				statement.setInt(1, idTask);
				rs = statement.executeQuery();
				while(rs.next()){
					task.setIdStage(rs.getInt("IdStage"));
					task.setRelativeWeight(rs.getFloat("RelativeWeight"));
				}
			}catch(SQLException e){
				e.printStackTrace();
				//TODO ilaria handle with a logger
			}finally{
				DbConnection.disconnect(currentConn, rs, statement);
			}
			
		}
		return task;
	}
	
	public void setTaskCompleted(int idTask){
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();
		
		if(currentConn != null){
			final String setTasksWeight = "UPDATE tasks AS T SET T.completed = 'True' WHERE T.idTask = ?";
			try{
					statement = currentConn.prepareStatement(setTasksWeight);
					statement.setInt(1, idTask);
					statement.executeUpdate();
				
			}catch(SQLException e){
				e.printStackTrace();
				//TODO Handle with a logger
			}finally{
				DbConnection.disconnect(currentConn, statement);
			}
		}
	}
	
	public List<String> checkDevelopersWork(int idTask) {
		List<String> workCompletedState = new ArrayList<String>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();
		if(currentConn != null){
			final String checkWorkQuery= "SELECT TD.workCompleted AS WorkCompleted FROM taskdevelopment AS TD WHERE TD.idTask = ?";
			try{
				statement = currentConn.prepareStatement(checkWorkQuery);
				statement.setInt(1, idTask);
				rs = statement.executeQuery();
				while(rs.next()){
					workCompletedState.add(rs.getString("WorkCompleted"));
				}
			}catch(SQLException e){
				e.printStackTrace();
				//TODO ilaria handle with a logger
			}finally{
				DbConnection.disconnect(currentConn, rs, statement);
			}
			
		}
		
		return workCompletedState;
	}
	
	public void setTasksWeight(List<TaskBean> tasks){
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();
		
		if(currentConn != null){
			final String setTasksWeight = "UPDATE task AS T SET T.relativeWeight = ? WHERE T.idTask = ?";
			try{
				for(TaskBean task : tasks){
					statement = currentConn.prepareStatement(setTasksWeight);
					statement.setFloat(1, task.getRelativeWeight());
					statement.setInt(2, task.getIdTask());
					statement.executeUpdate();
					statement.close();
				}
				
			}catch(SQLException e){
				e.printStackTrace();
				//TODO Handle with a logger
			}finally{
				DbConnection.disconnect(currentConn, statement);
			}
		}
	}
	
	public List<TaskBean> getTasksDetails(int idStage){
		List<TaskBean> tasks = new ArrayList<TaskBean>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();
		
		if(currentConn != null){
			final String getTasksQuery = "SELECT T.idTask AS IdTask, T.name AS Name, "
					+ "T.startDay AS StartDay, T.finishDay AS FinishDay, T.completed AS Completed "
					+ "FROM task AS T WHERE T.idStage = ?";
			try{
				statement = currentConn.prepareStatement(getTasksQuery);
				statement.setInt(1, idStage);
				rs = statement.executeQuery();
				
				while(rs.next()){
					TaskBean task = new TaskBean();
					task.setIdTask(rs.getInt("IdTask"));
					task.setName(rs.getString("Name"));
					task.setStartDay(rs.getString("StartDay"));
					task.setFinishDay(rs.getString("FinishDay"));
					task.setCompleted(rs.getString("Completed"));
					tasks.add(task);
				}
				
			}catch(SQLException e){
				e.printStackTrace();
				//TODO Handle with a Logger
			}finally{
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		
		return tasks;
		
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