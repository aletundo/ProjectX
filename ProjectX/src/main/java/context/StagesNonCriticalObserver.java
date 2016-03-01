package context;

import java.util.List;

import javax.mail.MessagingException;
import controllers.utils.UtilityFunctions;

import models.StageBean;

public class StagesNonCriticalObserver implements Observer {

	private Subject subj;

	public StagesNonCriticalObserver(Subject subj) {
		this.subj = subj;
	}

	@Override
	public void update() {
		List<StageBean> stagesNonCritical = subj.getStagesNonCritical();

		if (stagesNonCritical.isEmpty()) {
			return;
		}

		try {
			/*
			 * toAddress =
			 * models.UserDAO.getInstance().getGenericUserMailById(stage.
			 * getIdSupervisor()); final String userName =
			 * stage.getSupervisorFullname(); final String subject =
			 * "[STAGE DELAY]"; final String message =
			 * "The stage should have ended but it has not yet done it.";
			 * controllers.utils.SendEmail.sendEmail(host, port, userName, pw,
			 * toAddress, subject, message);
			 */

		} catch (Exception e) {

		}

	}
}