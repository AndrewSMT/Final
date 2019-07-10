package app.web.command.logCommand;

import app.Path;
import app.entities.User;
import app.exception.AppException;
import app.manager.DBManager;
import app.web.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RegistrationCommand extends Command {
    //    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
            LOG.debug("Command starts");
            DBManager manager = DBManager.getInstance();
            String login = request.getParameter("login");
            LOG.trace("Request parameter: login --> " + login);
            String pass = request.getParameter("pass");
            String first_name = request.getParameter("first_name");
            String last_name = request.getParameter("last_name");
            String phone = request.getParameter("phone");

            if (login == null || pass == null || first_name == null ||
                    last_name == null || phone == null ||
                    login.isEmpty() || first_name.isEmpty() ||
                    last_name.isEmpty() || phone.isEmpty() ||pass.isEmpty()) {
                throw new AppException("All fields must be fill");
            }
            //check exist user
            User userr = manager.findUserByLogin(login);
            LOG.trace("Found in DB: user --> " + userr);

            if (userr != null ) {
                throw new AppException("Account with this login already exists");
            }
            User user = new User();
            user.setLogin(login);
            user.setPassword(pass);
            user.setPhone(phone);
            user.setFirstName(first_name);
            user.setLastName(last_name);

             manager.insertNewUser(user);

            String forward = Path.PAGE_ERROR_PAGE;

            if (user != null) {
                forward = Path.PAGE_MAIN;
            }
            LOG.debug("Command finished");
            return forward;
    }
}
