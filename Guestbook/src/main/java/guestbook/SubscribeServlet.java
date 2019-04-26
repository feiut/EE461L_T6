package guestbook;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

public class SubscribeServlet extends HttpServlet{

	 public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	        


			ObjectifyService.register(UserMail.class);
			List<UserMail> usermails = ObjectifyService.ofy().load().type(UserMail.class).list();
			String userCollection = req.getParameter("userCollection");	
		 
	        UserService userService = UserServiceFactory.getUserService();
	        User user = userService.getCurrentUser();
	        
	        if(user == null) {
	        	resp.sendRedirect("/subscribeFail.jsp");
	        	return;
	        }
	        
	        //String userCollection = req.getParameter("userCollection");
	        //System.out.println(userCollection);
	        for(UserMail usermail: usermails) {
	        	if(usermail.getId().equals(user.getUserId())){
	    	        resp.sendRedirect("/subscribeSuccess.jsp");
	        		return;
	        	}
	        }
	        UserMail th = new UserMail(user, user.getEmail());
	        // Save Object/Entity to datastore
	        ofy().save().entity(th).now(); 
	        
	        resp.sendRedirect("/subscribeSuccess.jsp");
	        
	    }
	
}