package controllers.utils;

import models.ProjectBean;
import java.io.IOException;

import models.StageBean;
import models.TaskBean;
import models.TaskDAO;
import models.UserBean;
import utils.GetWorkhoursProperties;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author alessandro
 * The class computes the available users in order to assign to them supervision of a stage or develop a task
 */
public class CalculateAvailableUsers {
    private static int HOURPERDAY;
    private static int PROJECTHOURPERDAY;
    private static int STAGEHOURPERDAY;

    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private static Date minStart = new Date();
    private static Date maxFinish = new Date();

    private static final Logger LOGGER = Logger.getLogger(CalculateAvailableUsers.class.getName());

    private CalculateAvailableUsers() {};

    
    /**
     * @param workMap
     * @param newTask
     * @return list of UserBean
     * Calculate the list of the available users for a task
     */
    public static List<UserBean> calculate(Map<Integer, List<Object>> workMap, TaskBean newTask) {

        getProperties();

        format.setTimeZone(TimeZone.getTimeZone("GTM"));
        List<UserBean> availableUsers = new ArrayList<>();
        Iterator<Map.Entry<Integer, List<Object>>> mapIterator = workMap.entrySet().iterator();
        try {
            while (mapIterator.hasNext()) {
                minStart = format.parse(newTask.getStartDay());
                maxFinish = format.parse(newTask.getFinishDay());

                Map.Entry<Integer, List<Object>> pair = mapIterator.next();

                Iterator<Object> arrayListIterator = pair.getValue().iterator();
                availableUsers = getAvailableUsers(newTask, availableUsers, pair, arrayListIterator);
            }
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during parsing a date", e);
        }
        return availableUsers;
    }

    
    /**
     * @param newTask
     * @param availableUsers
     * @param pair
     * @param arrayListIterator
     * @return
     * @throws ParseException
     */
    private static List<UserBean> getAvailableUsers(TaskBean newTask, List<UserBean> availableUsers,
            Map.Entry<Integer, List<Object>> pair, Iterator<Object> arrayListIterator) throws ParseException {
        List<Object> criticalWorks = new ArrayList<>();
        if (pair.getValue() == null) {
            UserBean userFree = new UserBean();
            userFree.setIdUser(pair.getKey());
            long hoursAvailable = HOURPERDAY * UtilityFunctions.getDifferenceDays(format.parse(newTask.getStartDay()),
                    format.parse(newTask.getFinishDay()));
            userFree.setTemporaryHoursAvailable(hoursAvailable);
            availableUsers.add(userFree);
        }

        while (arrayListIterator.hasNext()) {
            Object work = arrayListIterator.next();
            boolean isCritical = calculateCriticalWork(work, newTask, format);
            if (isCritical && work != null) {
                criticalWorks.add(work);
            }
        }

        UserBean availableUser = isAvailable(newTask, pair, criticalWorks);
        if (availableUser != null) {
            availableUsers.add(availableUser);
        }
        return availableUsers;
    }

    
    /**
     * @param newTask
     * @param pair
     * @param criticalWorks
     * @return
     */
    private static UserBean isAvailable(TaskBean newTask, Map.Entry<Integer, List<Object>> pair,
            List<Object> criticalWorks) {
        long hourAvailable = Long.MIN_VALUE;

        try {
            hourAvailable = getAvailableHours(criticalWorks, newTask, pair);
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during parsing a date", e);
        }

        if (hourAvailable > 0) {
            UserBean availableUser = new UserBean();
            if (pair != null) {
                availableUser.setIdUser(pair.getKey());
                availableUser.setTemporaryHoursAvailable(hourAvailable);
                return availableUser;
            }
        }
        return null;

    }

    
    /**
     * @param criticalWorks
     * @param newTask
     * @param pair
     * @return a long that contains the available hours of a user
     * @throws ParseException
     */
    private static long getAvailableHours(List<Object> criticalWorks, TaskBean newTask,
            Map.Entry<Integer, List<Object>> pair) throws ParseException {
        long dateDiffTOT = UtilityFunctions.getDifferenceDays(minStart, maxFinish);
        long workingHoursTOT = HOURPERDAY * (dateDiffTOT);
        long hoursCriticalWorks = 0;
        for (Object work : criticalWorks) {

            long hourWork = 0;

            if (work instanceof models.StageBean) {
                StageBean workStage = (StageBean) work;
                long dateDiff = UtilityFunctions.getDifferenceDays(
                        UtilityFunctions.calculateMin(format.parse(workStage.getStartDay()),
                                format.parse(newTask.getStartDay())),
                        UtilityFunctions.calculateMax(format.parse(workStage.getFinishDay()),
                                format.parse(newTask.getFinishDay())));

                hourWork = STAGEHOURPERDAY * (dateDiff
                        - (UtilityFunctions.getDifferenceDaysExclusive(format.parse(workStage.getStartDay()),
                                format.parse(newTask.getStartDay()))
                        + UtilityFunctions.getDifferenceDaysExclusive(format.parse(workStage.getFinishDay()),
                                format.parse(newTask.getFinishDay()))));
                hoursCriticalWorks += hourWork;
            }
            if (work instanceof models.TaskBean) {
                TaskBean workTask = (TaskBean) work;
                hourWork = TaskDAO.getInstance().getTaskHourRequested(pair, hourWork, workTask);
                hoursCriticalWorks += hourWork;
            }
        }

        long hourSlack = HOURPERDAY * (UtilityFunctions.getDifferenceDaysExclusive(minStart,
                format.parse(newTask.getStartDay()))
                + (UtilityFunctions.getDifferenceDaysExclusive(format.parse(newTask.getFinishDay()), maxFinish)));
        return workingHoursTOT - hoursCriticalWorks - hourSlack;
    }

    
    /**
     * @param work
     * @param newTask
     * @param format
     * @return boolean
     * @throws ParseException
     * 
     */
    private static boolean calculateCriticalWork(Object work, TaskBean newTask, DateFormat format)
            throws ParseException {
        if (work instanceof models.StageBean) {
            StageBean workStage = (StageBean) work;
            if (!(format.parse(workStage.getFinishDay()).before(format.parse(newTask.getStartDay()))
                    || format.parse(workStage.getStartDay()).after(format.parse(newTask.getFinishDay())))) {
                calculateMinMaxTOT(format.parse(workStage.getStartDay()), format.parse(workStage.getFinishDay()));
                return true;
            }
        }

        if (work instanceof models.TaskBean) {
            TaskBean workTask = (TaskBean) work;
            if (!(format.parse(workTask.getFinishDay()).before(format.parse(newTask.getStartDay()))
                    || format.parse(workTask.getStartDay()).after(format.parse(newTask.getFinishDay())))) {
                calculateMinMaxTOT(format.parse(workTask.getStartDay()), format.parse(workTask.getFinishDay()));
                return true;
            }
        }
        return false;
    }

    
    /**
     * @param workMap
     * @param newStage
     * @return a list of UserBean
     */
    public static List<UserBean> calculate(Map<Integer, List<Object>> workMap, StageBean newStage) {

        getProperties();

        format.setTimeZone(TimeZone.getTimeZone("GTM"));
        List<UserBean> availableUsers = new ArrayList<>();
        Iterator<Map.Entry<Integer, List<Object>>> mapIterator = workMap.entrySet().iterator();
        try {
            while (mapIterator.hasNext()) {
                minStart = format.parse(newStage.getStartDay());
                maxFinish = format.parse(newStage.getFinishDay());

                Map.Entry<Integer, List<Object>> pair = mapIterator.next();

                Iterator<Object> arrayListIterator = pair.getValue().iterator();
                availableUsers = getAvailableUsers(newStage, availableUsers, pair, arrayListIterator);
            }
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during parsing a date", e);
        }
        return availableUsers;
    }

    
    /**
     * @param newStage
     * @param availableUsers
     * @param pair
     * @param arrayListIterator
     * @return a list of users available for a stage
     * @throws ParseException
     */
    private static List<UserBean> getAvailableUsers(StageBean newStage, List<UserBean> availableUsers,
            Map.Entry<Integer, List<Object>> pair, Iterator<Object> arrayListIterator) throws ParseException {
        List<Object> criticalWorks = new ArrayList<>();
        if (pair.getValue() == null) {
            UserBean userFree = new UserBean();
            userFree.setIdUser(pair.getKey());
            long hoursAvailable = HOURPERDAY * UtilityFunctions.getDifferenceDays(format.parse(newStage.getStartDay()),
                    format.parse(newStage.getFinishDay()));
            userFree.setTemporaryHoursAvailable(hoursAvailable);
            availableUsers.add(userFree);
        }
        while (arrayListIterator.hasNext()) {
            Object work = arrayListIterator.next();
            boolean isCritical = calculateCriticalWork(work, newStage, format);
            if (isCritical && work != null) {
                criticalWorks.add(work);
            }
        }

        UserBean availableUser = isAvailable(newStage, pair, criticalWorks);
        if (availableUser != null) {
            availableUsers.add(availableUser);
        }
        return availableUsers;
    }

    
    /**
     * @param newStage
     * @param pair
     * @param criticalWorks
     * @return a user if he/she is available
     */
    private static UserBean isAvailable(StageBean newStage, Map.Entry<Integer, List<Object>> pair,
            List<Object> criticalWorks) {
        long hourAvailable = Long.MIN_VALUE;
        long hourNewStage = Long.MIN_VALUE;
        try {
            hourAvailable = getAvailableHours(criticalWorks, newStage, pair);
            hourNewStage = STAGEHOURPERDAY * (UtilityFunctions.getDifferenceDays(format.parse(newStage.getStartDay()),
                    format.parse(newStage.getFinishDay())));
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during parsing a date", e);
        }
        if (hourAvailable >= hourNewStage) {
            UserBean availableUser = new UserBean();
            if (pair != null) {
                availableUser.setIdUser(pair.getKey());
                availableUser.setTemporaryHoursAvailable(hourAvailable);
                return availableUser;
            }
        }
        return null;
    }

    
    /**
     * @param criticalWorks
     * @param newStage
     * @param pair
     * @return a long that contains the available hours of a user
     * @throws ParseException
     */
    private static long getAvailableHours(List<Object> criticalWorks, StageBean newStage,
            Map.Entry<Integer, List<Object>> pair) throws ParseException {
        long dateDiffTOT = UtilityFunctions.getDifferenceDays(minStart, maxFinish);
        long workingHoursTOT = HOURPERDAY * (dateDiffTOT);
        long hoursCriticalWorks = 0;
        for (Object work : criticalWorks) {

            long hourWork = 0;

            if (work instanceof models.ProjectBean) {

                ProjectBean workProject = (ProjectBean) work;
                long dateDiff = UtilityFunctions.getDifferenceDays(
                        UtilityFunctions.calculateMin(format.parse(workProject.getStart()),
                                format.parse(newStage.getStartDay())),
                        UtilityFunctions.calculateMax(format.parse(workProject.getDeadline()),
                                format.parse(newStage.getFinishDay())));

                hourWork = PROJECTHOURPERDAY
                        * (dateDiff - (UtilityFunctions.getDifferenceDaysExclusive(format.parse(workProject.getStart()),
                                format.parse(newStage.getStartDay()))
                        + UtilityFunctions.getDifferenceDaysExclusive(format.parse(workProject.getDeadline()),
                                format.parse(newStage.getFinishDay()))));

                hoursCriticalWorks += hourWork;
            }

            if (work instanceof models.StageBean) {
                StageBean workStage = (StageBean) work;
                long dateDiff = UtilityFunctions.getDifferenceDays(
                        UtilityFunctions.calculateMin(format.parse(workStage.getStartDay()),
                                format.parse(newStage.getStartDay())),
                        UtilityFunctions.calculateMax(format.parse(workStage.getFinishDay()),
                                format.parse(newStage.getFinishDay())));

                hourWork = STAGEHOURPERDAY * (dateDiff
                        - (UtilityFunctions.getDifferenceDaysExclusive(format.parse(workStage.getStartDay()),
                                format.parse(newStage.getStartDay()))
                        + UtilityFunctions.getDifferenceDaysExclusive(format.parse(workStage.getFinishDay()),
                                format.parse(newStage.getFinishDay()))));
                hoursCriticalWorks += hourWork;

            }
            if (work instanceof models.TaskBean) {
                TaskBean workTask = (TaskBean) work;
                hourWork = TaskDAO.getInstance().getTaskHourRequested(pair, hourWork, workTask);
                hoursCriticalWorks += hourWork;
            }
        }
        long hourSlack = HOURPERDAY * (UtilityFunctions.getDifferenceDaysExclusive(minStart,
                format.parse(newStage.getStartDay()))
                + (UtilityFunctions.getDifferenceDaysExclusive(format.parse(newStage.getFinishDay()), maxFinish)));
        return workingHoursTOT - hoursCriticalWorks - hourSlack;
    }

   
	/**
	 * @param work
	 * @param newStage
	 * @param format
	 * @return boolean
	 * @throws ParseException
	 */
	private static boolean calculateCriticalWork(Object work, StageBean newStage, DateFormat format)
			throws ParseException {
		if (work instanceof models.ProjectBean) {
			ProjectBean workProject = (ProjectBean) work;
			if (!(format.parse(workProject.getDeadline()).before(format.parse(newStage.getStartDay()))
					|| format.parse(workProject.getStart()).after(format.parse(newStage.getFinishDay())))) {
				calculateMinMaxTOT(format.parse(workProject.getStart()), format.parse(workProject.getDeadline()));
				return true;
			}
		}

		if (work instanceof models.StageBean) {
			StageBean workStage = (StageBean) work;
			if (!(format.parse(workStage.getFinishDay()).before(format.parse(newStage.getStartDay()))
					|| format.parse(workStage.getStartDay()).after(format.parse(newStage.getFinishDay())))) {
				calculateMinMaxTOT(format.parse(workStage.getStartDay()), format.parse(workStage.getFinishDay()));
				return true;
			}
		}
		if (work instanceof models.TaskBean) {
			TaskBean workTask = (TaskBean) work;
			if (!(format.parse(workTask.getFinishDay()).before(format.parse(newStage.getStartDay()))
					|| format.parse(workTask.getStartDay()).after(format.parse(newStage.getFinishDay())))) {
				calculateMinMaxTOT(format.parse(workTask.getStartDay()), format.parse(workTask.getFinishDay()));
				return true;
			}
		}
		return false;
	}

	/**
	 * @param start
	 * @param finish
	 */
	public static void calculateMinMaxTOT(Date start, Date finish) {
		if (start.before(minStart)) {
			minStart = start;
		}

		if (finish.after(maxFinish)) {
			maxFinish = finish;
		}
	}

    /**
     * Get work hours properties from a configuration file
     */
    private static void getProperties() {
        try {
            Integer[] propertiesValues = GetWorkhoursProperties.getInstance().getPropValues();
            HOURPERDAY = propertiesValues[0];
            PROJECTHOURPERDAY = propertiesValues[1];
            STAGEHOURPERDAY = propertiesValues[2];
        } catch (IOException e1) {
            LOGGER.log(Level.SEVERE, "Something went wrong during getting properties values", e1);
        }
    }

}
