package guestbook;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

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

			String userCollection = req.getParameter("userCollection");
			//String userCollection = "userCollection";
			//String UserCollection = "UserCollection";
		 
	        UserService userService = UserServiceFactory.getUserService();
	        User user = userService.getCurrentUser();
	        
	        if(user == null) {
	        	resp.sendRedirect("/subscribeFail.jsp");
	        	return;
	        }
	        
	        //String userCollection = req.getParameter("userCollection");
	        //System.out.println(userCollection);
	        
	        UserMail be = new UserMail(user, user.getEmail());
	        // Save Object/Entity to datastore
	        ofy().save().entity(be).now(); 
	        
	        resp.sendRedirect("/ofyguestbook.jsp");
	        
	    }
	
}