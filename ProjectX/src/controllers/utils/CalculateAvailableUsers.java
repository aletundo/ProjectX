package controllers.utils;

import models.ProjectBean;
import java.sql.*;
import java.lang.Math;

import models.StageBean;
import models.TaskBean;
import models.UserBean;
import utils.DbConnection;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CalculateAvailableUsers {
	private static final int HOURPERDAY = 8;
	private static final int PROJECTHOURPERDAY = 4;
	private static final int STAGEHOURPERDAY = 2;

	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	private static Date minStart = new Date();
	private static Date maxFinish = new Date();

	// calculate the available users for a new TASK
	public static List<UserBean> calculate(Map<Integer, List<Object>> workMap, TaskBean newTask) {
		format.setTimeZone(TimeZone.getTimeZone("GTM"));
		List<UserBean> availableUsers = new ArrayList<UserBean>();
		Iterator<Map.Entry<Integer, List<Object>>> mapIterator = workMap.entrySet().iterator();
		while (mapIterator.hasNext()) {
			try {
				minStart = format.parse(newTask.getStartDay());
				maxFinish = format.parse(newTask.getFinishDay());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Map.Entry<Integer, List<Object>> pair = (Map.Entry<Integer, List<Object>>) mapIterator.next();
			List<Object> criticalWorks = new ArrayList<Object>();

			Iterator<Object> arrayListIterator = pair.getValue().iterator();
			if (pair.getValue() == null) {
				UserBean userFree = new UserBean();
				userFree.setIdUser(pair.getKey());
				availableUsers.add(userFree);
			}
			while (arrayListIterator.hasNext()) {
				try {
					Object work = arrayListIterator.next();
					boolean isCritical = calculateCriticalWork(work, newTask, format);
					if (isCritical) {
						if (work != null) {
							criticalWorks.add(work);
						}
					}
				} catch (ParseException e) {
					// TODO handle exception with a logger
					e.printStackTrace();
				}
			}

			UserBean availableUser = isAvailable(newTask, pair, criticalWorks);
			if (availableUsers != null) {
				availableUsers.add(availableUser);
			}
		}
		return availableUsers;
	}

	// calculate the available users for a new STAGE
	public static List<UserBean> calculate(Map<Integer, List<Object>> workMap, StageBean newStage) {
		format.setTimeZone(TimeZone.getTimeZone("GTM"));
		List<UserBean> availableUsers = new ArrayList<UserBean>();
		Iterator<Map.Entry<Integer, List<Object>>> mapIterator = workMap.entrySet().iterator();
		while (mapIterator.hasNext()) {
			try {
				minStart = format.parse(newStage.getStartDay());
				maxFinish = format.parse(newStage.getFinishDay());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Map.Entry<Integer, List<Object>> pair = (Map.Entry<Integer, List<Object>>) mapIterator.next();
			List<Object> criticalWorks = new ArrayList<Object>();

			Iterator<Object> arrayListIterator = pair.getValue().iterator();
			if (pair.getValue() == null) {
				UserBean userFree = new UserBean();
				userFree.setIdUser(pair.getKey());
				availableUsers.add(userFree);
			}
			while (arrayListIterator.hasNext()) {
				try {
					Object work = arrayListIterator.next();
					boolean isCritical = calculateCriticalWork(work, newStage, format);
					if (isCritical) {
						if (work != null) {
							criticalWorks.add(work);
						}
					}
				} catch (ParseException e) {
					// TODO handle exception with a logger
					e.printStackTrace();
				}
			}

			UserBean availableUser = isAvailable(newStage, pair, criticalWorks);
			if (availableUser != null) {
				availableUsers.add(availableUser);
			}
		}
		return availableUsers;
	}

	// calculate the availability of a user for a new STAGE
	private static UserBean isAvailable(StageBean newStage, Map.Entry<Integer, List<Object>> pair,
			List<Object> criticalWorks) {
		long hourAvailable = Long.MIN_VALUE;
		long hourNewStage = Long.MIN_VALUE;
		try {
			hourAvailable = calculateAvailability(criticalWorks, newStage, pair);
			hourNewStage = STAGEHOURPERDAY
					* (getDifferenceDays(format.parse(newStage.getStartDay()), format.parse(newStage.getFinishDay())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ore disponibili:" + hourAvailable);
		System.out.println("ore nuovo stage: " + hourNewStage);
		if (hourAvailable >= hourNewStage) {
			UserBean availableUser = new UserBean();
			if (pair != null) {
				availableUser.setIdUser(pair.getKey());
				return availableUser;
			}
		}
		return null;
	}

	// calculate the availability of a user for a new TASK
	private static UserBean isAvailable(TaskBean newTask, Map.Entry<Integer, List<Object>> pair,
			List<Object> criticalWorks) {
		long hourAvailable = Long.MIN_VALUE;

		try {
			hourAvailable = calculateAvailability(criticalWorks, newTask, pair);
		} catch (ParseException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (hourAvailable > 0) {
			UserBean availableUser = new UserBean();
			if (pair != null) {
				availableUser.setIdUser(pair.getKey());
				return availableUser;
			}
		}
		return null;

	}

	// calculate the available hours of a user for a new STAGE
	public static long calculateAvailability(List<Object> criticalWorks, StageBean newStage,
			Map.Entry<Integer, List<Object>> pair) throws ParseException {
		long dateDiffTOT = getDifferenceDays(minStart, maxFinish);
		System.out.println("dateDiffTOT: " + dateDiffTOT);
		long workingHoursTOT = HOURPERDAY * (dateDiffTOT);
		System.out.println("ore lavorative disponibili: " + workingHoursTOT);
		long hoursCriticalWorks = 0;
		for (Object work : criticalWorks) {

			long hourWork = 0;

			if (work.getClass().getName().equals("models.ProjectBean")) {

				System.out.println("working hours tot" + workingHoursTOT);
				ProjectBean workProject = (ProjectBean) work;
				long dateDiff = getDifferenceDays(
						calculateMin(format.parse(workProject.getStart()), format.parse(newStage.getStartDay())),
						calculateMax(format.parse(workProject.getDeadline()), format.parse(newStage.getFinishDay())));

				hourWork = PROJECTHOURPERDAY
						* (dateDiff - (getDifferenceDaysExclusive(format.parse(workProject.getStart()),
								format.parse(newStage.getStartDay()))
								+ getDifferenceDaysExclusive(format.parse(workProject.getDeadline()),
										format.parse(newStage.getFinishDay()))));

				hoursCriticalWorks += hourWork;
				System.out.println("ore lavori critici1 " + hoursCriticalWorks);
			}

			if (work.getClass().getName().equals("models.StageBean")) {
				StageBean workStage = (StageBean) work;
				long dateDiff = getDifferenceDays(
						calculateMin(format.parse(workStage.getStartDay()), format.parse(newStage.getStartDay())),
						calculateMax(format.parse(workStage.getFinishDay()), format.parse(newStage.getFinishDay())));

				hourWork = STAGEHOURPERDAY
						* (dateDiff - (getDifferenceDaysExclusive(format.parse(workStage.getStartDay()),
								format.parse(newStage.getStartDay()))
								+ getDifferenceDaysExclusive(format.parse(workStage.getFinishDay()),
										format.parse(newStage.getFinishDay()))));
				hoursCriticalWorks += hourWork;

				System.out.println("diff1: " + getDifferenceDaysExclusive(format.parse(workStage.getStartDay()),
						format.parse(newStage.getStartDay())));
				System.out.println("diff2: " + getDifferenceDaysExclusive(format.parse(workStage.getFinishDay()),
						format.parse(newStage.getFinishDay())));
				System.out.println("hourwork: " + hourWork);
				System.out.println("ore lavori critici2 " + hoursCriticalWorks);

			}
			if (work.getClass().getName().equals("models.TaskBean")) {
				TaskBean workTask = (TaskBean) work;
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
							hourWork = rs.getInt("HoursRequired");
							System.out.println("ore Task: " + hourWork);
						}
					} catch (SQLException e) {
						e.printStackTrace();
						// TODO Handle with a Logger
					} finally {
						DbConnection.disconnect(currentConn, rs, statement);
					}
				}
				hoursCriticalWorks += hourWork;
				System.out.println("ore lavori critici3 " + hoursCriticalWorks);
			}
		}
		long hourSlack = HOURPERDAY * (getDifferenceDaysExclusive(minStart, format.parse(newStage.getStartDay()))
				+ (getDifferenceDaysExclusive(format.parse(newStage.getFinishDay()), maxFinish)));
		System.out.println("ore di slack: " + hourSlack);
		long availableHoursUser = workingHoursTOT - hoursCriticalWorks - hourSlack;

		return availableHoursUser;

	}

	public static long calculateAvailability(List<Object> criticalWorks, TaskBean newTask,
			Map.Entry<Integer, List<Object>> pair) throws ParseException {
		long dateDiffTOT = getDifferenceDays(minStart, maxFinish);
		System.out.println("dateDiffTOT: " + dateDiffTOT);
		long workingHoursTOT = HOURPERDAY * (dateDiffTOT);
		System.out.println("ore lavorative disponibili: " + workingHoursTOT);
		long hoursCriticalWorks = 0;
		for (Object work : criticalWorks) {

			long hourWork = 0;

			if (work.getClass().getName().equals("models.StageBean")) {
				StageBean workStage = (StageBean) work;
				long dateDiff = getDifferenceDays(
						calculateMin(format.parse(workStage.getStartDay()), format.parse(newTask.getStartDay())),
						calculateMax(format.parse(workStage.getFinishDay()), format.parse(newTask.getFinishDay())));

				hourWork = STAGEHOURPERDAY
						* (dateDiff - (getDifferenceDaysExclusive(format.parse(workStage.getStartDay()),
								format.parse(newTask.getStartDay()))
								+ getDifferenceDaysExclusive(format.parse(workStage.getFinishDay()),
										format.parse(newTask.getFinishDay()))));
				hoursCriticalWorks += hourWork;

				System.out.println("diff1: " + getDifferenceDaysExclusive(format.parse(workStage.getStartDay()),
						format.parse(newTask.getStartDay())));
				System.out.println("diff2: " + getDifferenceDaysExclusive(format.parse(workStage.getFinishDay()),
						format.parse(newTask.getFinishDay())));
				System.out.println("hourwork: " + hourWork);
				System.out.println("ore lavori critici2 " + hoursCriticalWorks);

			}
			if (work.getClass().getName().equals("models.TaskBean")) {
				TaskBean workTask = (TaskBean) work;
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
							hourWork = rs.getInt("HoursRequired");
							System.out.println("ore Task: " + hourWork);
						}
					} catch (SQLException e) {
						e.printStackTrace();
						// TODO Handle with a Logger
					} finally {
						DbConnection.disconnect(currentConn, rs, statement);
					}
				}
				hoursCriticalWorks += hourWork;
				System.out.println("ore lavori critici3 " + hoursCriticalWorks);
			}
		}

		long hourSlack = HOURPERDAY * (getDifferenceDaysExclusive(minStart, format.parse(newTask.getStartDay()))
				+ (getDifferenceDaysExclusive(format.parse(newTask.getFinishDay()), maxFinish)));
		System.out.println("ore di slack: " + hourSlack);
		long availableHoursUser = workingHoursTOT - hoursCriticalWorks - hourSlack;

		return availableHoursUser;
	}

	// calculate the critical works (the ones who conflict) of a user for a new
	// STAGE
	public static boolean calculateCriticalWork(Object work, StageBean newStage, DateFormat format)
			throws ParseException {
		if (work.getClass().getName().equals("models.ProjectBean")) {
			ProjectBean workProject = (ProjectBean) work;
			// check if the referenced project does not collide through time
			// with the newStage to assign
			if (!(format.parse(workProject.getDeadline()).before(format.parse(newStage.getStartDay()))
					|| format.parse(workProject.getStart()).after(format.parse(newStage.getFinishDay())))) {
				calculateMinMaxTOT(format.parse(workProject.getStart()), format.parse(workProject.getDeadline()));
				return true;
			}
		}

		if (work.getClass().getName().equals("models.StageBean")) {
			StageBean workStage = (StageBean) work;
			if (!(format.parse(workStage.getFinishDay()).before(format.parse(newStage.getStartDay()))
					|| format.parse(workStage.getStartDay()).after(format.parse(newStage.getFinishDay())))) {
				calculateMinMaxTOT(format.parse(workStage.getStartDay()), format.parse(workStage.getFinishDay()));
				return true;
			}
		}
		if (work.getClass().getName().equals("models.TaskBean")) {
			TaskBean workTask = (TaskBean) work;
			if (!(format.parse(workTask.getFinishDay()).before(format.parse(newStage.getStartDay()))
					|| format.parse(workTask.getStartDay()).after(format.parse(newStage.getFinishDay())))) {
				calculateMinMaxTOT(format.parse(workTask.getStartDay()), format.parse(workTask.getFinishDay()));
				return true;
			}
		}
		return false;
	}

	// calculate the critical works (the ones who conflict) of a user for a new
	// TASK
	public static boolean calculateCriticalWork(Object work, TaskBean newTask, DateFormat format)
			throws ParseException {
		if (work.getClass().getName().equals("models.StageBean")) {
			StageBean workStage = (StageBean) work;
			if (!(format.parse(workStage.getFinishDay()).before(format.parse(newTask.getStartDay()))
					|| format.parse(workStage.getStartDay()).after(format.parse(newTask.getFinishDay())))) {
				calculateMinMaxTOT(format.parse(workStage.getStartDay()), format.parse(workStage.getFinishDay()));
				return true;
			}
		}

		if (work.getClass().getName().equals("models.TaskBean")) {
			TaskBean workTask = (TaskBean) work;
			if (!(format.parse(workTask.getFinishDay()).before(format.parse(newTask.getStartDay()))
					|| format.parse(workTask.getStartDay()).after(format.parse(newTask.getFinishDay())))) {
				calculateMinMaxTOT(format.parse(workTask.getStartDay()), format.parse(workTask.getFinishDay()));
				return true;
			}
		}
		return false;
	}

	public static void calculateMinMaxTOT(Date start, Date finish) {
		if (start.before(minStart)) {
			minStart = start;
		}

		if (finish.after(maxFinish)) {
			maxFinish = finish;
		}
	}

	public static Date calculateMin(Date d1, Date d2) {
		if (d1.before(d2)) {
			return d1;
		}
		return d2;
	}

	public static Date calculateMax(Date d1, Date d2) {
		if (d1.after(d2)) {
			return d1;
		}
		return d2;
	}

	public static long getDifferenceDaysExclusive(Date d1, Date d2) {
		long diff = d2.getTime() - d1.getTime();
		return Math.abs(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
	}

	public static long getDifferenceDays(Date d1, Date d2) {
		long diff = getDifferenceDaysExclusive(d1, d2);
		if (diff == 0) {
			return diff;
		}
		return 1 + diff;
	}
}
