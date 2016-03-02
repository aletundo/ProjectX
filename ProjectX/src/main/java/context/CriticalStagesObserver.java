package context;

import java.util.logging.Level;
import java.util.logging.Logger;

import controllers.utils.SchedulerEventsThread;

import models.UserDAO;

public class CriticalStagesObserver implements Observer {
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

    public static void update(int idSupervisor, int idProject) {
        try {

            UserDAO.getInstance().getGenericUserMailById(idSupervisor);

            final String subject = "[PROJECT DELAY]";
            final String message = "A critical stage should have ended but it has not yet done it and now the entire project is delaying.";

//            String toAddressProj = models.UserDAO.getInstance()
//                    .getProjectManagerMailByIdStage(stageCritical.getIdStage());

            controllers.utils.SendEmail.sendEmail(host, port, userName, pw, toAddress, subject, message);
//            controllers.utils.SendEmail.sendEmail(host, port, userName, pw, toAddressProj, subject, message);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during manding an email", e);

        }

    }

}
