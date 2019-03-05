
package guestbook;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.*;


@SuppressWarnings("serial")
public class CronServlet extends HttpServlet {
	private static final Logger _logger = Logger.getLogger(CronServlet.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			try {
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("hf2014v@gmail.com", "MyBlog"));
				msg.addRecipient(Message.RecipientType.TO,
				                       new InternetAddress("hf0114z@163.com", "Mr. User"));
				msg.setSubject("Your hf0114z@163.com account has been activated");
				msg.setText("This is a test");
				Transport.send(msg);
			} catch (AddressException e) {
				      // ...
			} catch (MessagingException e) {
				      // ...
			} catch (UnsupportedEncodingException e) {
				      // ...
			}
				    // [END simple_example]
		}

		catch (Exception ex) {
			_logger.info("Cron Job has error");
			//Log any exceptions in your Cron Job
		}
	}
@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	doGet(req, resp);
	}
}