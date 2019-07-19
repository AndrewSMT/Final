package app.web.filter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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
            String errorMessasge = "You do not have right to access the requested page";
            request.setAttribute("errorMessage", errorMessasge);
            LOG.trace("Set the request attribute: errorMessage --> " + errorMessasge);
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
        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return false;
        }
        return true;
    }

    public void init(FilterConfig fConfig) {
        LOG.debug("Filter initialization starts");

        LOG.debug("Filter initialization finished");
    }

}