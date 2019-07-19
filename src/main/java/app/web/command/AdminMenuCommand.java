package app.web.command;

import app.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminMenuCommand extends Command {
    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)  {
        return Path.PAGE_ADMIN_MENU;
    }

}

