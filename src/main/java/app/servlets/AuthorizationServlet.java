package app.servlets;

import app.connect.Database;
import app.entities.User;
import app.exception.AppException;
import app.manager.DBManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import app.exception.AppException;

@WebServlet("/authorization/*")
public class AuthorizationServlet extends HttpServlet {

    private static final long serialVersionUID = -3071536593627692473L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/jsp/authorization.jsp");
        requestDispatcher.forward(req, resp);
    }
}