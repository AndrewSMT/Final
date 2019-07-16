package app.web.command;

import app.Path;
import app.been.ViewCard;
import app.exception.AppException;
import app.manager.DBManager;
import app.web.command.logCommand.LoginCommand;
import app.web.command.logCommand.RegistrationCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class VerificationCommand extends Command {
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);
    private static final String NUMBER_PATTERN = "^[0-9]{9}$";
    private static final String PAY_PATTERN = "^[0-9]{1,9}$";
    private Matcher matcher;
    private Pattern pat;
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        int from = Integer.parseInt(request.getParameter("from"));
        int to = Integer.parseInt(request.getParameter("to"));
        int howMuch = Integer.parseInt(request.getParameter("howmuch"));
        int id_account = Integer.parseInt(request.getParameter("id_account"));
        // obtain login and password from a request
        DBManager manager = DBManager.getInstance();
        ViewCard viewCard = manager.getRecipientCard(to);

        if(!validatePatern(request.getParameter("to"), NUMBER_PATTERN)) {
            request.setAttribute("valid", "Field <<To card>>  must contain only 9 numbers");
            request.setAttribute("payment", "transfer");
            return Path.COMMAND_TRANSFER_PAYMENT;
        }
        if(!validatePatern(request.getParameter("howmuch"), NUMBER_PATTERN)) {
            request.setAttribute("valid", "Field <<How much>> must contain from 1 to 9 numbers");
            request.setAttribute("payment", "transfer");
            return Path.COMMAND_TRANSFER_PAYMENT;
        }

        int recipientCard = 0;
        recipientCard = viewCard.getId_account();
        int id_payment = manager.insertPayment(howMuch);
        if (recipientCard == 0) {
            return Path.PAGE_ERROR_PAGE;
        }else {
            boolean flag;
            int fromm = 1;
            flag =  manager.insertFromTo(fromm, id_account, id_payment);
            if (!flag) {
                return Path.PAGE_ERROR_PAGE;
            }
            int too = 2;
            flag = manager.insertFromTo(too, recipientCard, id_payment);
            if (!flag) {
                return Path.PAGE_ERROR_PAGE;
            }
            request.setAttribute("from", from);
            request.setAttribute("to", to);
            request.setAttribute("howmuch", howMuch);
            request.setAttribute("id_payment", id_payment);
            return Path.PAGE_VERIFICATION;
        }
    }
    //valid methods
    public boolean validatePatern(String value, String pattern) {
        pat = Pattern.compile(pattern);
        matcher = pat.matcher(value);
        return matcher.matches();
    }
}
