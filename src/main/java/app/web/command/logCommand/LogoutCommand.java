package app.web.command.logCommand;

import app.Path;
import app.exception.AppException;
import app.web.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//Command for logout user
public class LogoutCommand extends Command {

    private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)  {
        LOG.debug("Command starts");
        //clear session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        LOG.debug("Command finished");
        return Path.PAGE_LOGIN;
    }
}

