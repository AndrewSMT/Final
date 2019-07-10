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


//Login command
public class BlockCardCommand extends Command {

    //private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        int id_account = Integer.parseInt(request.getParameter("id_account"));
        String status =request.getParameter("status");
        // obtain login and password from a request
        DBManager manager = DBManager.getInstance();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int type = user.getId_type();
        boolean block = manager.blockCard(id_account,status,type);

        String forward = Path.PAGE_ERROR_PAGE;
            if (block) {
                forward = Path.COMMAND_LIST_CARDS;
            }
        return forward;
    }
}