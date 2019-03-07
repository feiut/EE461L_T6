<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%@ page import="guestbook.Greeting" %>
<%@ page import="java.util.Collections" %>
<%@ page import="com.googlecode.objectify.Objectify" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<html>
  <head>
  	<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>
 
  <body>
 
<%
    String guestbookName = request.getParameter("guestbookName");
    if (guestbookName == null) {
        guestbookName = "default";
    }
    pageContext.setAttribute("guestbookName", guestbookName);
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
      pageContext.setAttribute("user", user);
%>
<p>Hello, ${fn:escapeXml(user.nickname)}! (You can
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">make a post</a>.)</p>

<!-- put your blog insert statement here-->
<%
    } else {
%>
<%
    }
%>
 
<%  
	ObjectifyService.register(Greeting.class);
	List<Greeting> greetings = ObjectifyService.ofy().load().type(Greeting.class).list();   
	Collections.sort(greetings); 
    /*
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
    // Run an ancestor query to ensure we see the most up-to-date
    // view of the Greetings belonging to the selected Guestbook.
    Query query = new Query("Greeting", guestbookKey).addSort("date", Query.SortDirection.DESCENDING);
    List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
    */
    if (greetings.isEmpty()) {
        %>
        <p>Guestbook '${fn:escapeXml(guestbookName)}' has no messages.</p>
        <%
    } else {
        %>
        <p>Messages in Guestbook '${fn:escapeXml(guestbookName)}'.</p>
        <%
        for (Greeting greeting : greetings) {
            pageContext.setAttribute("greeting_content",
                                     greeting.getContent());
            pageContext.setAttribute("greeting_title",
                    greeting.getTitle());
            if (greeting.getUser() == null) {
                %>
                <p>An anonymous person wrote:</p>
                <%
            } else {
                pageContext.setAttribute("greeting_user",
                                         greeting.getUser());
                %>
                <p><b>${fn:escapeXml(greeting_user.nickname)}</b> wrote:</p>
                <%
            }
            %>
            <blockquote>${fn:escapeXml(greeting_content)}</blockquote>
            <blockquote>${fn:escapeXml(greeting_title)}</blockquote>
            <%
        }
    }
%>
 
    <form action="/ofysign" method="post">
      <div><textarea name="title" rows="1" cols="60"></textarea></div>
      <p>Enter_Title</p>
      <!-- <div><input type="submit" value="Enter Title" /></div> -->
      <div><textarea name="content" rows="3" cols="60"></textarea></div>
      <p>Enter_Content</p>
      <div><input type="submit" value="Make the post" /></div>
      <input type="hidden" name="guestbookName" value="${fn:escapeXml(guestbookName)}"/>
    </form>
     
  </body>
</html>