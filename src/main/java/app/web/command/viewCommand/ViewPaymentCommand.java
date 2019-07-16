package app.web.command.viewCommand;


import app.Path;
import app.been.ListPayment;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ViewPaymentCommand extends Command {
    //sorting
    private static class CompareByNumberDown implements Comparator<ViewPayment> {
        public int compare(ViewPayment bean1, ViewPayment bean2) {
            if (bean1.getNumberFrom() > bean2.getNumberFrom()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    private static class CompareByDateDown implements Comparator<ViewPayment> {
        DateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        @Override
        public int compare(ViewPayment o1, ViewPayment o2) {
            try {
                return f.parse(o1.getDate()).compareTo(f.parse(o2.getDate()));
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
    private static class CompareByNumberUp implements Comparator<ViewPayment> {
        public int compare(ViewPayment bean1, ViewPayment bean2) {
            if (bean1.getNumberFrom() < bean2.getNumberFrom()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    private static class CompareByDateUp implements Comparator<ViewPayment> {
        DateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        @Override
        public int compare(ViewPayment o1, ViewPayment o2) {
            try {
                return f.parse(o2.getDate()).compareTo(f.parse(o1.getDate()));
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);
    private static Comparator<ViewPayment> CompareByNumberDown = new CompareByNumberDown();
    private static Comparator<ViewPayment> CompareByDateDown = new CompareByDateDown();
    private static Comparator<ViewPayment> CompareByNumberUp = new CompareByNumberUp();
    private static Comparator<ViewPayment> CompareByDateUp = new CompareByDateUp();
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
        boolean flag = true;
        for (ListPayment lp: listPayment){
            ViewPayment payment = manager.getUserNumberFrom(lp.getId_payment(),user,viewPayments);
           flag = manager.getUserPayment(lp.getId_payment(),viewPayments,payment);
        }

        LOG.trace("Found in DB: viewPayments --> " + viewPayments);

       if("sortNumbDown".equals(sort)) {
           viewPayments.sort(CompareByNumberDown);
        }else if("sortNameDown".equals(sort)) {
           viewPayments.sort(ViewPaymentCommand.CompareByDateDown);
        }else if("sortNumbUp".equals(sort)) {
           viewPayments.sort(CompareByNumberUp);
        }else if("sortNameUp".equals(sort)) {
           viewPayments.sort(ViewPaymentCommand.CompareByDateUp);
        }

        String forward = Path.PAGE_ERROR_PAGE;

        if (flag) {
            forward = Path.PAGE_LIST_PAYMENTS;
        }


        // put user order beans list to request
        request.setAttribute("viewPayments", viewPayments);


        LOG.trace("Set the request attribute: viewPayments --> " + viewPayments);

        LOG.debug("Commands finished");
        return forward;
    }
}