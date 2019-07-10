package app.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.Path;
import app.exception.AppException;
import app.manager.DBManager;
import app.web.command.Command;
import app.web.command.logCommand.LoginCommand;
import org.apache.log4j.Logger;


public class UnblockRequsetCommand extends Command {


    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        int id_account = Integer.parseInt(request.getParameter("id_account"));
        // obtain login and password from a request
        DBManager manager = DBManager.getInstance();
        boolean req = manager.requestUnblockCard(id_account);
        String forward = Path.PAGE_ERROR_PAGE;
        if (req) {
            forward = Path.COMMAND_LIST_CARDS;
        }
        return forward;
    }
}

