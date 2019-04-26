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
<%@ page import="guestbook.UserMail" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.googlecode.objectify.Objectify" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<html>
  <head>
    <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css" />
  	<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>
  
    <body>
	<div class="ui container">
		<h1 class="ui header center aligned" >
		  <div class="content">
			UT BLOG
		  </div>
		</h1>
	
<h3>
<div class="ui segment">
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

	 

<p style="margin:10px; text-align:center">
<img style="margin-bottom:10px" class="ui centered image" src="https://source.unsplash.com/ZLGFy3dNWfo/800x100">
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>" class="ui button right floated">Sign Out</a>
Hello, ${fn:escapeXml(user.nickname)}!
<a href="/ofyguestbook.jsp" class="ui button blue left floated">Main Page</a>
</p>
<%
    } else {
%>
<p style="margin:10px; text-align:center">
<img style="margin-bottom:10px" class="ui centered image" src="https://source.unsplash.com/ZLGFy3dNWfo/800x100">
<a href="/ofyguestbook.jsp" class="ui button blue left floated">Main Page</a>
Hello! Sign in to post blog. You can still view all blogs without signing in.
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>" class="ui button right floated"> Sign In</a>
</p>
<%
    }
%>
</div></h3>
 
<%  ObjectifyService.register(UserMail.class);
	ObjectifyService.register(Greeting.class);
	List<Greeting> greetings = ObjectifyService.ofy().load().type(Greeting.class).list();   
	Collections.sort(greetings); 
    if (greetings.isEmpty()) {
    %>	
    	<p>No Blog now, post one?</p>
    <% 	
    } else {
       %>
    	<div class="ui segment">
    	<a href="/Postblog.jsp" class="ui inverted button blue left">Post blog</a>
    	<div class = "ui divider"></div>
    	<%
        TimeZone tz = TimeZone.getTimeZone("America/Mexico City");
        DateFormat formatter = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss");
       
       for (Greeting greeting : greetings) {

            pageContext.setAttribute("greeting_content",
                                     greeting.getContent());
            pageContext.setAttribute("greeting_title",
                    greeting.getTitle());
            pageContext.setAttribute("greeting_date",
                    formatter.format(greeting.getDate()));
        
            if (greeting.getUser() == null) {

            } else {
                pageContext.setAttribute("greeting_user",
                                         greeting.getUser());
                %>
                <p><b>${fn:escapeXml(greeting_user.nickname)}</b> post at : ${fn:escapeXml(greeting_date)}</p>
                <%
            }
            %>
            
       <div class="ui items">
		  <div class="item">
            <h2 class="ui header olive" ><b>Blog Title</b>: ${fn:escapeXml(greeting_title)}</h2>	
	      </div>
		  <div class="item">
            <h4 class="ui header dark " ><b>Blog Content</b>: ${fn:escapeXml(greeting_content)}</h4>
		  </div>
		<div class="ui divider"></div>	
      </div> 
 
            <%
        }
    }
%>
    <form action="/subscribe" method="post">
      <div><input type="submit" class="ui inverted button blue left floated" value="subscribe" /></div>
      <input type="hidden" name="guestbookName" value="${fn:escapeXml(guestbookName)}"/>
    </form>
 	
     <form action="/unsubscribe" method="get">
     <div><input type="submit" class="ui button right floated" value="unsubscribe" /></div>
      <input type="hidden" name="guestbookName" value="${fn:escapeXml(guestbookName)}"/>
    </form> 
    <p style="margin-bottom:60px"> </p>
    </div>
   </div>	
  </body>
</html>