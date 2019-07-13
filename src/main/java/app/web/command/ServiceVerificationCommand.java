package app.web.command;

import app.Path;
import app.been.ViewCard;
import app.exception.AppException;
import app.manager.DBManager;
import app.web.command.logCommand.LoginCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServiceVerificationCommand extends Command{
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        int from = Integer.parseInt(request.getParameter("from"));
        String title = request.getParameter("title");
        int howMuch = Integer.parseInt(request.getParameter("howmuch"));
        int personal_account = Integer.parseInt(request.getParameter("personal_account"));
        int id_account_card = Integer.parseInt(request.getParameter("id_account_card"));
        int id_account_service = Integer.parseInt(request.getParameter("id_account_service"));
        // obtain login and password from a request
        DBManager manager = DBManager.getInstance();
        int id_payment = manager.insertPaymentService(howMuch,personal_account);
            boolean flag;
            int fromm = 1;
            flag =  manager.insertFromTo(fromm, id_account_card, id_payment);
            if (!flag) {
                return Path.PAGE_ERROR_PAGE;
            }
            int too = 2;
            flag = manager.insertFromTo(too, id_account_service, id_payment);
            if (!flag) {
                return Path.PAGE_ERROR_PAGE;
            }
            request.setAttribute("from", from);
            request.setAttribute("to", 0);
            request.setAttribute("title", title);
            request.setAttribute("id_account_service", id_account_service);
            request.setAttribute("personal_account", personal_account);
            request.setAttribute("howmuch", howMuch);
            request.setAttribute("id_payment", id_payment);
            return Path.PAGE_VERIFICATION_SERVICE;
        }
    }

