package app.web.command;

import app.Path;
import app.been.ViewCard;
import app.exception.AppException;
import app.manager.DBManager;
import app.web.command.logCommand.LoginCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoPaymentCommand extends Command {
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        int from = Integer.parseInt(request.getParameter("from"));
        int to = Integer.parseInt(request.getParameter("to"));
        int id_account_service = Integer.parseInt(request.getParameter("id_account_service"));
        int howmuch = Integer.parseInt(request.getParameter("howmuch"));
        int id_payment = Integer.parseInt(request.getParameter("id_payment"));
        // obtain login and password from a request
        int id_accountTo = 0;
        DBManager manager = DBManager.getInstance();
        ViewCard fromCard = manager.getRecipientCard(from);
        ViewCard toCard = manager.getRecipientCard(to);
        int id_accountFrom = fromCard.getId_account();
        if (id_account_service !=  0){
             id_accountTo = id_account_service;
        }else {
             id_accountTo = toCard.getId_account();
        }
        int balanceFrom = manager.getBalance(id_accountFrom);
        int balanceTo = manager.getBalance(id_accountTo);
        balanceFrom-=howmuch;
        balanceTo+=howmuch;

        boolean flag;
        flag = manager.performPayment(id_accountFrom,balanceFrom);
        if (!flag) {
            return Path.PAGE_ERROR_PAGE;
        }
        flag = manager.performPayment(id_accountTo,balanceTo);
        if (!flag) {
            return Path.PAGE_ERROR_PAGE;
        }
        flag = manager.updateStatusPayment(id_payment);
        if (!flag) {
            return Path.PAGE_ERROR_PAGE;
        }
        return  Path.PAGE_SUCCESSFUL_PAYMENT;
    }
}

