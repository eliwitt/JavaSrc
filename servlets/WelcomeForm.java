import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class WelcomeForm extends HttpServlet
{
     public void doGet(HttpServletRequest request,
          HttpServletResponse response) throws ServletException, IOException
     {

	response.setContentType("text/html");
	PrintWriter out = response.getWriter();

	out.print("<html>");
	out.print("<head><title>Steve's Servlets</title>");
	out.print("</head>");
	out.print("<body>");
	out.print("<h1>Welcome</h1>");
	out.print("<hr>");
	out.print("<form action=/web/servlet/ProcessForm method=get>");
	out.print("<p align=left>Enter your name:<INPUT NAME=user></p>");
	out.print("<input type=submit>");
	out.print("</form>");
	out.print("</body>");
	out.print("</html>");


     }

     public void doPost(HttpServletRequest request,
     	HttpServletResponse response)  throws ServletException, IOException
     {
     	doGet(request, response);
     }

}