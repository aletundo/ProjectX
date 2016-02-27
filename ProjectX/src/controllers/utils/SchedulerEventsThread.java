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

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import models.StageBean;
import models.UserDAO;

public class SchedulerEventsThread implements Runnable {
	
	public static void main (String [] args){
		final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(new SchedulerEventsThread(),0,24,TimeUnit.HOURS);
	}
	
	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	String host = "localhost";
	String port = ""; /* TODO check port */
	final String password = null; /* TODO check password */
	String toAddress = "";

	
	@Override
	public void run() {
		List<StageBean> stages = new ArrayList<>();

		// stages =
		// models.StageDAO.getInstance().getStagesByIdProject(idproject);

		long dataCritica;
		for (StageBean stage : stages) {
			try {
				dataCritica = UtilityFunctions.getDifferenceDays(format.parse(stage.getFinishDay()), format.parse(GetCurrentDateTime()));
				System.out.println(dataCritica);
				/* STAGE NON CRITICO IN RITARDO */
				if (dataCritica < 0 && stage.getRateWorkCompleted() < 100 && "False".equals(stage.getCritical())) {
					
					
					toAddress = models.UserDAO.getInstance().getGenericUserMailById(stage.getIdSupervisor());

					final String userName = stage.getSupervisorFullname();
					final String subject = "[STAGE DELAY]";
					final String message = "The stage should have ended but it has not yet done it.";

					controllers.utils.SendEmail.sendEmail(host, port, userName, password, toAddress, subject, message);
				} /* STAGE CRITICO IN RITARDO */
				else if (dataCritica < 0 && stage
						.getRateWorkCompleted() < 99 && "True".equals(stage.getCritical())) {

					UserDAO.getInstance().getGenericUserMailById(stage.getIdSupervisor());

					final String userName = stage.getSupervisorFullname();
					final String subject = "[PROJECT DELAY]";
					final String message = "A critical stage should have ended but it has not yet done it and now the entire project in delaying.";

					String toAddressProj = models.UserDAO.getInstance()
							.getProjectManagerMailByIdStage(stage.getIdStage());

					controllers.utils.SendEmail.sendEmail(host, port, userName, password, toAddress, subject, message);
					controllers.utils.SendEmail.sendEmail(host, port, userName, password, toAddressProj, subject,
							message);

				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static String GetCurrentDateTime() {
		/* get current date time with Calendar() */
		Date date = new Date();
		String dateStr = format.format(date);
		System.out.println(dateStr);
		return dateStr;
	}
}
