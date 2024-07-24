import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SubmitFormServlet")
public class SubmitFormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        // JDBC Connection parameters
        String jdbcURL = "jdbc:mysql://localhost:3306/mydatabase";
        String dbUser = "your_username";
        String dbPassword = "your_password";

        // JDBC variables
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // 1. Load and register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // 2. Connect to the database
            connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            // 3. Create SQL statement
            String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);

            // 4. Set parameters for SQL statement
            statement.setString(1, name);
             statement.setString(2, email);

            // 5. Execute SQL statement
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                response.getWriter().println("Data inserted successfully!");
            } else {
                response.getWriter().println("Failed to insert data.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // 6. Close JDBC objects
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
