
//package guestbook;
//
//import guestbook.UserMail;
//import com.googlecode.objectify.*;
//import com.googlecode.objectify.ObjectifyService;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.List;
//import java.util.Properties;
//import java.util.logging.Logger;
//
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.AddressException;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.*;
//
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.google.storage.onestore.v3.OnestoreEntity.User;
//
//@SuppressWarnings("serial")
//public class CronServlet extends HttpServlet {
/////???	   ObjectifyService.register(UserMail.class);
//			  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//				  
////				    ObjectifyService.register(UserMail.class);
////					List<UserMail> usermails = ObjectifyService.ofy().load().type(UserMail.class).list(); 
//									  
////				    for(UserMail usermail: usermails) {
//						Properties props = new Properties();
//						Session session = Session.getDefaultInstance(props, null);
//					    try {
//
//					      Message msg = new MimeMessage(session);
//					      msg.setFrom(new InternetAddress("hf2014v@gmail.com", "TestMan!"));
//					      msg.addRecipient(Message.RecipientType.TO,
//					                       new InternetAddress(usermail.getEmail(), "Mr. User"));
//					      msg.setSubject("Your Example.com account has been activated");
//					      msg.setText("This is a test");
//					      Transport.send(msg);
//					    } catch (AddressException e) {
//					      // ...
//					    } catch (MessagingException e) {
//					      // ...
//					    } catch (UnsupportedEncodingException e) {
//					      // ...
//					    }
////				    }
////			Properties props = new Properties();
////			Session session = Session.getDefaultInstance(props, null);
////			try {
////				Message msg = new MimeMessage(session);
////				msg.setFrom(new InternetAddress("lcherry0521@gmail.com", "MyBlog"));
////				msg.addRecipient(Message.RecipientType.TO,
////				                       new InternetAddress("hf0114z@163.com", "Mr. User"));
////				msg.setSubject("Your hf0114z@163.com account has been activated");
////				msg.setText("This is a test");
////				Transport.send(msg);
////			} catch (AddressException e) {
////				      // ...
////			} catch (MessagingException e) {
////				      // ...
////			} catch (UnsupportedEncodingException e) {
////				      // ...
////			}
////				    // [END simple_example]
////			
////		catch (Exception ex) {
////			_logger.info("Cron Job has error");
////			//Log any exceptions in your Cron Job
////		}
//}
//
//}

package guestbook;

import guestbook.UserMail;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class CronServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(CronServlet.class.getName());

			  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
					
				    Date date = new Date();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.DAY_OF_MONTH, -1);
					calendar.set(Calendar.HOUR_OF_DAY, 17);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND,0);
					Date dateBefore = calendar.getTime();

				    ObjectifyService.register(UserMail.class);
					List<UserMail> usermails = ObjectifyService.ofy().load().type(UserMail.class).list(); 
					
					ObjectifyService.register(Greeting.class);
					List<Greeting> greetings = ObjectifyService.ofy().load().type(Greeting.class).list();   
					Collections.sort(greetings); 
					
					List<Greeting> SelectedBlog = (List<Greeting>) new ArrayList<Greeting>();
					
					for(Greeting blog: greetings){
						if(blog.date.after(dateBefore)) {
							SelectedBlog.add(blog);
						}
					}
					
					int number = SelectedBlog.size();
					if(number == 0) {
						logger.info("No new post");
						return;
					}
					StringBuilder mailContent = new StringBuilder();
					for(Greeting blog: SelectedBlog){
						mailContent.append(blog.user.getNickname()+
								"\n	Posted on: "+blog.date.toString()+
								"\n	Title: "+blog.title +
								"\n	Content: "+blog.content+
								"\n\n");
					}
	
				   for(UserMail usermail: usermails) {
						Properties props = new Properties();
						Session session = Session.getDefaultInstance(props, null);

				    try {
				    	logger.info("Cron job has been executed");
				      Message msg = new MimeMessage(session);
				      msg.setFrom(new InternetAddress("hf0114z@gmail.com", "LHBlog Team"));
				      msg.addRecipient(Message.RecipientType.TO,
				                       new InternetAddress(usermail.getEmail(), usermail.getNickname()));
				      msg.setSubject("LHBlog Daily Digest");
				      msg.setText("Hi, "+usermail.getNickname()+"\n\n\n" +"Here are the new posts yesterday, please enjoy!\n"+ 
				    		  mailContent.toString()+"Have a nice day!\n"+"LHBlog Team");
				      Transport.send(msg);
				    } catch (AddressException e) {
						logger.info(e.getMessage());
				    } catch (MessagingException e) {
						logger.info(e.getMessage());
				    } catch (UnsupportedEncodingException e) {
						logger.info(e.getMessage());
				    }		
			  }
			  }
	  }