<!doctype html public "-//w3c/dtd HTML 4.0//en">
<html>
<head><title>Calendar</title></head>

<%@ page 
        info="Calendar JSP example"
        contentType="text/html"
        import="Steve.CalendarBean"
        import="java.util.*"
%>

<jsp:useBean id="calendar" 
	scope="page"
	class="Steve.CalendarBean"
/>


<h1>Today is <% out.println("\"" +calendar.getTodayString() +"\""); %></h1>
<p>
<center>
<% out.println(calendar.getHtmlMonth()); %>
</center>



<p>
<hr>
<font face="Helvetica">
<p>This page executed by 
<%= application.getServerInfo() %>.<br>
Copyright 1999-2000 &copy; BEA Systems, Inc. 
All Rights Reserved. 
</body>
</html>




