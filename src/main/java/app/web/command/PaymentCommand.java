package app.web.command;

import app.Path;
import app.been.ViewCard;
import app.been.ViewService;
import app.entities.User;
import app.exception.AppException;
import app.manager.DBManager;
import app.web.command.logCommand.LoginCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class PaymentCommand extends  Command {

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        String payment = request.getParameter("payment");
        String payment2 = (String) request.getAttribute("payment");

        DBManager manager = DBManager.getInstance();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String forward = Path.PAGE_ERROR_PAGE;
        List<ViewCard> viewCards  = manager.getCards(user);
        List<ViewService> viewService  = manager.getService(payment);
        request.setAttribute("viewCards",viewCards);
        request.setAttribute("viewService",viewService);
       if("transfer".equals(payment)){
           forward = Path.PAGE_PAYMENT_TRANSFER;
       }else if("internet".equals(payment)){
           forward = Path.PAGE_PAYMENT_INTERNET;
       }else if("public".equals(payment)){
           forward = Path.PAGE_PAYMENT_PUBLIC;
       }

        if("transfer".equals(payment2)){
            forward = Path.PAGE_PAYMENT_TRANSFER;
        }else if("internet".equals(payment2)){
            forward = Path.PAGE_PAYMENT_INTERNET;
        }else if("public".equals(payment2)){
            forward = Path.PAGE_PAYMENT_PUBLIC;
        }

        return forward;
    }
}
