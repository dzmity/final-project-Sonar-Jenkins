package by.epam.filmrating.command.guest;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.exception.ApplicationException;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.manager.MessageManager;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.util.FilmRatingRegEx;
import by.epam.filmrating.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Locale;

/**
 * The {@code RegistrationCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for the registration operation on the web-application.
 * @author Dmitry Rafalovich
 */
public class RegistrationCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String HREF_COMMAND = "command=";
    private final static String LOGIN_COMMAND = "go_to_login";
    private static final String PATH = "path.page.login";
    private static final String LOCALE = "locale";
    private static final String LOGIN_ERROR = "loginError";
    private static final String EMAIL_ERROR = "emailError";
    private static final String SUCCESS_MESSAGE = "successMessage";
    private static final String LOGIN_KEY = "message.registration.login";
    private static final String EMAIL_KEY = "message.registration.email";
    private static final String MESSAGE_KEY = "message.registration.success";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String CONFIRM = "confirm";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the current page if an error occurred;
     *        to the login.jsp if there was no errors;
     */
    @Override
    public String execute(SessionRequestContent content) {

        Locale locale = new Locale((String) content.getSessionAttributes().get(LOCALE));
        String page = PathManager.getProperty(PATH);

        String[] name = content.getRequestParameters().get(FIRST_NAME);
        String[] lastName = content.getRequestParameters().get(LAST_NAME);
        String[] email =content.getRequestParameters().get(EMAIL);
        String[] login = content.getRequestParameters().get(LOGIN);
        String[] password = content.getRequestParameters().get(PASSWORD);
        String[] confirm = content.getRequestParameters().get(CONFIRM);

        if (name == null || lastName == null || email == null || login == null ||
                password == null || confirm == null) {
            LOG.error("All fields must be filled. Check 'required' parameter for " +
                    " <input> tags in the registration.jsp.");
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        try {

            FilmRatingRegEx.checkData(NAME, name[0]);
            FilmRatingRegEx.checkData(NAME, lastName[0]);
            FilmRatingRegEx.checkData(EMAIL, email[0]);
            FilmRatingRegEx.checkData(LOGIN, login[0]);
            FilmRatingRegEx.checkData(PASSWORD, password[0]);
            FilmRatingRegEx.checkData(PASSWORD, confirm[0]);


            if (!password[0].equals(confirm[0])) {
                LOG.error("Passwords don't match. Check js in thr registration.jsp.");
                return (String) content.getSessionAttributes().get(CURRENT_PAGE);
            }

            UserService userService = new UserService();

            if (!userService.existEmail(email[0]) && !userService.existLogin(login[0])) {

                User user = new User();
                user.setName(name[0]);
                user.setSurname(lastName[0]);
                user.setEmail(email[0]);
                user.setLogin(login[0]);
                user.setPassword(password[0]);
                userService.create(user);
                content.setAttribute(SUCCESS_MESSAGE, MessageManager.getProperty(MESSAGE_KEY, locale));
                content.addSessionAttribute(CURRENT_PAGE, CONTROLLER_PATH + HREF_COMMAND + LOGIN_COMMAND);
                return page;
            }

            if (userService.existEmail(email[0])) {
                content.setAttribute(EMAIL_ERROR, MessageManager.getProperty(EMAIL_KEY, locale));
             }

            if (userService.existLogin(login[0])) {
                content.setAttribute(LOGIN_ERROR, MessageManager.getProperty(LOGIN_KEY, locale));
            }

        } catch (ServiceException e) {
            LOG.error("Exception in RegistrationCommand", e);
        } catch (ApplicationException e) {
            LOG.error("Wrong data format from registration.jsp", e);
        }
        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }
}
