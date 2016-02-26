package controllers.utils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import models.StageBean;

public class SchedulerEvents {

	private SchedulerEvents() {

	}

	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) throws ParseException {
		final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		String host = "localhost";
		String port = ""; /* TODO check port */
		final String password = null; /* TODO check password */
		String toAddress = "";

		service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				List<StageBean> stages = new ArrayList<>();

				/*
				 * TODO da rendere statico ? stage =
				 * models.StageDAO.getStagesByIdProject(idproject);
				 */

				long dataCritica;
				for (StageBean stage : stages) {
					try {
						dataCritica = getDifferenceDays(format.parse(stage.getFinishDay()),
								format.parse(GetCurrentDateTime()));
						System.out.println(dataCritica);
						/* STAGE NON CRITICO IN RITARDO */
						if (dataCritica < 0 && stage
								.getRateWorkCompleted() < 99 /*
																 * &&
																 * varDaCiclo.
																 * getCritical()
																 * != true
																 */) {
							/*
							 * TODO da rendere statico toAddress =
							 * models.UserDAO.getGenericUserMailById(stage.
							 * getIdSupervisor());
							 */

							final String userName = stage.getSupervisorFullname();
							final String subject = "[STAGE DELAY]";
							final String message = "The stage should have ended but it has not yet done it.";

							controllers.utils.SendEmail.sendEmail(host, port, userName, password, toAddress, subject,
									message);
						} /* STAGE CRITICO IN RITARDO */
						else if (dataCritica < 0 && stage
								.getRateWorkCompleted() < 99 /*
																 * &&
																 * varDaCiclo.
																 * getCritical()
																 * == true
																 */) {
							/*
							 * TODO da rendere statico toAddress =
							 * models.UserDAO.getGenericUserMailById(stage.
							 * getIdSupervisor());
							 */

							final String userName = stage.getSupervisorFullname();
							final String subject = "[PROJECT DELAY]";
							final String message = "A critical stage should have ended but it has not yet done it and now the entire project in delaying.";

							/*
							 * TODO da rendere statico
							 * 
							 * String toAddressProj =
							 * models.UserDAO.getProjectManagerMailByIdStage(
							 * stage.getIdStage());
							 */

							controllers.utils.SendEmail.sendEmail(host, port, userName, password, toAddress, subject,
									message);
							controllers.utils.SendEmail.sendEmail(host, port, userName, password, toAddress, subject,
									message);/*
												 * TODO change in address of the
												 * projectmanager
												 */

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
		}, 0, 24, TimeUnit.HOURS);
	}

	public static String GetCurrentDateTime() {
		/* get current date time with Calendar() */
		Date date = new Date();
		String dateStr = format.format(date);
		System.out.println(dateStr);
		return dateStr;
	}

	/* TODO si può togliere? */
	public static long getDifferenceDays(Date d1, Date d2) {
		long diff = d2.getTime() - d1.getTime();
		long diff2 = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		if (diff2 == 0) {
			return diff2;
		}
		return 1 + diff2;
	}
}
