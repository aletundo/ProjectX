package context;

import java.util.logging.Level;
import java.util.logging.Logger;

import controllers.utils.SchedulerEventsThread;

public class StagesNonCriticalObserver implements Observer {
    private static final Logger LOGGER = Logger.getLogger(SchedulerEventsThread.class.getName());

    static String host = "localhost";
    static String port = "";
    static String toAddress;
    final static String Pw = null;
    final static String UserName = "";

    private StagesNonCriticalObserver() {

    }

    public static void update(int idSupervisor) {

        try {

            toAddress = models.UserDAO.getInstance().getGenericUserMailById(idSupervisor);
            final String subject = "[STAGE DELAY]";
            final String message = "The stage should have ended but it has not yet done it.";
            controllers.utils.SendEmail.sendEmail(host, port, UserName, Pw, toAddress, subject, message);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during manding an email", e);

        }

    }
}