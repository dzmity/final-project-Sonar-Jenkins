package by.epam.filmrating.command.guest;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.exception.ApplicationException;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.MessageManager;
import by.epam.filmrating.entity.Role;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.util.FilmRatingRegEx;
import by.epam.filmrating.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Locale;

/**
 * The {@code LogInCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for the log in operation on the web-application.
 * @author Dmitry Rafalovich
 */
public class LogInCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String LOCALE = "locale";
    private static final String LOGIN_ERROR = "loginError";
    private static final String LOGIN_KEY = "message.login.login";
    private final static String BEFORE_LOGIN = "beforeLogin";
    private static final String HREF_ACTION = "/controller?";
    private static final String HREF_COMMAND = "command=";
    private static final String ADMIN_COMMAND = "view_comments";
    private static final String GO_TO_LOGIN_COMMAND = "go_to_login";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String CURRENT_USER = "currentUser";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the comments.jsp if role is administrator;
     *        to the last before registration page if registered
     *        user is not administrator;
     *        redirecting to the login.jsp if user is not registered;
     *        to the to the current page if an error occurred.
     */
    @Override
    public String execute(SessionRequestContent content) {

        Locale locale = new Locale((String) content.getSessionAttributes().get(LOCALE));
        String[] login = content.getRequestParameters().get(LOGIN);
        String[] password = content.getRequestParameters().get(PASSWORD);

        if (login == null || password == null) {
            LOG.error("'Login' and 'Password' fields must be filled. Check 'required' parameter for " +
                    " <input> tags in the jsp.");
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        try {
            FilmRatingRegEx.checkData(LOGIN, login[0]);
            FilmRatingRegEx.checkData(PASSWORD, password[0]);

            UserService userService = new UserService();
            User user = userService.findByLoginPassword(login[0], password[0]);

            if (user.getLogin() == null) {
                content.setAttribute(LOGIN_ERROR, MessageManager.getProperty(LOGIN_KEY, locale));
                return HREF_ACTION + HREF_COMMAND + GO_TO_LOGIN_COMMAND;
            }

            content.addSessionAttribute(CURRENT_USER, user);

            if (user.getRole() == Role.ADMINISTRATOR) {
                return HREF_ACTION + HREF_COMMAND + ADMIN_COMMAND;
            }

            String before = (String) content.getSessionAttributes().get(BEFORE_LOGIN);
            return before == null ? (String) content.getSessionAttributes().get(CURRENT_PAGE) : before;

        } catch (ServiceException e) {
            LOG.error("Exception in LogInCommand", e);
        } catch (ApplicationException e) {
            LOG.error("Wrong data format from jsp", e);
        }
        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }
}
