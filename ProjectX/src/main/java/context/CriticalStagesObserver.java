package context;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import controllers.utils.SchedulerEventsThread;
import controllers.utils.UtilityFunctions;

import models.StageBean;

public class CriticalStagesObserver implements Observer {
	private static final Logger LOGGER = Logger.getLogger(SchedulerEventsThread.class.getName());

	private Subject subj;

	public CriticalStagesObserver(Subject subj) {
		this.subj = subj;
	}

	@Override
	public void update() {
		List<StageBean> stagesCritical = subj.getStagesCritical();

		if (stagesCritical.isEmpty()) {
			return;
		}

		try {
			/*
			 * UserDAO.getInstance().getGenericUserMailById(stageCritical.
			 * getIdSupervisor());
			 * 
			 * final String userName = stageCritical.getSupervisorFullname();
			 * final String subject = "[PROJECT DELAY]"; final String message =
			 * "A critical stage should have ended but it has not yet done it and now the entire project in delaying."
			 * ;
			 * 
			 * String toAddressProj = models.UserDAO.getInstance()
			 * .getProjectManagerMailByIdStage(stageCritical.getIdStage());
			 * 
			 * controllers.utils.SendEmail.sendEmail(host, port, userName, pw,
			 * toAddress, subject, message);
			 * controllers.utils.SendEmail.sendEmail(host, port, userName, pw,
			 * toAddressProj, subject, message);
			 */

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during manding an email", e);

		}

	}

}
