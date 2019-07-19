package app.web.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.Path;
import app.entities.User;
import app.exception.AppException;
import app.manager.DBManager;
import app.web.command.logCommand.LoginCommand;
import org.apache.log4j.Logger;


//Command for blocking card by user or admin
public class BlockCardCommand extends Command {

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        DBManager manager = DBManager.getInstance();
        HttpSession session = request.getSession();

        LOG.debug("Command starts");
        int id_account = Integer.parseInt(request.getParameter("id_account"));
        String status =request.getParameter("status");
        User user = (User) session.getAttribute("user");
        LOG.trace("Request parameter: id_account --> " + id_account);
        LOG.trace("Request parameter: status --> " + status);
        LOG.trace("Request parameter: user --> " + user);

        //get user type
        int type = user.getId_type();

        //blocking
        boolean block = manager.blockCard(id_account,status,type);

        //check on error
        String forward = Path.PAGE_ERROR_PAGE;
            if (block) {
                forward = Path.COMMAND_LIST_CARDS;
            }

        return forward;
    }
}