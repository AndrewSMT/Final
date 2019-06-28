package app.servlets;

import app.connect.Connector;
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
import java.sql.ResultSet;
import java.util.List;

public class ListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection con = Connector.initializeDatabase();
            System.out.println(con);
            PrintWriter out = resp.getWriter();
            PreparedStatement stmt = con.prepareStatement("select * from demo");
            ResultSet rs = stmt.executeQuery();
            String temp = ( rs.getInt(1) + " " + rs.getString(2));
            req.setAttribute("temp", temp);
            while (rs.next()) {
                out.println( rs.getInt(1) + " " + rs.getString(2));
            }
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Model model = Model.getInstance();
        List names = model.list();
        req.setAttribute("userNames", names);



        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/list.jsp");
        requestDispatcher.forward(req, resp);
    }
}
