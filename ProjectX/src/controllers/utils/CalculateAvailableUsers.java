package controllers.utils;

import models.ProjectBean;
import models.StageBean;
import models.UserBean;

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

public class CalculateAvailableUsers{
	private static final int HOURPERDAY = 8;
	private static final int PROJECTHOURPERDAY = 4;
	private static final int STAGEHOURPERDAY = 2;
	
	private static DateFormat format = new SimpleDateFormat("yyyy-mm-dd");


	
	private static Date minStart = new Date();
	private static Date maxFinish = new Date();
	//TODO overload the method in case of task assignement
	public static List<UserBean> calculate(Map<Integer, List<Object>> workMap, StageBean newStage) throws ParseException{
		format.setTimeZone(TimeZone.getTimeZone("GTM"));
		List<UserBean> availableUsers = new ArrayList<UserBean>();
		Iterator<Map.Entry<Integer, List<Object>>> mapIterator = workMap.entrySet().iterator();
		while (mapIterator.hasNext()) {
			minStart = format.parse(newStage.getStartDay());
			maxFinish = format.parse(newStage.getFinishDay());
			
			Map.Entry<Integer, List<Object>> pair = (Map.Entry<Integer, List<Object>>) mapIterator.next();
			List<Object> criticalWorks = new ArrayList<Object>();
			

			Iterator<Object> arrayListIterator = pair.getValue().iterator();
			while (arrayListIterator.hasNext()) {	
				try {
					Object work = arrayListIterator.next();
					boolean isCritical = calculateCriticalWork(work, newStage, format);
					if (isCritical) {
						if(work!=null){
						criticalWorks.add(work);
						}
					}
				} catch (ParseException e) {
					// TODO handle exception with a logger
				}
			}
			
			long hourAvailable = calculateAvailability(criticalWorks, newStage);
			System.out.println(hourAvailable);
			long hourNewStage = STAGEHOURPERDAY*(getDifferenceDays(format.parse(newStage.getStartDay()), format.parse(newStage.getFinishDay())));
			if (hourAvailable >= hourNewStage){
				UserBean availableUser = new UserBean();
				if(pair!=null){
				availableUser.setIdUser(pair.getKey());
				availableUsers.add(availableUser);
				}
			}
		}
		return availableUsers;
	}
	
	public static long calculateAvailability(List<Object> criticalWorks, StageBean newStage) throws ParseException {
		long dateDiff = getDifferenceDays(minStart, maxFinish);
		long workingHoursTOT = HOURPERDAY * (dateDiff + 1);
		System.out.println("ore lavorative disponibili: " + workingHoursTOT);
		long hoursCriticalWorks = 0;
		for (Object work : criticalWorks) {
			long hourWork = 0;
			if (work.getClass().getName().equals("models.ProjectBean")) {
				ProjectBean workProject = (ProjectBean) work;
				hourWork = PROJECTHOURPERDAY * (1 + getDifferenceDays(format.parse(workProject.getStart()),
						format.parse(workProject.getDeadline())));
				
				hoursCriticalWorks += hourWork;
			}

			if (work.getClass().getName().equals("models.StageBean")) {
				StageBean workProject = (StageBean) work;
				hourWork = STAGEHOURPERDAY * (1 + getDifferenceDays(format.parse(workProject.getStartDay()),
						format.parse(workProject.getFinishDay())));
				hoursCriticalWorks += hourWork;

			} else {
				// case task
			}
		}
		long hourSlack = HOURPERDAY * (getDifferenceDays(minStart, format.parse(newStage.getStartDay()))
				+ (getDifferenceDays(format.parse(newStage.getFinishDay()), maxFinish)));
		System.out.println("ore di slack: " + hourSlack);
		long availableHoursUser = workingHoursTOT - hoursCriticalWorks - hourSlack;
		 
		return availableHoursUser;

	}
	
	public static boolean calculateCriticalWork (Object work, StageBean newStage, DateFormat format) throws ParseException{
		if(work.getClass().getName().equals("models.ProjectBean")){
			ProjectBean workProject = (ProjectBean)work;
			//check if the referenced project does not collide through time with the newStage to assign
			if(!(format.parse(workProject.getDeadline()).before(format.parse(newStage.getStartDay())) || format.parse(workProject.getStart()).after(format.parse(newStage.getFinishDay())))){
				calculateMinMax(format.parse(workProject.getStart()),format.parse(workProject.getDeadline()));
				return true;
			}
		}
		
		if(work.getClass().getName().equals("models.StageBean")){
			StageBean workStage = (StageBean)work;
			if(!(format.parse(workStage.getFinishDay()).before(format.parse(newStage.getStartDay())) || format.parse(workStage.getStartDay()).after(format.parse(newStage.getFinishDay())))){
				calculateMinMax(format.parse(workStage.getStartDay()),format.parse(workStage.getFinishDay()));
				return true;
			}
		}
		/*if(work.getClass().getName().equals("TaskBean")){
			
    	}
    	*/
		return false;
	}
	
	public static void calculateMinMax(Date start, Date finish){
		if(start.before(minStart)){
			minStart = start;
		}
		
		if(finish.after(maxFinish)){
			maxFinish = finish;
		}
	}
	
	public static long getDifferenceDays(Date d1, Date d2) {
	    long diff = d2.getTime() - d1.getTime();
	    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
}
