package app.web.command.logCommand;

import app.Path;
import app.entities.User;
import app.exception.AppException;
import app.manager.DBManager;
import app.web.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegistrationCommand extends Command {
    private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);
    private static final String PHONE_PATTERN = ".+38\\([0-9]{3}\\)[0-9]{3}-[0-9]{2}-[0-9]{2}";
    private static final String LOGIN_PATTERN = "(^(\\p{L}+){6,20}$)";
    private static final String NAME_PATTERN = "(^(\\p{L}+){3,20}$)";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(^(\\w+){6,20}$)";
    private Matcher matcher;
    private Pattern pat;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        DBManager manager = DBManager.getInstance();
        String login = request.getParameter("login");
        LOG.trace("Request parameter: login --> " + login);
        String pass = request.getParameter("pass");
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String phone = request.getParameter("phone");
        String forward = null;
        //check exist user
        User userr = manager.findUserByLogin(login);
        LOG.trace("Found in DB: user --> " + userr);
        //validations section

        if (userr != null) {
            request.setAttribute("valid", "Account with this login already exists");
            return Path.PAGE_REG;
        }
        if(!validatePatern(login, LOGIN_PATTERN)) {
            request.setAttribute("valid", "Login must contain only letters from 5 to 4");
            return Path.PAGE_REG;
        }
        if(!validatePatern(first_name, NAME_PATTERN)) {
            request.setAttribute("valid", "First_name must contain only letters from 3 to 20");
            return Path.PAGE_REG;
        }
        if(!validatePatern(last_name, NAME_PATTERN)) {
            request.setAttribute("valid", "Last_name must contain only letters from 3 to 20");
            return Path.PAGE_REG;
        }
        if(!validatePatern(pass, PASSWORD_PATTERN)) {
            request.setAttribute("valid", "Password must contain at least one character and one number from 6 to 20");
            return Path.PAGE_REG;
        }
        if(!validatePatern(phone, PHONE_PATTERN)) {
            request.setAttribute("valid", "Enter the phone number according to the pattern +38(___)___-__-__");
            return Path.PAGE_REG;
        }

        //create new user
        User user = new User();
        user.setLogin(login);
        user.setPassword(pass);
        user.setPhone(phone);
        user.setFirstName(first_name);
        user.setLastName(last_name);

        manager.insertNewUser(user);


        if (user != null) {
            forward = Path.PAGE_MAIN;
        }
        LOG.debug("Command finished");
        return forward;
    }

    //valid methods
    public boolean validatePatern(String value, String pattern) {
        pat = Pattern.compile(pattern);
        matcher = pat.matcher(value);
        return matcher.matches();
    }
}
