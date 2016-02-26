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

	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) throws ParseException {
		final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		String host = "localhost";
		String port = "90"; /*TODO check port*/
		//final String userName;
		final String password = "password"; /*TODO check password*/
		String toAddress = "someAddress"; /*TODO check toAddress*/
		//String subject;
		//String message;
		
		service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {

				//int idproject;
				List<StageBean> stage = new ArrayList<StageBean>();

				/*
				 * TODO da rendere statico ? stage =
				 * models.StageDAO.getStagesByIdProject(idproject);
				 */

				long dataCritica;
				for (StageBean varDaCiclo : stage) {
					try {
						dataCritica = CalculateAvailableUsers.getDifferenceDays(format.parse(varDaCiclo.getFinishDay()),
								format.parse(GetCurrentDateTime()));
						/*STAGE NON CRITICO IN RITARDO*/
						if (dataCritica < 0 && varDaCiclo.getRateWorkCompleted() < 99 /*&& varDaCiclo.getCritical() != true*/) {
							
							/*TODO altra query per indirizzo email:
							"toAddress" */
							
							final String userName=varDaCiclo.getSupervisorFullname();
							final String subject="[STAGE DELAY]";
							final String message="The stage should have ended but it has not yet done it.";
							
							controllers.utils.SendEmail.sendEmail(host,port,userName,password,toAddress,subject,message);
						}/*STAGE CRITICO IN RITARDO*/
						else if (dataCritica < 0 && varDaCiclo.getRateWorkCompleted() < 99 /*&& varDaCiclo.getCritical() == true*/){
							
							/*TODO altra query per indirizzo email:
							"toAddress" */
							
							final String userName=varDaCiclo.getSupervisorFullname();
							final String subject="[PROJECT DELAY]";
							final String message="A critical stage should have ended but it has not yet done it and now the entire project in delaying.";
							
							controllers.utils.SendEmail.sendEmail(host,port,userName,password,toAddress,subject,message);
							controllers.utils.SendEmail.sendEmail(host,port,userName,password,toAddress,subject,message);/*TODO change in address of the projectmanager*/

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
		return dateStr;
	}
}
