package controllers.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import models.StageBean;
import models.UserDAO;

/**
 * @author alessandro The aim of NotifyManager is to notify to project managers
 *         and supervisors delays of stages and their status
 */
public class NotifyManager {

	private static final Logger LOGGER = Logger.getLogger(NotifyManager.class.getName());
	private static final String host = "localhost";
	private static final String from = "fooCompany@bar.baz";

	/**
	 * @param idSupervisor
	 *            Send an email to a supervisor if his stage should start but a
	 *            precedent stages has not yet finished
	 */
	public static void notifyDelayStartStage(int idSupervisor) {
		try {
			String to = UserDAO.getInstance().getGenericUserMailById(idSupervisor);
			String subject = "[STAGE DELAY]";
			String message = "A stage should have started but one precedent stage has not yet finished";

			controllers.utils.SendEmail.sendEmail(to, from, subject, message, host);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during sending an email", e);

		}
	}

	/**
	 * @param idSupervisor
	 *            Send an email to a supervisor if his stage is on late
	 */
	public static void notifyStagesNonCritical(int idSupervisor) {

		try {

			String to = UserDAO.getInstance().getGenericUserMailById(idSupervisor);
			String subject = "[STAGE DELAY]";
			String message = "The stage should have ended but it has not yet done it.";
			controllers.utils.SendEmail.sendEmail(to, from, subject, message, host);

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during sending an email", e);

		}
	}

	/**
	 * @param idSupervisor
	 *            Send an email to a supervisor if his stage starts
	 */
	public static void notifyStartStages(int idSupervisor) {
		try {
			String to = models.UserDAO.getInstance().getGenericUserMailById(idSupervisor);
			String subject = "[STAGE START]";
			String message = "The stage started";
			controllers.utils.SendEmail.sendEmail(to, from, subject, message, host);

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during sending an email", e);

		}
	}

	/**
	 * @param stage
	 *            Send an email to a supervisor and the projects if a critical
	 *            stage is on late
	 */
	public static void notifyCriticalStages(StageBean stage) {
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

	/**
	 * @param idSupervisor
	 *            Send an email to a supervisor if his stage has been completed
	 */
	public static void notifyStageCompleted(int idSupervisor) {
		try {
			String to = models.UserDAO.getInstance().getGenericUserMailById(idSupervisor);
			String subject = "[STAGE FINISH]";
			String message = "The stage finished";
			controllers.utils.SendEmail.sendEmail(to, from, subject, message, host);

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during sending an email", e);

		}
	}

}
