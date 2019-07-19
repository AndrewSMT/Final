package app.web.command;

import app.Path;
import app.been.ViewCard;
import app.exception.AppException;
import app.manager.DBManager;
import app.web.command.logCommand.LoginCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Command for verification service payment
public class ServiceVerificationCommand extends Command {
    private static final Logger LOG = Logger.getLogger(ServiceVerificationCommand.class);
    private static final String PAY_PATTERN = "^[0-9]{1,9}$";
    private Matcher matcher;
    private Pattern pat;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        DBManager manager = DBManager.getInstance();

        String from = request.getParameter("from");
        String title = request.getParameter("title");
        String howMuch = request.getParameter("howmuch");
        String pay = request.getParameter("pay");
        String personal_account = request.getParameter("personal_account");
        String id_account_card = request.getParameter("id_account_card");
        String id_account_service = request.getParameter("id_account_service");

        LOG.trace("Request parameter: from --> " + from);
        LOG.trace("Request parameter: title --> " + title);
        LOG.trace("Request parameter: howMuch --> " + howMuch);
        LOG.trace("Request parameter: pay --> " + pay);
        LOG.trace("Request parameter: personal_account --> " + personal_account);
        LOG.trace("Request parameter: id_account_card --> " + id_account_card);
        LOG.trace("Request parameter: id_account_service --> " + id_account_service);

        //check on empty field
        if (title == null || from == null || howMuch == null|| id_account_card == null|| id_account_service == null ||
                from.isEmpty() || title.isEmpty() || howMuch.isEmpty() || id_account_card.isEmpty() || id_account_service.isEmpty()){
            if("internet".equals(pay)) {
                request.setAttribute("payment", "internet");
            }else{
                request.setAttribute("payment", "public");
            }
            request.setAttribute("valid", "Fill in all the fields");
            return Path.COMMAND_TRANSFER_PAYMENT;
        }
        //check on invalid  field
        if (!validatePatern(request.getParameter("howmuch"), PAY_PATTERN) && "internet".equals(pay)) {
            request.setAttribute("valid", "Field <Amount> must contain from 1 to 9 numbers");
            request.setAttribute("payment", "internet");
            return Path.COMMAND_TRANSFER_PAYMENT;
        } else if (!validatePatern(request.getParameter("howmuch"), PAY_PATTERN) && "public".equals(pay)){
            request.setAttribute("valid", "Field <Amount> must contain from 1 to 9 numbers");
            request.setAttribute("payment", "public");
            return Path.COMMAND_TRANSFER_PAYMENT;
        }

        //parseInt
        int id_account_card_int = Integer.parseInt(id_account_card);
        int id_account_service_int = Integer.parseInt(id_account_service);

        //insert into payment
        int id_payment = manager.insertPaymentService(Integer.parseInt(howMuch), Integer.parseInt(personal_account));

        //insert into account_payment and check on error
        boolean flag;
        int fromm = 1;
        flag = manager.insertFromTo(fromm, id_account_card_int, id_payment);
        if (!flag) {
            return Path.PAGE_ERROR_PAGE;
        }
        int too = 2;
        flag = manager.insertFromTo(too, id_account_service_int, id_payment);
        if (!flag) {
            return Path.PAGE_ERROR_PAGE;
        }

        request.setAttribute("from", from);
        request.setAttribute("to", 0);
        request.setAttribute("title", title);
        request.setAttribute("id_account_service", id_account_service_int);
        request.setAttribute("personal_account", personal_account);
        request.setAttribute("howmuch", howMuch);
        request.setAttribute("id_payment", id_payment);

        LOG.debug("Command finish");
        return Path.PAGE_VERIFICATION_SERVICE;
    }
    //validation method
    public boolean validatePatern(String value, String pattern) {
        pat = Pattern.compile(pattern);
        matcher = pat.matcher(value);
        return matcher.matches();
    }
}

