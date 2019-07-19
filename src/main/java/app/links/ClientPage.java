package app.links;

//Link on page: client menu

import app.Path;
import app.entities.TypeUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/clientpage/*")
public class ClientPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher;
        HttpSession session = req.getSession();
        TypeUser role = (TypeUser) session.getAttribute("userRole");

        if(role == TypeUser.CLIENT) {
             requestDispatcher = req.getRequestDispatcher(Path.COMMAND_CLIENT_MENU);
            requestDispatcher.forward(req, resp);
        }else if (role == TypeUser.ADMIN ){
             requestDispatcher = req.getRequestDispatcher(Path.COMMAND_ADMIN_MENU);
            requestDispatcher.forward(req, resp);
        }
    }
}