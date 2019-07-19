package app.web.command;

import app.Path;
import app.been.ViewCard;
import app.exception.AppException;
import app.manager.DBManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoPaymentCommand extends Command {
    private static final Logger LOG = Logger.getLogger(GoPaymentCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        DBManager manager = DBManager.getInstance();
        int id_accountTo;

        // get payment parameters from a request
        int from = Integer.parseInt(request.getParameter("from"));
        int to = Integer.parseInt(request.getParameter("to"));
        int id_account_service = Integer.parseInt(request.getParameter("id_account_service"));
        int howmuch = Integer.parseInt(request.getParameter("howmuch"));
        int id_payment = Integer.parseInt(request.getParameter("id_payment"));

        LOG.trace("Request parameter: from --> " + from);
        LOG.trace("Request parameter: to --> " + to);
        LOG.trace("Request parameter: id_account_service --> " + id_account_service);
        LOG.trace("Request parameter: howmuch --> " + howmuch);
        LOG.trace("Request parameter: id_payment --> " + id_payment);

        ViewCard fromCard = manager.getRecipientCard(from);
        ViewCard toCard = manager.getRecipientCard(to);

        LOG.trace("Found in DB: fromCard --> " + fromCard);
        LOG.trace("Found in DB: toCard --> " + toCard);

        //define type payment
        int id_accountFrom = fromCard.getId_account();
        LOG.trace("Found in DB: id_accountFrom --> " + id_accountFrom);

        if (id_account_service !=  0){
             id_accountTo = id_account_service;
        }else {
             id_accountTo = toCard.getId_account();
             LOG.trace("Found in DB: id_accountTo --> " + id_accountTo);
        }

        //balance management and check on error
        int balanceFrom = manager.getBalance(id_accountFrom);
        int balanceTo = manager.getBalance(id_accountTo);
        LOG.trace("Found in DB: balanceFrom --> " + balanceFrom);
        LOG.trace("Found in DB: balanceTo --> " + balanceTo);

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
        LOG.debug("Command finish");
        return  Path.PAGE_SUCCESSFUL_PAYMENT;
    }
}

