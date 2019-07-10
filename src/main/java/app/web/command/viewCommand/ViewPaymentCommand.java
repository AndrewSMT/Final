package app.web.command.viewCommand;


/*public class ViewPaymentCommand extends Command {
    //    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        DBManager manager = DBManager.getInstance();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        List<ViewCard> viewCards = manager.getUserCards(user);
        LOG.trace("Found in DB: ViewCard --> " + viewCards);

        //Collections.sort(userOrderBeanList, compareById);

        // put user order beans list to request
        request.setAttribute("viewCards", viewCards);
        LOG.trace("Set the request attribute: viewCards --> " + viewCards);

        LOG.debug("Commands finished");
        return Path.PAGE_LIST_CARDS;
    }
}*/