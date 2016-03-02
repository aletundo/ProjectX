package context;

import java.util.logging.Level;
import java.util.logging.Logger;

import controllers.utils.SchedulerEventsThread;

import models.UserDAO;

public class CriticalStagesObserver implements Observer {
    private static final Logger LOGGER = Logger.getLogger(SchedulerEventsThread.class.getName());

    static String host = "localhost";
    static String port = "0";
    static String toAddress;
    final static String pw = null; 
    final static String userName = "";
    
    private CriticalStagesObserver(){
        
    }
    
    public static void update(int idSupervisor, String projectManagerMail) {
        try {

            UserDAO.getInstance().getGenericUserMailById(idSupervisor);

            final String subject = "[PROJECT DELAY]";
            final String message = "A critical stage should have ended but it has not yet done it and now the entire project is delaying.";

            controllers.utils.SendEmail.sendEmail(host, port, userName, pw, toAddress, subject, message);
            controllers.utils.SendEmail.sendEmail(host, port, userName, pw, projectManagerMail, subject, message);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during manding an email", e);

        }

    }

}
