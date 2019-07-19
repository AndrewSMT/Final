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

//Command for verification transfer payments
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
        DBManager manager = DBManager.getInstance();

        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String howMuch = request.getParameter("howmuch");
        int id_account = Integer.parseInt(request.getParameter("id_account"));

        LOG.trace("Request parameter: from --> " + from);
        LOG.trace("Request parameter: to --> " + to);
        LOG.trace("Request parameter: howMuch --> " + howMuch);
        LOG.trace("Request parameter: id_account --> " + id_account);

        //check on empty field
        if (from == null || to == null || howMuch == null|| from.isEmpty() || to.isEmpty() || howMuch.isEmpty()) {
            request.setAttribute("valid", "Fill in all the fields");
            request.setAttribute("payment", "transfer");
            return Path.COMMAND_TRANSFER_PAYMENT;
        }

        int toInt = Integer.parseInt(to);
        int howMuchInt = Integer.parseInt(howMuch);

        ViewCard viewCard = manager.getRecipientCard(toInt);
        LOG.trace("Found in DB: viewCard --> " + viewCard);

        //check on invalid  field
        if (viewCard == null){
            request.setAttribute("valid", "Recipient card does not exist");
            request.setAttribute("payment", "transfer");
            return Path.COMMAND_TRANSFER_PAYMENT;
        }
        if(!validatePatern(to, NUMBER_PATTERN)) {
            request.setAttribute("valid", "Field <To card>  must contain only 9 numbers");
            request.setAttribute("payment", "transfer");
            return Path.COMMAND_TRANSFER_PAYMENT;
        }
        if(!validatePatern(howMuch, PAY_PATTERN)) {
            request.setAttribute("valid", "Field <Amount> must contain from 1 to 9 numbers");
            request.setAttribute("payment", "transfer");
            return Path.COMMAND_TRANSFER_PAYMENT;
        }

        int recipientCard;
        recipientCard = viewCard.getId_account();
        LOG.trace("Found in DB: recipientCard --> " + recipientCard);
        //insert into payment
        int id_payment = manager.insertPayment(howMuchInt);

        //insert into account_payment and check on error
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

            LOG.debug("Command finish");
            return Path.PAGE_VERIFICATION;
        }
    }
    //validation method
    public boolean validatePatern(String value, String pattern) {
        pat = Pattern.compile(pattern);
        matcher = pat.matcher(value);
        return matcher.matches();
    }
}
