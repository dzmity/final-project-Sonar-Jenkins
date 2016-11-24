package by.epam.filmrating.command.user;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.exception.ApplicationException;
import by.epam.filmrating.exception.CommandAccessException;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.MessageManager;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.util.AccessInspector;
import by.epam.filmrating.util.FilmRatingRegEx;
import by.epam.filmrating.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Locale;

/**
 * The {@code UpdateUserCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for changing current user information( name, surname, login).
 * @author Dmitry Rafalovich
 */
public class UpdateUserCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String LOCALE = "locale";
    private static final String LOGIN_ERROR = "loginError";
    private static final String LOGIN_KEY = "message.registration.login";
    private static final String CURRENT_USER = "currentUser";
    private static final String NAME = "name";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String LOGIN = "login";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the index.jsp if access denied for the current user;
     *        to the current page in other cases.
     */
    @Override
    public String execute(SessionRequestContent content) {

        try {
            UserService userService = new UserService();
            userService.checkBanForAll();
        } catch (ServiceException e) {
            LOG.error("Exception in ban checking operation.", e);
        }

        User currentUser = (User) content.getSessionAttributes().get(CURRENT_USER);
        try {
            AccessInspector.checkRegistration(currentUser);
        } catch (CommandAccessException e) {
            LOG.error("Access denied.", e);
            return PathManager.getProperty(INDEX_PATH);
        }

        Locale locale = new Locale((String) content.getSessionAttributes().get(LOCALE));
        String[] name = content.getRequestParameters().get(FIRST_NAME);
        String[] lastName = content.getRequestParameters().get(LAST_NAME);
        String[] login = content.getRequestParameters().get(LOGIN);

        if (name == null || lastName == null || login == null) {
            LOG.error("All fields must be filled. Check 'required' parameter for " +
                    " <input> tags in the settings.jsp.");
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }


        try {
            FilmRatingRegEx.checkData(NAME, name[0]);
            FilmRatingRegEx.checkData(NAME, lastName[0]);
            FilmRatingRegEx.checkData(LOGIN, login[0]);

            UserService userService = new UserService();
            if (currentUser.getLogin().equals(login[0]) || !userService.existLogin(login[0])) {
                User user = new User();
                user.setName(name[0]);
                user.setSurname(lastName[0]);
                user.setLogin(login[0]);
                long id = currentUser.getId();
                userService.updateById(id ,user);
                content.setAttribute(CURRENT_USER, userService.findById(id));
            } else {
                content.setAttribute(LOGIN_ERROR, MessageManager.getProperty(LOGIN_KEY, locale));
            }

        } catch (ServiceException e) {
            LOG.error("Exception in UpdateUserCommand", e);
        } catch (ApplicationException e) {
            LOG.error("Wrong data format from settings.jsp. Check regex.", e);
        }
        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }
}
