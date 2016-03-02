package context;

import java.util.logging.Level;
import java.util.logging.Logger;

import controllers.utils.SchedulerEventsThread;

import models.UserDAO;

public class DelayStartStagesObserver implements Observer {
    private static final Logger LOGGER = Logger.getLogger(SchedulerEventsThread.class.getName());
    static String host = "localhost";
    static String port = "";
    static String toAddress;
    static final  String PW = null;
    static final  String USERNAME = "";

    private DelayStartStagesObserver() {

    }

    public static void update(int idSupervisor) {
        try {
            UserDAO.getInstance().getGenericUserMailById(idSupervisor);

            final String subject = "[PROJECT DELAY]";
            final String message = "A stage should have started but one precedent stage has not yet finished";

            controllers.utils.SendEmail.sendEmail(host, port, USERNAME, PW, toAddress, subject, message);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during manding an email", e);

        }

    }

}
