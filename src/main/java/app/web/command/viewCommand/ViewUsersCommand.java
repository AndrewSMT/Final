package app.web.command.viewCommand;

import app.Path;
import app.been.ViewCard;
import app.been.ViewUsers;
import app.entities.User;
import app.exception.AppException;
import app.exception.DBException;
import app.exception.Messages;
import app.manager.DBManager;
import app.web.command.Command;
import app.web.command.logCommand.RegistrationCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ViewUsersCommand extends Command {

    private static final Logger LOG = Logger.getLogger(ViewUsersCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        DBManager manager = DBManager.getInstance();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        LOG.trace("Request attribute: user --> " + user);

        List<ViewUsers> viewUsers = manager.getAllUser();
        LOG.trace("Found in DB: viewUsers --> " + viewUsers);

        String forward = Path.PAGE_ERROR_PAGE;

        if (user.getId_type() == 1) {
            forward = Path.PAGE_LIST_USERS;
        }if (user.getId_type() == 2){
            throw new AppException("Sorry you have no right to get on this page");
        }

        request.setAttribute("viewUsers", viewUsers);
        LOG.trace("Set the request attribute: viewUsers --> " + viewUsers);

        LOG.debug("Commands finished");
        return forward;
    }

}