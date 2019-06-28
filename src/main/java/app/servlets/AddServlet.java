package app.servlets;

import app.connect.Connector;
import app.entities.User;
import app.model.Model;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/add.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection con = Connector.initializeDatabase();
            System.out.println(con);
            PreparedStatement st = con
                    .prepareStatement("INSERT INTO demoprj.demo  ( id,login,password) VALUES (?,?,?)");
            st.setString(1,"0");
            st.setString(2, request.getParameter("name"));
            st.setString(3, request.getParameter("pass"));
            st.executeUpdate();
            st.close();
            con.close();
            PrintWriter out = response.getWriter();
            out.println("<html><body><b>Successfully Inserted"
                    + "</b></body></html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String name = request.getParameter("name");
        String password = request.getParameter("pass");
        User user = new User(name, password);
        Model model = Model.getInstance();
        model.add(user);

        request.setAttribute("userName", name);
        doGet(request, response);
    }
}
