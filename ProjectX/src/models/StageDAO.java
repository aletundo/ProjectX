package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import utils.DbConnection;

public class StageDAO {

	private static final StageDAO INSTANCE = new StageDAO();

	private StageDAO() {

	}

	public static StageDAO getInstance() {

		return INSTANCE;
	}
	
	public StageBean getStageInfo(int idStage){
		StageBean stageInfo = new StageBean();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String getProjectInfoQuery = "SELECT S.idStage as IdStage, S.name AS StageName, S.startDay AS StartDay, S.finishDay AS FinishDay, U.fullname AS SupervisorFullname "
					+ "FROM stage AS S JOIN user AS U ON S.idSupervisor = U.idUser  WHERE S.idStage = ?";
			try {
				statement = currentConn.prepareStatement(getProjectInfoQuery);
				statement.setInt(1, idStage);

				rs = statement.executeQuery();
				while (rs.next()) {
					stageInfo.setIdStage(Integer.parseInt(rs.getString("IdStage")));
					stageInfo.setName(rs.getString("StageName"));
					stageInfo.setStartDay(rs.getString("StartDay"));
					stageInfo.setFinishDay(rs.getString("FinishDay"));
					stageInfo.setSupervisorFullname(rs.getString("SupervisorFullname"));
				}

			} catch (SQLException e) {
				e.printStackTrace();
				// TODO Handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		return stageInfo;
	}
	public List<StageBean> getStagesDetails(int idProject){
		List<StageBean> stages = new ArrayList<StageBean>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();
		
		if(currentConn != null){
			final String getStagesQuery = "SELECT S.idStage AS IdStage, S.name AS Name, "
					+ "S.startDay AS StartDay, S.finishDay AS FinishDay, U.fullname AS Supervisor "
					+ "FROM stage AS S JOIN user AS U ON U.idUser = S.idSupervisor "
					+ "WHERE S.idProject = ? AND S.outsourcing LIKE 'False' "
					+ "UNION SELECT S.idStage AS IdStage, S.name AS Name, "
					+ "S.startDay AS StartDay, S.finishDay AS FinishDay, S.companyName AS Supervisor "
					+ "FROM stage AS S WHERE S.idProject = ? AND S.outsourcing LIKE 'True'";
			try{
				statement = currentConn.prepareStatement(getStagesQuery);
				statement.setInt(1, idProject);
				statement.setInt(2, idProject);
				rs = statement.executeQuery();
				
				while(rs.next()){
					StageBean stage = new StageBean();
					stage.setIdStage(rs.getInt("IdStage"));
					stage.setName(rs.getString("Name"));
					stage.setStartDay(rs.getString("StartDay"));
					stage.setFinishDay(rs.getString("FinishDay"));
					stage.setSupervisorFullname(rs.getString("Supervisor"));
					stages.add(stage);
				}
				
			}catch(SQLException e){
				e.printStackTrace();
				//TODO Handle with a Logger
			}finally{
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		
		return stages;
		
	}
	
	public boolean addPrecedences(StageBean stage, List<StageBean> precedences){
		boolean added = false;
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();
		
		if(currentConn != null){
			final String addPrecedencesQuery = "INSERT INTO precedences (idStage, idPrecedence) VALUES (?, ?)";
			try{
				for(StageBean p : precedences){
					statement = currentConn.prepareStatement(addPrecedencesQuery);
					statement.setInt(1, stage.getIdStage());
					statement.setInt(2, p.getIdStage());
					statement.executeUpdate();
				}
				added = true;
			}catch(SQLException e){
				e.printStackTrace();
				//TODO Handle with a Logger
			}finally{
				DbConnection.disconnect(currentConn, statement);
			}
		}
		return added;
	}

	public List<StageBean> getStages(int idProject){
		List<StageBean> stages = new ArrayList<StageBean>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();
		
		if(currentConn != null){
			final String getStagesQuery = "SELECT S.idStage AS IdStage, S.name AS Name"
					+ " FROM stage AS S WHERE S.idProject = ?";
			try{
				statement = currentConn.prepareStatement(getStagesQuery);
				statement.setInt(1, idProject);
				rs = statement.executeQuery();
				while(rs.next()){
					StageBean stage = new StageBean();
					stage.setIdStage(rs.getInt("IdStage"));
					stage.setName(rs.getString("Name"));
					stages.add(stage);
				}
			}catch(SQLException e){
				e.printStackTrace();
				//TODO Handle with a Logger
			}finally{
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		
		return stages;
	}
	
	public boolean addSupervisor(StageBean stage) {
		PreparedStatement statement = null;
		boolean updated = false;
		Connection currentConn = DbConnection.connect();
		String addSupervisorQuery = "UPDATE stage SET idSupervisor = ? WHERE idStage = ?";

		if (currentConn != null) {
			try {
				if (stage.getOutsourcing().equals("True")) {
					addSupervisorQuery = "UPDATE stage SET outsourcing = ?, companyName = ?, "
							+ "companyMail = ? WHERE idStage = ?";
					statement = currentConn.prepareStatement(addSupervisorQuery);
					statement.setString(1, "True");
					statement.setString(2, stage.getCompanyName());
					statement.setString(3, stage.getCompanyMail());
					statement.setInt(4, stage.getIdStage());
				} else {
					statement = currentConn.prepareStatement(addSupervisorQuery);
					statement.setInt(1, stage.getIdSupervisor());
					statement.setInt(2, stage.getIdStage());
				}
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

	public int createStage(StageBean stage) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String addStageQuery = "INSERT INTO stage (idProject, name, goals, requirements, startDay, "
					+ "finishDay, estimatedDuration) VALUES(?, ?, ?, ?, ?, ?, ?)";
			try {
				statement = currentConn.prepareStatement(addStageQuery, Statement.RETURN_GENERATED_KEYS);

				statement.setInt(1, stage.getIdProject());
				statement.setString(2, stage.getName());
				statement.setString(3, stage.getGoals());
				statement.setString(4, stage.getRequirements());
				statement.setString(5, stage.getStartDay());
				statement.setString(6, stage.getFinishDay());
				statement.setInt(7, stage.getEstimatedDuration());

				statement.executeUpdate();
				rs = statement.getGeneratedKeys();
				while (rs.next())
					stage.setIdStage(rs.getInt(1));

			} catch (SQLException e) {
				e.printStackTrace();
				// TODO Handle with a Logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return stage.getIdStage();
	}

}
