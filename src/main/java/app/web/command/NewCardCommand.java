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


public class NewCardCommand extends Command {
    //    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        DBManager manager = DBManager.getInstance();
        String number = request.getParameter("number");
        String date = request.getParameter("date");
        String code = request.getParameter("code");
        String type = request.getParameter("type");

        HttpSession session = request.getSession();
        if (number == null || date == null || code == null ||
                number.isEmpty() || date.isEmpty() ||
                code.isEmpty() ) {
            throw new AppException("All fields must be fill");
        }
        //check exist user
        User user = (User) session.getAttribute("user");
        boolean cardExist = manager.findCardByLogin(user,number);

        if (cardExist) {
            throw new AppException("Card already exist in your account");
        }
        Card card = new Card();
        card.setNumber(Integer.parseInt(number));
        card.setDate(date);
        card.setCode(Integer.parseInt(code));
        card.setId_user(user.getId_user());
         card.setId_account(manager.insertNewAccount(type));


        Card cardd = manager.insertNewCard(card);
        String forward = Path.PAGE_ERROR_PAGE;

        if (cardd != null) {
            forward = Path.PAGE_CLIENT_MENU;
        }
        LOG.debug("Command finished");
        return forward;
    }
}