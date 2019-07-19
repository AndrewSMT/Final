package app.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.Path;
import app.exception.AppException;
import app.manager.DBManager;
import app.web.command.Command;
import app.web.command.logCommand.LoginCommand;
import org.apache.log4j.Logger;

//Command on send unblock request
public class UnblockRequestCommand extends Command {

    private static final Logger LOG = Logger.getLogger(UnblockRequestCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        DBManager manager = DBManager.getInstance();
        int id_account = Integer.parseInt(request.getParameter("id_account"));

        LOG.trace("Request parameter: id_account --> " + id_account);

        //do unblock
        boolean req = manager.requestUnblockCard(id_account);

        //check error
        String forward = Path.PAGE_ERROR_PAGE;
        if (req) {
            forward = Path.COMMAND_LIST_CARDS;
        }
        LOG.debug("Command finish");

        return forward;
    }
}

