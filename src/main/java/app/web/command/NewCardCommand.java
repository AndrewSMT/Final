package app.web.command;

import app.Path;
import app.entities.Card;
import app.entities.User;
import app.exception.AppException;
import app.manager.DBManager;
import app.web.command.logCommand.RegistrationCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Command for add new card
public class NewCardCommand extends Command {
    private static final Logger LOG = Logger.getLogger(NewCardCommand.class);
    private static final String NUMBER_PATTERN = "^[0-9]{9}$";
    private static final String DATE_PATTERN = "^[0-9]{2}\\/[0-9]{2}$";
    private static final String CVV_PATTERN = "^[0-9]{3}$";
    private Matcher matcher;
    private Pattern pat;
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        DBManager manager = DBManager.getInstance();
        HttpSession session = request.getSession();

        String number = request.getParameter("number");
        String date = request.getParameter("date");
        String code = request.getParameter("code");
        String type = request.getParameter("type");

        LOG.trace("Request parameter: number --> " + number);
        LOG.trace("Request parameter: date --> " + date);
        LOG.trace("Request parameter: code --> " + code);
        LOG.trace("Request parameter: type --> " + type);

        //check parameters on null
        if (number == null || date == null || code == null ||
                number.isEmpty() || date.isEmpty() ||
                code.isEmpty() ) {
            request.setAttribute("valid", "All fields must be fill");
            return Path.PAGE_ADD_CARD;
        }

        //check exist card
        User user = (User) session.getAttribute("user");
        boolean cardExist = manager.findCardByLogin(user,number);
        if (cardExist) {
            request.setAttribute("valid", "Card already exist in your account");
            return Path.PAGE_ADD_CARD;
        }
        //validation card

        if(!validatePatern(number, NUMBER_PATTERN)) {
            request.setAttribute("valid", "Field card number must contain only 9 numbers");
            return Path.PAGE_ADD_CARD;
        }
        if(!validatePatern(date, DATE_PATTERN)) {
            request.setAttribute("valid", "Enter the date  according to the pattern MM/YY");
            return Path.PAGE_ADD_CARD;
        }
        if(!validatePatern(code, CVV_PATTERN)) {
            request.setAttribute("valid", "Field СVV2 must contain only 3 numbers");
            return Path.PAGE_ADD_CARD;
        }

        //create new card
        Card card = new Card();
        card.setNumber(Integer.parseInt(number));
        card.setDate(date);
        card.setCode(Integer.parseInt(code));
        card.setId_user(user.getId_user());
        card.setId_account(manager.insertNewAccount(type));
        Card cardd = manager.insertNewCard(card);

        //check on error
        String forward = Path.PAGE_ERROR_PAGE;
        if (cardd != null) {
            forward = Path.PAGE_SUCCESSFUL_ADD_CARD;
        }
        LOG.debug("Command finished");
        return forward;
    }
    //valid methods
    public boolean validatePatern(String value, String pattern) {
        pat = Pattern.compile(pattern);
        matcher = pat.matcher(value);
        return matcher.matches();
    }
}