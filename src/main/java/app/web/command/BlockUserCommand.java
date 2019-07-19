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

//Command for blocking user by  admin
public class BlockUserCommand extends Command {

    private static final Logger LOG = Logger.getLogger(BlockUserCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        DBManager manager = DBManager.getInstance();

        LOG.debug("Command starts");
        String status = request.getParameter("status");
        int id_user = Integer.parseInt(request.getParameter("id_user"));
        LOG.trace("Request parameter: id_user --> " + id_user);
        LOG.trace("Request parameter: status --> " + status);

        //blocking
        boolean block = manager.blockUser(status,id_user);

        //check on error
        String forward = Path.PAGE_ERROR_PAGE;
        if (block) {
            forward = Path.COMMAND_LIST_USER;
        }

        return forward;
    }
}