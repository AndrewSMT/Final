package app.servlets;


import app.connect.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/login/*")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String loginn = request.getParameter("login");
        String pass = request.getParameter("pass");
        try {

            String SQL_FIND_USERS = "SELECT * FROM demoprj.demo WHERE login = '"+loginn+"' ";
            Connection con = Database.initializeDatabase();
            Statement st;
            ResultSet rs;
            st = con.createStatement();
            rs = st.executeQuery(SQL_FIND_USERS);

            if(rs.absolute(1)) {
                response.sendRedirect("index3.jsp");
            }else{
                response.sendRedirect("index.jsp");
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
