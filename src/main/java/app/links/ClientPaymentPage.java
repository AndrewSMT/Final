package app.links;

import app.Path;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/clientpayment/*")
public class ClientPaymentPage extends HttpServlet{

   // private static final long serialVersionUID = -3071536593627692473L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(Path.PAGE_LIST_PAYMENTS);
        requestDispatcher.forward(req, resp);
    }
}
