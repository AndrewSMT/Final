package app.web.command;

import app.Path;
import app.exception.AppException;
import app.manager.DBManager;
import app.web.command.logCommand.LoginCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Payment_Command extends  Command {

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        String payment = request.getParameter("payment");

        DBManager manager = DBManager.getInstance();
        HttpSession session = request.getSession();
        String forward = Path.PAGE_ERROR_PAGE;
       if("transfer".equals(payment)){
           forward = Path.PAGE_PAYMENT_TRANSFER;
       }else if("internet".equals(payment)){
           forward = Path.PAGE_PAYMENT_INTERNET;
       }else if("public".equals(payment)){
           forward = Path.PAGE_PAYMENT_PUBLIC;
       }

        return forward;
    }
}
