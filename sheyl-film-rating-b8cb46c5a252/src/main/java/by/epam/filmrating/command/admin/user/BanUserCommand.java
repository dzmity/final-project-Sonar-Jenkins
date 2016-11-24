package by.epam.filmrating.command.admin.user;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.exception.ApplicationException;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.util.AccessInspector;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.CommandAccessException;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.util.FilmRatingRegEx;
import by.epam.filmrating.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code BanUserCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for banning {@link User} object.
 * @author Dmitry Rafalovich
 */
public class BanUserCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String ID = "id";
    private static final String DAYS = "days";
    private static final String NUMBER = "number";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the index.jsp if access denied for the current user or
     *        missing 'Id' parameter in the {@code content};
     *        to the current page in other cases.
     */
    @Override
    public String execute(SessionRequestContent content) {

        User user = (User) content.getSessionAttributes().get(CURRENT_USER);
        try {
            AccessInspector.checkAdminAccess(user);
        } catch (CommandAccessException e) {
            LOG.error("Access denied.", e);
            return PathManager.getProperty(INDEX_PATH);
        }

        if (content.getRequestParameters().get(ID) == null) {
            LOG.error("Exception in BanUserCommand. Missing Id parameter.");
            return PathManager.getProperty(INDEX_PATH);
        }

        long id =  new Long(content.getRequestParameters().get(ID)[0]);

        String[] days = content.getRequestParameters().get(DAYS);

        if (days == null) {
            LOG.error("'Days' field must be filled. Check 'required' parameter for " +
                    "this <input> tag in user.jsp.");
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        try {

            FilmRatingRegEx.checkData(NUMBER, days[0]);
            int dayCount = new Integer(days[0]);
            UserService userService = new UserService();
            userService.banUserById(id, dayCount);
        } catch (ServiceException e) {
            LOG.error("Exception in BanUserCommand", e);
        } catch (ApplicationException e) {
            LOG.error("Wrong data format from  user.jsp. 'Days' must be number.", e);
        }
        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }
}