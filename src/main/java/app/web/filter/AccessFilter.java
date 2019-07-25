package app.web.filter;


import java.io.IOException;
import java.util.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import app.Path;
import app.entities.TypeUser;
import org.apache.log4j.Logger;

public class AccessFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(AccessFilter.class);

    // commands access
    private Map<TypeUser, List<String>> accessMap = new HashMap<TypeUser, List<String>>();
    private List<String> commons = new ArrayList<String>();
    private List<String> outOfControl = new ArrayList<String>();

    public void destroy() {
        LOG.debug("Filter destruction starts");
        // do nothing
        LOG.debug("Filter destruction finished");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOG.debug("Filter starts");

        if (accessAllowed(request)) {
            LOG.debug("Filter finished");
            chain.doFilter(request, response);
        } else {
            String errorMessage = "You do not have right to access the requested page";

            request.setAttribute("errorMessage", errorMessage);
            LOG.trace("Set the request attribute: errorMessage --> " + errorMessage);

            request.getRequestDispatcher(Path.PAGE_ERROR_PAGE)
                    .forward(request, response);
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");
        if (commandName == null || commandName.isEmpty()) {
            return false;
        }

        if (outOfControl.contains(commandName)) {
            return true;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return false;
        }

        TypeUser userRole = (TypeUser)session.getAttribute("userRole");
        if (userRole == null) {
            return false;
        }

        return accessMap.get(userRole).contains(commandName)
                || commons.contains(commandName);
    }

    public void init(FilterConfig fConfig)  {
        LOG.debug("Filter initialization starts");

        // roles
        accessMap.put(TypeUser.ADMIN, asList(fConfig.getInitParameter("admin")));
        accessMap.put(TypeUser.CLIENT, asList(fConfig.getInitParameter("client")));
        LOG.trace("Access map --> " + accessMap);

        // commons
        commons = asList(fConfig.getInitParameter("common"));
        LOG.trace("Common commands --> " + commons);

        // out of control
        outOfControl = asList(fConfig.getInitParameter("out-of-control"));
        LOG.trace("Out of control commands --> " + outOfControl);

        LOG.debug("Filter initialization finished");
    }



     //list of parameter values.

    private List<String> asList(String str) {
        List<String> list = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }

}