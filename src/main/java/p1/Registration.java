package p1;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

 
/**
 * Servlet implementation class Registration
 */
@WebServlet("/addForm")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Registration() {
    	super();
    }
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	   System.out.println("Request Parameters:");
           request.getParameterMap().forEach((key, value) -> {
               System.out.println(key + ": " + String.join(", ", value));
           });
    	String name = request.getParameter("name");
    	String email = request.getParameter("email");
    	String mobile = request.getParameter("mobile");
    	 System.out.println("Name: " + name);    // Should print name
         System.out.println("Email: " + email);  // Should print email
         System.out.println("Mobile: " + mobile); // Should print mobile
         
         response.setContentType("text/html");
         PrintWriter out = response.getWriter();

         try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","php@java");
         Statement stmt = connection.createStatement();
         stmt.executeUpdate("insert into registration values('"+name+"','"+email+"','"+mobile+"')");
         
         System.out.println("data saved successfully");
         
         
         ResultSet rs = stmt.executeQuery("SELECT * FROM registration");

         // Write HTML header
         out.println("<html><head><title>Employee List</title></head><body>");
         out.println("<h1>Employee List</h1>");
         out.println("<table border='1'><tr><th>Name</th><th>Email</th><th>Mobile</th></tr>");

         // Iterate over the result set and display data in a table
         while (rs.next()) {
             out.println("<tr>");
             out.println("<td>" + rs.getString("name") + "</td>");
             out.println("<td>" + rs.getString("email") + "</td>");
             out.println("<td>" + rs.getString("mobile") + "</td>");

             out.println("</tr>");
         }

         out.println("</table>");
         out.println("</body></html>");
         connection.close();
        		 
         }
         catch(Exception e) {
        	 e.printStackTrace();
         }
         

	}

}
