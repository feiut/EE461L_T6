package guestbook;

import java.io.IOException;
import javax.servlet.http.*;
//second import
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

public class GuestbookServlet extends HttpServlet {
	
	static {
		ObjectifyService.register(Greeting.class);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	         //HW1
	        /********
			throws IOException{
				resp.setContentType("text/plain" );
				resp.getWriter().println("Hello, World!");
	   		}
	        *******/
    		//HW2
   ///********
	throws IOException{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
//		static {
//			ObjectifyService.register(Greeting.class);
//		}
		
			if(user != null) {
				resp.setContentType("text/plain");
				resp.getWriter().println("Hello," + user.getNickname());
			} else {
				resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
			}
	}
   //*******/	
}
