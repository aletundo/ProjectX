package controllers.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

	private static final Logger LOGGER = Logger.getLogger(SchedulerEventsThread.class.getName());

	public static void main(String[] args) {
		final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(new SchedulerEventsThread(), 0, 24, TimeUnit.HOURS);
	}

	static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	String host = "localhost";
	String port = ""; /* TODO check port */
	final String pw = null; /* TODO check password */
	String toAddress = "";

	@Override
	public void run() {
		List<StageBean> stages = new ArrayList<>();

		// stages =
		// models.StageDAO.getInstance().getStagesByIdProject(idproject);

		long dataCritica;
		for (StageBean stage : stages) {
			try {
				dataCritica = UtilityFunctions.getDifferenceDays(format.parse(stage.getFinishDay()),
						format.parse(UtilityFunctions.GetCurrentDateTime()));
				System.out.println(dataCritica);
				/* STAGE NON CRITICO IN RITARDO */
				if (dataCritica < 0 && stage.getRateWorkCompleted() < 100 && "False".equals(stage.getCritical())) {

					toAddress = models.UserDAO.getInstance().getGenericUserMailById(stage.getIdSupervisor());

					final String userName = stage.getSupervisorFullname();
					final String subject = "[STAGE DELAY]";
					final String message = "The stage should have ended but it has not yet done it.";

					controllers.utils.SendEmail.sendEmail(host, port, userName, pw, toAddress, subject, message);
				} /* STAGE CRITICO IN RITARDO */
				else if (dataCritica < 0 && stage.getRateWorkCompleted() < 99 && "True".equals(stage.getCritical())) {

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
}
