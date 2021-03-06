package by.epam.filmrating.command.admin.user;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.util.AccessInspector;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.CommandAccessException;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.manager.MessageManager;
import by.epam.filmrating.entity.Status;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.util.ActionEvaluator;
import by.epam.filmrating.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Locale;

/**
 * The {@code UpgradeStatusCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for upgrading status for {@link User} object.
 * @author Dmitry Rafalovich
 */
public class UpgradeStatusCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String ID = "id";
    private static final String STATUS = "status";
    private static final String STATUS_ERROR = "statusError";
    private static final String LOCALE = "locale";
    private static final String STATUS_KEY = "message.user.status";

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
            LOG.error("Exception in UpgradeStatusCommand. Missing Id parameter.");
            return PathManager.getProperty(INDEX_PATH);
        }

        long id =  new Long(content.getRequestParameters().get(ID)[0]);

        Locale locale = new Locale((String) content.getSessionAttributes().get(LOCALE));


        try {
            UserService userService = new UserService();

            String[] statusField = content.getRequestParameters().get(STATUS);
            if (statusField == null) {
                LOG.error("'status' fields must be filled. Check 'required' parameter for " +
                        "this <input> tag in user.jsp.");
                return (String) content.getSessionAttributes().get(CURRENT_PAGE);
            }

            Status status = Status.valueOf(statusField[0].toUpperCase());
            switch (status) {
                case BEGINNER:
                    userService.updatedMarkById(id, 0);
                    break;
                case EXPERIENCED:
                    userService.updatedMarkById(id, ActionEvaluator.BEGINNER_MAX_SUM + 1);
                    break;
                case MASTER:
                    userService.updatedMarkById(id, ActionEvaluator.EXPERIENCED_MAX_SUM + 1);
                    break;
                default:
                    LOG.error("Unreachable statement in UpgradeStatusCommand.");
                    break;
            }

        } catch (ServiceException e) {
            LOG.error("Exception in UpgradeStatusCommand", e);
        } catch (IllegalArgumentException e) {
            content.setAttribute(STATUS_ERROR,  MessageManager.getProperty(STATUS_KEY, locale));
        }
        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }
}