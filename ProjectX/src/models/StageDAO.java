package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.DbConnection;

public class StageDAO {

	private static final StageDAO INSTANCE = new StageDAO();
	private static final Logger LOGGER = Logger.getLogger(StageDAO.class.getName());

	private StageDAO() {

	}

	public static StageDAO getInstance() {

		return INSTANCE;
	}
	
	public Map<StageBean, List<StageBean>> getPrecedences(int idProject){
		return null;
	}

	public StageBean getRelativeWeight(int idStage) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();
		StageBean stage = new StageBean();

		if (currentConn != null) {
			final String getRelativeWeightQuery = "SELECT S.relativeWeight AS RelativeWeight, S.idProject AS IdProject "
					+ "FROM stage AS S WHERE S.idStage = ?";
			try {
				statement = currentConn.prepareStatement(getRelativeWeightQuery);
				statement.setInt(1, idStage);
				rs = statement.executeQuery();
				while (rs.next()) {
					stage.setIdProject(rs.getInt("IdProject"));
					stage.setRelativeWeight(rs.getFloat("RelativeWeight"));
				}

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during getting relative weight of stage " + idStage, e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return stage;
	}

	public void setStagesWeight(List<StageBean> stages) {
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String setStagesWeight = "UPDATE stage AS S SET S.relativeWeight = ? WHERE S.idStage = ?";
			try {
				for (StageBean stage : stages) {
					statement = currentConn.prepareStatement(setStagesWeight);
					statement.setFloat(1, stage.getRelativeWeight());
					statement.setInt(2, stage.getIdStage());
					statement.executeUpdate();
					statement.close();
				}

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during setting relative weight of stages " + stages.toString(), e);
			} finally {
				DbConnection.disconnect(currentConn, statement);
			}
		}
	}

	public float getRateWorkCompleted(int idStage) {
		float rateWorkCompleted = 0;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String getRateworkCompletedQuery = "SELECT S.rateWorkCompleted AS RateWorkCompleted FROM stage AS S "
					+ "WHERE S.idStage = ? ";
			try {
				statement = currentConn.prepareStatement(getRateworkCompletedQuery);
				statement.setInt(1, idStage);
				rs = statement.executeQuery();
				while (rs.next()) {
					rateWorkCompleted = rs.getFloat("RateWorkCompleted");
				}

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during getting rate work completed of stage " + idStage, e);
			} finally {
				DbConnection.disconnect(currentConn, statement);
			}
		}

