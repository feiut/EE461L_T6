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
   <div class="ui container"> 
   	<div class="ui segment">
<p>Hello, ${fn:escapeXml(user.nickname)}! You can post.</p>
	
    <form action="/ofysign" method="post">
          <p>Enter Title:</p>
      <div><textarea name="title" rows="1" cols="60"></textarea></div>
      <p>Enter Content:</p>
      <div><textarea name="content" rows="3" cols="60"></textarea></div>
<p style="margin-bottom:10px"> </p>
      <div>
      		<input type="submit" class="ui inverted button blue left floated" value="Make Post" />
     </div>
     <input type="hidden" name="guestbookName" value="${fn:escapeXml(guestbookName)}"/>
    </form>
    
   <form action="/ofyguestbook.jsp"  method="post">
     <div>
      		<input type="submit" class="ui button right floated" value="Cancel Post" />
     </div>
    </form>
        <p style="margin-bottom:50px"> </p>
        
<!-- put your blog insert statement here-->
<%
    } else {
%>
<p>Hello, Visitor! You can sign in to post.)</p>
<%
    }
%>

    </div>   
     </div>   
  </body>
</html>