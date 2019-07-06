package app.web.command.page;
import app.Path;
import app.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegPage extends Command {
    //  private static final long serialVersionUID = 7732286214029478505L;
    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)  {
        return Path.PAGE_REG;
    }

}
