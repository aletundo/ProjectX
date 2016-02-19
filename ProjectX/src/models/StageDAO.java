package models;

import java.sql.*;
import utils.DbConnection;

public class StageDAO {

	private static final StageDAO INSTANCE = new StageDAO();

	private StageDAO() {

	}

	public static StageDAO getInstance() {

		return INSTANCE;
	}

	public boolean addSupervisor(StageBean stage) {
		PreparedStatement statement = null;
		boolean updated = false;
		Connection currentConn = DbConnection.connect();
		String addSupervisorQuery = "UPDATE stage SET idSupervisor = ? WHERE idStage = ?";

		if (currentConn != null) {
			try {
				if (stage.isOutsourcing()) {
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
