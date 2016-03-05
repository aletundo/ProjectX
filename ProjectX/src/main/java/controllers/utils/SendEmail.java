package controllers.utils;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
/*import javax.mail.Transport;*/
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	private static final Logger LOGGER = Logger.getLogger(SendEmail.class.getName());
	
    private SendEmail() {

    }

    public static void sendEmail(String to, String from, String subject, String message, String host) throws MessagingException {

    	MimeMessage msg;
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.user", "myuser");
		properties.put("mail.password", "mypw");

        Session session = Session.getInstance(properties);
        
		try{		
			msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSentDate(new Date());
			msg.setSubject(subject);
			msg.setText(message);
		/*	Transport.send(msg); */
		} catch (MessagingException e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during sending mail", e);
		}

    }

}