		return rateWorkCompleted;
	}

	public void setRateWorkCompleted(int idStage, float rate) {
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String setRateWorkCompletedQuery = "UPDATE stage AS S SET S.rateWorkCompleted = ? "
					+ "WHERE S.idStage = ? ";
			try {
				statement = currentConn.prepareStatement(setRateWorkCompletedQuery);
				statement.setFloat(1, rate);
				statement.setInt(2, idStage);
				statement.executeUpdate();

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during setting rate work completed of stage " + idStage, e);
			} finally {
				DbConnection.disconnect(currentConn, statement);
			}
		}
	}

	public int[] checkIdProjectManagerOrSupervisor(int idStage) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();
		int[] idAuthorizedUsers = new int[2];

		if (currentConn != null) {
			final String getIdSupervisorQuery = "SELECT S.idSupervisor AS IdSupervisor, P.idProjectManager AS IdProjectManager "
					+ "FROM stage AS S JOIN project AS P ON S.idProject = P.idProject WHERE S.idStage = ?";
			try {
				statement = currentConn.prepareStatement(getIdSupervisorQuery);
				statement.setInt(1, idStage);
				rs = statement.executeQuery();
				while (rs.next()) {
					idAuthorizedUsers[0] = rs.getInt("IdSupervisor");
					idAuthorizedUsers[1] = rs.getInt("IdProjectManager");
				}

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during getting either project manager or supervisor of stage " + idStage, e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return idAuthorizedUsers;
	}

	public StageBean getStageInfo(int idStage) {
		StageBean stageInfo = new StageBean();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String getProjectInfoQuery = "SELECT S.idStage as IdStage, S.idProject AS IdProject, S.name AS StageName, S.startDay AS StartDay, "
					+ "S.finishDay AS FinishDay, S.rateWorkCompleted AS RateWorkCompleted, U.fullname AS SupervisorFullname "
					+ "FROM stage AS S JOIN user AS U ON S.idSupervisor = U.idUser  WHERE S.idStage = ?";
			try {
				statement = currentConn.prepareStatement(getProjectInfoQuery);
				statement.setInt(1, idStage);

				rs = statement.executeQuery();
				while (rs.next()) {
					stageInfo.setIdStage(rs.getInt("IdStage"));
					stageInfo.setIdProject(rs.getInt("IdProject"));
					stageInfo.setName(rs.getString("StageName"));
					stageInfo.setStartDay(rs.getString("StartDay"));
					stageInfo.setFinishDay(rs.getString("FinishDay"));
					stageInfo.setRateWorkCompleted(rs.getFloat("RateWorkCompleted"));
					stageInfo.setSupervisorFullname(rs.getString("SupervisorFullname"));
				}

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during getting details of stage " + idStage, e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}
		return stageInfo;
	}

	public List<StageBean> getStagesByIdProject(int idProject) {
		List<StageBean> stages = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String getStagesQuery = "SELECT S.idStage AS IdStage, S.name AS Name, "
					+ "S.startDay AS StartDay, S.finishDay AS FinishDay, S.rateWorkCompleted AS RateWorkCompleted, "
					+ "U.fullname AS Supervisor, S.outsourcing AS Outsourcing, S.critical AS Critical "
					+ "FROM stage AS S JOIN user AS U ON U.idUser = S.idSupervisor "
					+ "WHERE S.idProject = ? AND S.outsourcing LIKE 'False'";

			final String getStagesOutsourcedQuery = "SELECT S.idStage AS IdStage, S.name AS Name, "
					+ "S.startDay AS StartDay, S.finishDay AS FinishDay, S.rateWorkCompleted AS RateWorkCompleted, "
					+ "S.outsourcing AS Outsourcing, S.critical AS Critical FROM stage AS S WHERE S.idProject = ? AND S.outsourcing LIKE 'True'";
			try {
				statement = currentConn.prepareStatement(getStagesQuery);
				statement.setInt(1, idProject);
				rs = statement.executeQuery();

				while (rs.next()) {
					StageBean stage = new StageBean();
					stage.setIdStage(rs.getInt("IdStage"));
					stage.setName(rs.getString("Name"));
					stage.setStartDay(rs.getString("StartDay"));
					stage.setFinishDay(rs.getString("FinishDay"));
					stage.setRateWorkCompleted(rs.getFloat("RateWorkCompleted"));
					stage.setSupervisorFullname(rs.getString("Supervisor"));
					stage.setOutsourcing(rs.getString("Outsourcing"));
					stage.setCritical(rs.getString("Critical"));
					stages.add(stage);
				}
				rs.close();
				statement.close();

				statement = currentConn.prepareStatement(getStagesOutsourcedQuery);
				statement.setInt(1, idProject);
				rs = statement.executeQuery();
				while (rs.next()) {
					StageBean stage = new StageBean();
					stage.setIdStage(rs.getInt("IdStage"));
					stage.setName(rs.getString("Name"));
					stage.setStartDay(rs.getString("StartDay"));
					stage.setFinishDay(rs.getString("FinishDay"));
					stage.setRateWorkCompleted(rs.getFloat("RateWorkCompleted"));
					stage.setOutsourcing(rs.getString("Outsourcing"));
					stage.setCritical(rs.getString("Critical"));
					stages.add(stage);
				}

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during getting all stages of project " + idProject, e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return stages;

	}

	public boolean addPrecedences(StageBean stage, List<StageBean> precedences) {
		boolean added = false;
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String addPrecedencesQuery = "INSERT INTO precedences (idStage, idPrecedence) VALUES (?, ?)";
			try {
				for (StageBean p : precedences) {
					statement = currentConn.prepareStatement(addPrecedencesQuery);
					statement.setInt(1, stage.getIdStage());
					statement.setInt(2, p.getIdStage());
					statement.executeUpdate();
				}
				added = true;
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during setting precedences of stage " + stage.getIdStage(), e);
			} finally {
				DbConnection.disconnect(currentConn, statement);
			}
		}
		return added;
	}

	public void addSupervisor(StageBean stage) {
		PreparedStatement statement = null;
		Connection currentConn = DbConnection.connect();
		String addSupervisorQuery = "UPDATE stage SET idSupervisor = ? WHERE idStage = ?";

		if (currentConn != null) {
			try {
				if ("True".equals(stage.getOutsourcing())) {
					addSupervisorQuery = "UPDATE stage SET outsourcing = ?, " + "idSupervisor = ? WHERE idStage = ?";
					statement = currentConn.prepareStatement(addSupervisorQuery);
					statement.setString(1, "True");
					statement.setInt(2, stage.getIdSupervisor());
					// statement.setString(2, stage.getCompanyName());
					// statement.setString(3, stage.getCompanyMail());
					statement.setInt(3, stage.getIdStage());
				} else {
					statement = currentConn.prepareStatement(addSupervisorQuery);
					statement.setInt(1, stage.getIdSupervisor());
					statement.setInt(2, stage.getIdStage());
				}
				statement.executeUpdate();
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during setting supervisor of stage " + stage.toString(), e);
			} finally {
				DbConnection.disconnect(currentConn, statement);
			}
		}
	}

	public int createStage(StageBean stage) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		int idStage = Integer.MIN_VALUE;
		Connection currentConn = DbConnection.connect();

		if (currentConn != null) {
			final String addStageQuery = "INSERT INTO stage (idProject, name, goals, requirements, startDay, "
					+ "finishDay) VALUES(?, ?, ?, ?, ?, ?)";
			try {
				statement = currentConn.prepareStatement(addStageQuery, Statement.RETURN_GENERATED_KEYS);

				statement.setInt(1, stage.getIdProject());
				statement.setString(2, stage.getName());
				statement.setString(3, stage.getGoals());
				statement.setString(4, stage.getRequirements());
				statement.setString(5, stage.getStartDay());
				statement.setString(6, stage.getFinishDay());

				statement.executeUpdate();
				rs = statement.getGeneratedKeys();
				while (rs.next())
					idStage = rs.getInt(1);

			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during cretion  of stage " + stage.toString(), e);
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return idStage;
	}

}
