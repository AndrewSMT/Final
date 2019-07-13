package app.web.command.viewCommand;


import app.Path;
import app.been.ListPayment;
import app.been.ViewCard;
import app.been.ViewPayment;
import app.entities.User;
import app.exception.AppException;
import app.manager.DBManager;
import app.web.command.Command;
import app.web.command.logCommand.RegistrationCommand;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ViewPaymentCommand extends Command {
    //    private static final long serialVersionUID = -3071536593627692473L;
    //sorting
    private static class CompareByNumberDown implements Comparator<ViewCard> {
        public int compare(ViewCard bean1, ViewCard bean2) {
            if (bean1.getNumber() > bean2.getNumber()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    private static class CompareByNameDown implements Comparator<ViewCard> {
        public int compare(ViewCard bean1, ViewCard bean2) {
            return bean1.getName().compareTo(bean2.getName());
        }
    }
    private static class CompareByBalanceDown implements Comparator<ViewCard> {
        public int compare(ViewCard bean1, ViewCard bean2) {
            if (bean1.getBalance() > bean2.getBalance()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    private static class CompareByNumberUp implements Comparator<ViewCard> {
        public int compare(ViewCard bean1, ViewCard bean2) {
            if (bean1.getNumber() < bean2.getNumber()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    private static class CompareByNameUp implements Comparator<ViewCard>  {
        public int compare(ViewCard bean1, ViewCard bean2) {
            return bean2.getName().compareTo(bean1.getName());
        }
    }
    private static class CompareByBalanceUp implements Comparator<ViewCard> {
        public int compare(ViewCard bean1, ViewCard bean2) {
            if (bean1.getBalance() < bean2.getBalance()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);
    private static Comparator<ViewCard> CompareByNumberDown = new CompareByNumberDown();
    private static Comparator<ViewCard> CompareByNameDown = new CompareByNameDown();
    private static Comparator<ViewCard> CompareByBalanceDown = new CompareByBalanceDown();
    private static Comparator<ViewCard> CompareByNumberUp = new CompareByNumberUp();
    private static Comparator<ViewCard> CompareByNameUp = new CompareByNameUp();
    private static Comparator<ViewCard> CompareByBalanceUp = new CompareByBalanceUp();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        DBManager manager = DBManager.getInstance();
        HttpSession session = request.getSession();
        String sort = request.getParameter("sort");
        User user = (User) session.getAttribute("user");

        List<ListPayment> listPayment = manager.getUserIdPayment(user);
        List<ViewPayment> viewPayments = new ArrayList<>();
        for (ListPayment lp: listPayment){
            ViewPayment payment = manager.getUserNumberFrom(lp.getId_payment(),user,viewPayments);
            manager.getUserPayment(lp.getId_payment(),viewPayments,payment);
        }

        LOG.trace("Found in DB: viewPayments --> " + viewPayments);

   /*     if("sortNumbDown".equals(sort)) {
            viewCards.sort(CompareByNumberDown);
        }else if("sortNameDown".equals(sort)) {
            viewCards.sort(CompareByNameDown);
        }else if("sortBalDown".equals(sort)) {
            viewCards.sort(CompareByBalanceDown);
        }else if("sortNumbUp".equals(sort)) {
            viewCards.sort(CompareByNumberUp);
        }else if("sortNameUp".equals(sort)) {
            viewCards.sort(CompareByNameUp);
        }else if("sortBalUp".equals(sort)) {
            viewCards.sort(CompareByBalanceUp);
        }*/

        String forward = Path.PAGE_ERROR_PAGE;

        if ( !viewPayments.isEmpty()) {
            forward = Path.PAGE_LIST_PAYMENTS;
        }


        // put user order beans list to request
        request.setAttribute("viewPayments", viewPayments);


        LOG.trace("Set the request attribute: viewPayments --> " + viewPayments);

        LOG.debug("Commands finished");
        return forward;
    }
}