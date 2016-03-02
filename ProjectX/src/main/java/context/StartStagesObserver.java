package context;

import java.util.logging.Level;
import java.util.logging.Logger;

import controllers.utils.SchedulerEventsThread;

public class StartStagesObserver {

    private static final Logger LOGGER = Logger.getLogger(SchedulerEventsThread.class.getName());

    static String host = "localhost";
    static String port = "8080"; /* TODO check port */
    final static String pw = "bla"; /* TODO check password */
    static String toAddress;
    final static String userName = "";

    // private Subject subj;
    //
    // public CriticalStagesObserver(Subject subj) {
    // this.subj = subj;
    // }

    public static void update(int idSupervisor) {
        try {

            toAddress = models.UserDAO.getInstance().getGenericUserMailById(idSupervisor);
            final String subject = "[STAGE START]";
            final String message = "The stage started";
            controllers.utils.SendEmail.sendEmail(host, port, userName, pw, toAddress, subject, message);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during manding an email", e);

        }

    }

}
