package guestbook;

import com.googlecode.objectify.*;

import static com.googlecode.objectify.ObjectifyService.ofy;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
 
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
        String nickname = user.getNickname();
        //Thing th = ofy().load().key(thingKey).get();
 
        // We have one entity group per Guestbook with all Greetings residing
        // in the same entity group as the Guestbook to which they belong.
        // This lets us run a transactional ancestor query to retrieve all
        // Greetings for a given Guestbook.  However, the write rate to each
        // Guestbook should be limited to ~1/second.
        String guestbookName = req.getParameter("guestbookName");
        Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
        String content = req.getParameter("content");
        String title = req.getParameter("title");
        //Date date = new Date();
        /*
        Entity greeting = new Entity("Greeting", guestbookKey);
        greeting.setProperty("user", user);
        greeting.setProperty("date", date);
        greeting.setProperty("content", content);
        */
 
        //DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        //datastore.put(greeting);
//        ObjectifyService.register(Greeting.class);
//        List<Greeting> greetings = ObjectifyService.ofy().load().type(Greeting.class).list();   
//        Collections.sort(greetings); 
        

        
        Greeting greeting = new Greeting(user, nickname,content, title, guestbookName);
//        greeting.("user", user);
//        greeting.setProperty("date", date);
//        greeting.setProperty("content", content);
        
        ofy().save().entity(greeting).now();
        resp.sendRedirect("/ofyguestbook.jsp?guestbookName=" + guestbookName);
        
        

        //Query query = new Query("Greeting", guestbookKey).addSort("user", Query.SortDirection.DESCENDING).addSort("date", Query.SortDirection.DESCENDING);
        //Query query = new Query("Greeting", guestbookKey).addSort("date", Query.SortDirection.DESCENDING);
        //List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
        /*
     // Simple key fetch, always asynchronous
        Ref<Thing> th = ofy().load().key(thingKey);
         
        // Usually you don't need async, so call through the Ref<?>
        Thing th =  ofy().load().key(thingKey).get();         // return null if not found
        Thing th =  ofy().load().key(thingKey).safeGet();     // throws NotFoundException
         
        Ref<Thing> th = ofy().load().entity(entity);  // Equivalent to ofy().load().key(get_key_of(entity));
        Ref<Thing> th = ofy().load().value(rawKey);   // Accepts anything key-like; Key, Key<?>, Ref<?>, etc.
         
        // Multi get is async, hides asynchrony behind Map interface
        Map<Key<Thing>, Thing> ths = ofy().load().keys(thingKey1, thingKey2, thingKey3);        
        Map<Key<Thing>, Thing> ths = ofy().load().keys(iterableOfKeys);
        // There are also entities() and values() methods
         
        // Fetching by id - this is really just syntactic sugar for get-by-key
        Ref<Thing> th = ofy().load().type(Thing.class).id(123L);
        Thing th =      ofy().load().type(Thing.class).id(123L).get();
         
        // With a parent
        Ref<Thing> th = ofy().load().type(Thing.class).parent(par).id(123L);
        
        // Batch get, asynchrony is hidden behind Map
        Map<Key<Thing>, Thing> ths = ofy().load().type(Thing.class).ids(123L, 456L, 789L);
        
        */
    }
}
