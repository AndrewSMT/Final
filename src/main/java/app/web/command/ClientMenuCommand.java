package app.web.command;

import app.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class ClientMenuCommand extends Command {

    private static final long serialVersionUID = 7732286214029478505L;
    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)  {
        return Path.PAGE_CLIENT_MENU;
    }

}
