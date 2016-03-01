package controllers.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import context.Subject;
import models.StageBean;

public class SchedulerEventsThread extends Subject implements Runnable {

	public SchedulerEventsThread(int idProject) {
		this.idProject = idProject;
	}

	private int idProject;

	public int getIdProject() {
		return idProject;
	}

	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}

	private static final Logger LOGGER = Logger.getLogger(SchedulerEventsThread.class.getName());

	// public static void main(String[] args) {
	// final ScheduledExecutorService service =
	// Executors.newSingleThreadScheduledExecutor();
	// SchedulerEventsThread scheduler = new SchedulerEventsThread(74);
	// scheduler.attach(new StagesObserver(scheduler));
	// service.scheduleAtFixedRate(scheduler, 0, 24, TimeUnit.HOURS);
	// }

	public SchedulerEventsThread() {
		super();
	}

	static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	String host = "localhost";
	String port = "8080"; /* TODO check port */
	final String pw = "bla"; /* TODO check password */
	String toAddress = "asd@mail.com";

	@Override
	public void run() {
		List<StageBean> stagesCritical = new ArrayList<>();
		List<StageBean> stagesNonCritical = new ArrayList<>();

		stagesCritical = getStagesCritical();
		stagesNonCritical = getStagesNonCritical();

		long criticalDate = 0;
		for (StageBean stageNonCritical : stagesNonCritical) {
			try {
				criticalDate = getDifferenceDays(format.parse(stageNonCritical.getFinishDay()),
						format.parse(UtilityFunctions.GetCurrentDateTime()));
				if (criticalDate < 0 && stageNonCritical.getRateWorkCompleted() < 100) {
					notifyObservers();
				}
			} catch (ParseException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during parsing a date", e);
			}
		}
		for (StageBean stageCritical : stagesCritical) {
			try {
				criticalDate = getDifferenceDays(format.parse(stageCritical.getFinishDay()),
						format.parse(UtilityFunctions.GetCurrentDateTime()));
				if (criticalDate < 0 && stageCritical.getRateWorkCompleted() < 99
						&& "True".equals(stageCritical.getCritical())) {
					notifyObservers();
				}
			} catch (ParseException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during parsing a date", e);
			}
		}
	}

	public static long getDifferenceDays(Date d1, Date d2) {
		long diff = d1.getTime() - d2.getTime();
		if (diff < 0) {
			return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)) - 1;
		}
		if (diff > 0) {
			return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)) + 1;
		}
		return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
	}

	@Override
	public List<StageBean> getStagesNonCritical() {
		List<StageBean> stages = new ArrayList<>();
		/* TODO QUERY */
		stages = models.StageDAO.getInstance().getStagesByIdProject(this.getIdProject());
		return stages;
	}

	@Override
	public List<StageBean> getStagesCritical() {
		List<StageBean> stages = new ArrayList<>();
		/* TODO QUERY */
		stages = models.StageDAO.getInstance().getStagesByIdProject(this.getIdProject());
		return stages;
	}
}
