package controllers.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


import models.StageBean;
import utils.DbConnection;

public class SchedulerEvents {
	
	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) throws ParseException {
		final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				List<StageBean> stage = new ArrayList<StageBean>();

				int dataCritica;
	/*			for (StageBean varDaCiclo : stage) {
					dataCritica = CalculateAvailableUsers.getDifferenceDays(format.parse(varDaCiclo.getFinishDay()),
							format.parse(GetCurrentDateTime()));
					if (dataCritica < 0 && varDaCiclo.getRateWorkCompleted() < 99) {
					}
				} */
				/* TODO CHANGING TO SEND EMAIL */
			}
		}, 0, 24, TimeUnit.HOURS);
	}

	public List<StageBean> getStagesDates() {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection currentConn = DbConnection.connect();
		List<StageBean> stage = new ArrayList<StageBean>();

		if (currentConn != null) {
			final String getStagesDates = "SELECT S.idStage as IdStage, S.name AS StageName, S.startDay AS StartDay, "
					+ "S.finishDay AS FinishDay, U.fullname AS SupervisorFullname "
					+ "FROM stage AS S JOIN user AS U ON S.idSupervisor = U.idUser";
			try {
				statement = currentConn.prepareStatement(getStagesDates);
				rs = statement.executeQuery();
				while (rs.next()) {
					// TODO QUALCOSA
				}

			} catch (SQLException e) {
				e.printStackTrace();
				// TODO Handle with a logger
			} finally {
				DbConnection.disconnect(currentConn, rs, statement);
			}
		}

		return stage;
	}

	public static Date GetCurrentDateTime() {

		// get current date time with Calendar()
		Date date = new Date();
//		String dateStr = (String) date;

		return date;
	}
}
