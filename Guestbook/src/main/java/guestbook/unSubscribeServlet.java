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

public class unSubscribeServlet extends HttpServlet{

	 public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	        
			ObjectifyService.register(UserMail.class);

//			String userCollection = req.getParameter("userCollection");

	        UserService userService = UserServiceFactory.getUserService();
	        User user = userService.getCurrentUser();
	        
	        if(user == null) {
	        	resp.sendRedirect("/subscribeFail.jsp");
	        	return;
	        }
	        
	        List<UserMail> usermails = ObjectifyService.ofy().load().type(UserMail.class).list(); 
	        for(UserMail usermail: usermails) {
	        	if(usermail.getId().equals(user.getUserId())) {
	        		ofy().delete().entity(usermail).now();
	    	        resp.sendRedirect("/unsub.jsp");
	    	        return;
	        	}
	        }
	        resp.sendRedirect("/subscribeFail.jsp");
	
	    }
	
}