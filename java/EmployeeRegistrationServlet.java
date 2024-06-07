import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EmployeeRegistrationServlet")
public class EmployeeRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String id = request.getParameter("id");
        String desig = request.getParameter("desig");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        int age = Integer.parseInt(request.getParameter("age"));
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
            out.println("<body><div  style='position: fixed;'><img src='images/logo.jpg' alt='logo' onclick='redirectToIndex()'>");
            out.println("<div style='margin:150px 50px;'><h2>Passwords do not match</h2>");
            out.println("<script>");
            out.println("function redirectToIndex() {");
            out.println("   window.location.href = 'index.html';");
            out.println("}");
            out.println("</script>");
            out.println("</div></body></html>");
            return;
        }


        try {
            // Update the JDBC connection details
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/bus";
            String username = "root";
            String dbPassword = "";

            Connection connection = DriverManager.getConnection(url, username, dbPassword);

            String insertQuery = "INSERT INTO employee (first_name, last_name,id,designation, phone_number, email, age, password) VALUES (? ,?, ?, ?, ?, ?,?,?)";
            
            	PreparedStatement preparedStatement = connection.prepareStatement(insertQuery); 
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, id);
                preparedStatement.setString(4, desig);
                preparedStatement.setString(5, phoneNumber);
                preparedStatement.setString(6, email);
                preparedStatement.setInt(7, age);
                preparedStatement.setString(8, password);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) 
                {
                	response.setContentType("text/html");
                	PrintWriter out = response.getWriter();
                	out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
                	out.println("<body><div  style='position: fixed;'><img src='images/logo.jpg' alt='logo' onclick='redirectToIndex()'>");
                	out.println("<div style='margin:150px 50px 0px;'><h2>Registered Successfully.</h2></div>");
                	out.println("<button onclick='redirectToLogin()' class='login-button' style='margin:50px 50px;'>Login Now</button>");
                	out.println("<script>");
                	out.println("function redirectToIndex() {");
                	out.println("   window.location.href = 'index.html';");
                	out.println("}");
                	out.println("function redirectToLogin() {");
                	out.println("   window.location.href = 'emplogin.html';");
                	out.println("}");
                	out.println("</script>");
                	out.println("</div></body></html>");
                } else {
                	response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.println("<html><head><link rel='stylesheet' href='styles.css'></head>");
                    out.println("<body><div  style='position: fixed;'><img src='images/logo.jpg' alt='logo' onclick='redirectToIndex()'>");
                    out.println("<div style='margin:150px 50px;'><h2>Registration Failed</h2></div>");
                    out.println("<script>");
                    out.println("function redirectToIndex() {");
                    out.println("   window.location.href = 'index.html';");
                    out.println("}");
                    out.println("</script>");
                    out.println("</div></body></html>");
                }
        }
             catch (SQLException e) {
                e.printStackTrace();
            }

         catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
