package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    public String createUpdateStageQuery(Map<String, Object> attributes) {

        String query = "UPDATE stage AS S SET";

        for (Map.Entry<String, Object> pair : attributes.entrySet()) {
            if (pair.getValue() != "") {
                query = query + " S." + pair.getKey() + " = ?,";
            }
        }

        if (query.charAt(query.length() - 1) == ',') {
            query = query.substring(0, query.length() - 1);
        }

        query = query + " WHERE S.idStage = ?";

        return query;
    }
    
    public void updateStage(StageBean stage, Map<String, Object> attributes) {
        PreparedStatement statement = null;
        Connection currentConn = DbConnection.connect();

        String query = createUpdateStageQuery(attributes);

        final String updateStageQuery = query;
        try {
            statement = currentConn.prepareStatement(updateStageQuery);
            int i = 1;
            for (Map.Entry<String, Object> pair : attributes.entrySet()) {
                if (pair.getValue() != "") {
                    statement.setObject(i, pair.getValue());
                    ++i;
                }
            }
            statement.setInt(i, stage.getIdStage());
            if (!"UPDATE stage AS S SET WHERE S.idStage = ?".equals(query)) {
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during updating project " + stage.getIdStage(), e);
        } finally {
            DbConnection.disconnect(currentConn, statement);
        }
    }

    
    public void setCriticalStages(List<StageBean> criticalStages) {
        PreparedStatement statement = null;
        Connection currentConn = DbConnection.connect();

        if (currentConn != null) {
            final String setCriticalStagesQuery = "UPDATE stage AS S SET S.critical = 'True' WHERE S.idStage = ?";
            try {
                for (StageBean critical : criticalStages) {
                    statement = currentConn.prepareStatement(setCriticalStagesQuery);
                    statement.setFloat(1, critical.getIdStage());
                    statement.executeUpdate();
                    statement.close();
                }

            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE,
                        "Something went wrong during setting the critical status of stages" + criticalStages.toString(),
                        e);
            } finally {
                DbConnection.disconnect(currentConn, statement);
            }
        }
    }
    
    public void setStatusStage(int idStage, String status) {
        PreparedStatement statement = null;
        Connection currentConn = DbConnection.connect();

        if (currentConn != null) {
            final String setStatusStagesQuery = "UPDATE stage AS S SET S.status = ? WHERE S.idStage = ?";
            try {
                
                    statement = currentConn.prepareStatement(setStatusStagesQuery);
                    statement.setString(1, status);
                    statement.setInt(2, idStage);
                    statement.executeUpdate();
                    statement.close();
                

            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE,
                        "Something went wrong during setting the status of the stage",
                        e);
            } finally {
                DbConnection.disconnect(currentConn, statement);
            }
        }
    }

    public Map<StageBean, List<StageBean>> getPrecedences(int idProject) {

        Map<StageBean, List<StageBean>> mapPrecedences = new HashMap<>();

        List<StageBean> stages = getStagesByIdProject(idProject);
        for (StageBean stage : stages) {
            List<StageBean> precedences = new ArrayList<>();
            mapPrecedences.put(stage, precedences);
        }

        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection currentConn = DbConnection.connect();

        if (currentConn != null) {
            final String getPrecedencesQuery = "SELECT P.idStage AS IdStage, P.idPrecedence AS IdPrecedence, S.name AS Name, "
                    + "S.startDay AS StartDay, S.finishDay AS FinishDay, S.rateWorkCompleted AS RateWorkCompleted, "
                    + "U.fullname AS Supervisor, S.outsourcing AS Outsourcing, S.critical AS Critical, S.status AS Status "
                    + "FROM stage AS S JOIN user AS U ON U.idUser = S.idSupervisor JOIN  precedences AS P ON S.idStage = P.idPrecedence  "
                    + "WHERE S.idProject = ? AND S.outsourcing LIKE 'False'";
            final String getPrecendecesOutsourcedQuery = "SELECT P.idStage AS IdStage, P.idPrecedence AS IdPrecedence, S.name AS Name, "
                    + "S.startDay AS StartDay, S.finishDay AS FinishDay, S.rateWorkCompleted AS RateWorkCompleted, "
                    + "U.fullname AS Supervisor, S.outsourcing AS Outsourcing, S.critical AS Critical, S.status AS Status "
                    + "FROM stage AS S JOIN user AS U ON U.idUser = S.idSupervisor JOIN precedences AS P ON S.idStage = P.idPrecedence WHERE S.idProject = ? AND S.outsourcing LIKE 'True'";
            try {
                statement = currentConn.prepareStatement(getPrecedencesQuery);
                statement.setInt(1, idProject);
                rs = statement.executeQuery();
                helperQueryMethod(mapPrecedences, rs);

                rs.close();
                statement.close();

                statement = currentConn.prepareStatement(getPrecendecesOutsourcedQuery);
                statement.setInt(1, idProject);
                rs = statement.executeQuery();
                helperQueryMethod(mapPrecedences, rs);

            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE,
                        "Something went wrong during getting stages precedences of project " + idProject, e);
            } finally {
                DbConnection.disconnect(currentConn, rs, statement);
            }
        }
        return mapPrecedences;
    }

    private static void helperQueryMethod(Map<StageBean, List<StageBean>> mapPrecedences, ResultSet rs)
            throws SQLException {
        while (rs.next()) {
            StageBean precedence = new StageBean();
            precedence.setIdStage(rs.getInt("IdPrecedence"));
            precedence.setName(rs.getString("Name"));
            precedence.setStartDay(rs.getString("StartDay"));
            precedence.setFinishDay(rs.getString("FinishDay"));
            precedence.setRateWorkCompleted(rs.getFloat("RateWorkCompleted"));
            precedence.setSupervisorFullname(rs.getString("Supervisor"));
            precedence.setOutsourcing(rs.getString("Outsourcing"));
            precedence.setCritical(rs.getString("Critical"));
            precedence.setStatus(rs.getString("Status"));

            for (Map.Entry<StageBean, List<StageBean>> pair : mapPrecedences.entrySet()) {
                if (pair.getKey().getIdStage() == rs.getInt("IdStage")) {
                    List<StageBean> updatedValue = pair.getValue();
                    updatedValue.add(precedence);
                    mapPrecedences.put(pair.getKey(), updatedValue);
                }
            }
        }
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
                LOGGER.log(Level.SEVERE,
                        "Something went wrong during setting relative weight of stages " + stages.toString(), e);
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
                LOGGER.log(Level.SEVERE, "Something went wrong during getting rate work completed of stage " + idStage,
                        e);
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
                LOGGER.log(Level.SEVERE, "Something went wrong during setting rate work completed of stage " + idStage,
                        e);
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
                LOGGER.log(Level.SEVERE,
                        "Something went wrong during getting either project manager or supervisor of stage " + idStage,
                        e);
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
                    + "S.finishDay AS FinishDay, S.goals AS Goals, S.requirements AS Requirements, S.rateWorkCompleted AS RateWorkCompleted, U.fullname AS SupervisorFullname, S.critical AS Critical, S.status AS Status "
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
                    stageInfo.setGoals(rs.getString("Goals"));
                    stageInfo.setRequirements(rs.getString("Requirements"));
                    stageInfo.setRateWorkCompleted(rs.getFloat("RateWorkCompleted"));
                    stageInfo.setSupervisorFullname(rs.getString("SupervisorFullname"));
                    stageInfo.setCritical(rs.getString("Critical"));
                    stageInfo.setStatus(rs.getString("Status"));
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
                    + "U.fullname AS Supervisor, S.outsourcing AS Outsourcing, S.critical AS Critical, S.status AS Status "
                    + "FROM stage AS S JOIN user AS U ON U.idUser = S.idSupervisor "
                    + "WHERE S.idProject = ? AND S.outsourcing LIKE 'False'";

            final String getStagesOutsourcedQuery = "SELECT S.idStage AS IdStage, S.name AS Name, "
                    + "S.startDay AS StartDay, S.finishDay AS FinishDay, S.rateWorkCompleted AS RateWorkCompleted, "
                    + "S.outsourcing AS Outsourcing, S.critical AS Critical, S.status AS Status FROM stage AS S WHERE S.idProject = ? AND S.outsourcing LIKE 'True'";
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
                    stage.setStatus(rs.getString("Status"));
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
                LOGGER.log(Level.SEVERE,
                        "Something went wrong during setting precedences of stage " + stage.getIdStage(), e);
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
                    statement.setInt(3, stage.getIdStage());
                } else {
                    statement = currentConn.prepareStatement(addSupervisorQuery);
                    statement.setInt(1, stage.getIdSupervisor());
                    statement.setInt(2, stage.getIdStage());
                }
                statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Something went wrong during setting supervisor of stage " + stage.toString(),
                        e);
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
