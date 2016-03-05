package controllers.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import models.StageBean;
import models.UserDAO;

public class NotifyManager {
	
	private static final Logger LOGGER = Logger.getLogger(NotifyManager.class.getName());
	private static final String host = "localhost";
	private static final String from = "fooCompany@bar.baz";
	
	public static void notifyDelayStartStage(int idSupervisor){
		try {
            String to = UserDAO.getInstance().getGenericUserMailById(idSupervisor);
            String subject = "[PROJECT DELAY]";
            String message = "A stage should have started but one precedent stage has not yet finished";

            controllers.utils.SendEmail.sendEmail(to, from, subject, message, host);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during sending an email", e);

        }
	}
	
	public static void notifyStagesNonCritical(int idSupervisor){
		
		try {

        	String to = UserDAO.getInstance().getGenericUserMailById(idSupervisor);
            String subject = "[STAGE DELAY]";
            String message = "The stage should have ended but it has not yet done it.";
            controllers.utils.SendEmail.sendEmail(to, from, subject, message, host);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during sending an email", e);

        }
	}
	
	public static void notifyStartStages(int idSupervisor){
		try { 
            String to = models.UserDAO.getInstance().getGenericUserMailById(idSupervisor);
            String subject = "[STAGE START]";
            String message = "The stage started";
            controllers.utils.SendEmail.sendEmail(to, from, subject, message, host);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during sending an email", e);

        }
	}
	
	public static void notifyCriticalStages(StageBean stage){
		try {

            String to = UserDAO.getInstance().getGenericUserMailById(stage.getIdStage());
            String pmMail = UserDAO.getInstance().getProjectManagerMailByIdStage(stage.getIdStage());
            String subject = "[PROJECT DELAY]";
            String message = "A critical stage should have ended but it has not yet done it and now the entire project is delaying.";

            controllers.utils.SendEmail.sendEmail(to, from, subject, message, host);
            controllers.utils.SendEmail.sendEmail(pmMail, from, subject, message, host);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during sending an email", e);

        }
	}

}
