// https://exalted-density-230605.appspot.com/ofyguestbook.jsp

package guestbook;


import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.appengine.api.users.User;

import com.google.appengine.api.users.UserService;

import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

import java.io.IOException;

import java.util.Date;

 

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

 

public class OfySignGuestbookServlet extends HttpServlet {
	

static {

        ObjectifyService.register(Greeting.class);

    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)

                throws IOException {

    	
    	
        UserService userService = UserServiceFactory.getUserService();

        User user = userService.getCurrentUser();

        String guestbookName = req.getParameter("guestbookName");

        String title = req.getParameter("title");

        String content = req.getParameter("content");

        Greeting th = new Greeting(user, title, content, guestbookName);

        ofy().save().entity(th).now();  

 

        resp.sendRedirect("/ofyguestbook.jsp?guestbookName=" + guestbookName);

    }

}