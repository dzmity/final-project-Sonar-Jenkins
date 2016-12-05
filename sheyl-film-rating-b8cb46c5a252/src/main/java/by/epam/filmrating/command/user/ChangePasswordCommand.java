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
 * The {@code ChangePasswordCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for changing user password.
 * @author Dmitry Rafalovich
 */
public class ChangePasswordCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String LOCALE = "locale";
    private static final String CURRENT_USER = "currentUser";
    private static final String PASS_ERROR = "passwordError";
    private static final String CONFIRM_ERROR = "error";
    private static final String PASS_INFO = "passwordInfo";
    private static final String PASS_INFO_KEY = "message.settings.passwordInfo";
    private static final String PASS_ERROR_KEY = "message.settings.passwordError";
    private static final String CONFIRM_ERROR_KEY = "message.settings.error";
    private static final String PASS = "password";
    private static final String OLD_PASS = "oldPassword";
    private static final String NEW_PASS = "newPassword";
    private static final String CONFIRM = "confirm";

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
        String[] oldPassword = content.getRequestParameters().get(OLD_PASS);
        String[] newPassword = content.getRequestParameters().get(NEW_PASS);
        String[] confirm = content.getRequestParameters().get(CONFIRM);

        if (oldPassword == null || newPassword == null || confirm == null) {
            LOG.error("All fields must be filled. Check 'required' parameter for " +
                    " <input> tags in the settings.jsp.");
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        if (!newPassword[0].equals(confirm[0])) {
            LOG.error("Passwords donn't match! Check js on settings.jsp.");
            content.setAttribute(CONFIRM_ERROR, MessageManager.getProperty(CONFIRM_ERROR_KEY, locale));
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        try {
            FilmRatingRegEx.checkData(PASS, oldPassword[0]);
            FilmRatingRegEx.checkData(PASS, newPassword[0]);

            UserService userService = new UserService();
            User user = userService.findByLoginPassword(currentUser.getLogin(), oldPassword[0]);

            if (user.getLogin() == null) {
                content.setAttribute(PASS_ERROR, MessageManager.getProperty(PASS_ERROR_KEY, locale));
                return (String) content.getSessionAttributes().get(CURRENT_PAGE);
            }
            userService.changePasswordById(currentUser.getId(), oldPassword[0], newPassword[0]);
            content.setAttribute(PASS_INFO, MessageManager.getProperty(PASS_INFO_KEY, locale));

        } catch (ServiceException e) {
            LOG.error("Exception in ChangePasswordCommand", e);
        } catch (ApplicationException e) {
            LOG.error("Wrong data format from settings.jsp. Check regex.", e);
        }
        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }

}
