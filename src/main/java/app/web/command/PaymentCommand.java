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

//Start payment command
public class PaymentCommand extends  Command {

    private static final Logger LOG = Logger.getLogger(PaymentCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        DBManager manager = DBManager.getInstance();
        HttpSession session = request.getSession();

        String payment = request.getParameter("payment");
        String payment2 = (String) request.getAttribute("payment");

        LOG.trace("Request parameter: payment --> " + payment);
        LOG.trace("Request attribute: payment2 --> " + payment2);

        //get user
        User user = (User) session.getAttribute("user");

        //fill user cards  and service list
        List<ViewCard> viewCards  = manager.getCards(user);
        List<ViewService> viewService  = manager.getService(payment);
        if(payment == null){
           viewService  = manager.getService(payment2);
            LOG.trace("Found in DB: payment --> " + viewService);
        }

        request.setAttribute("viewCards",viewCards);
        request.setAttribute("viewService",viewService);

        LOG.trace("Set the request attribute: viewCards --> " + viewCards);
        LOG.trace("Set the request attribute: viewService --> " + viewService);

        String forward = Path.PAGE_ERROR_PAGE;

        //define next page
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

        LOG.debug("Command finish");
        return forward;
    }
}
