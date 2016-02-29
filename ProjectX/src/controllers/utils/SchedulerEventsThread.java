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

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import models.StageBean;
import models.UserDAO;

public class SchedulerEventsThread implements Runnable {
	
	public SchedulerEventsThread(int idProject){
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

	public static void main(String[] args) {
		final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		SchedulerEventsThread scheduler = new SchedulerEventsThread(74);
		service.scheduleAtFixedRate(scheduler, 0, 5, TimeUnit.SECONDS);
	}

	static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	String host = "localhost";
	String port = ""; /* TODO check port */
	final String pw = null; /* TODO check password */
	String toAddress = "";

	@Override
	public void run() {
		System.out.println("entro");
		List<StageBean> stages = new ArrayList<>();

		stages = models.StageDAO.getInstance().getStagesByIdProject(this.getIdProject());
		System.out.println("ho preso gli stage dal db");
		long criticalDate;
		for (StageBean stage : stages) {
			try {
				System.out.println("sono nel try");
				criticalDate = getDifferenceDays(format.parse(stage.getFinishDay()),
						format.parse(UtilityFunctions.GetCurrentDateTime()));
				System.out.println(criticalDate);
				//non critical stage delay
				System.out.println(stage.getIdStage());
				System.out.println("stage is critical? " + stage.getCritical());
				if (criticalDate < 0 && stage.getRateWorkCompleted() < 100 && "False".equalsIgnoreCase(stage.getCritical())) {
					System.out.println("RITARDO NON CRITICO");
					toAddress = models.UserDAO.getInstance().getGenericUserMailById(stage.getIdSupervisor());

					final String userName = stage.getSupervisorFullname();
					final String subject = "[STAGE DELAY]";
					final String message = "The stage should have ended but it has not yet done it.";

					controllers.utils.SendEmail.sendEmail(host, port, userName, pw, toAddress, subject, message);
				} //critical stage delay
				else if (criticalDate < 0 && stage.getRateWorkCompleted() < 99 && "True".equals(stage.getCritical())) {
					System.out.println("RITARDO CRITICO");
					UserDAO.getInstance().getGenericUserMailById(stage.getIdSupervisor());

					final String userName = stage.getSupervisorFullname();
					final String subject = "[PROJECT DELAY]";
					final String message = "A critical stage should have ended but it has not yet done it and now the entire project in delaying.";

					String toAddressProj = models.UserDAO.getInstance()
							.getProjectManagerMailByIdStage(stage.getIdStage());

					controllers.utils.SendEmail.sendEmail(host, port, userName, pw, toAddress, subject, message);
					controllers.utils.SendEmail.sendEmail(host, port, userName, pw, toAddressProj, subject, message);

				}
			} catch (ParseException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during parsing a date", e);
			} catch (AddressException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during sending a mail", e);
			} catch (MessagingException e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during sending a mail", e);
			}
		}

	}
	public static long getDifferenceDays(Date d1, Date d2) {
		long diff = d1.getTime() - d2.getTime();
		if(diff < 0){
			return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)) - 1;
		}
		if(diff > 0){
			return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)) + 1;
		}
		return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
	}
}
