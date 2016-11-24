package by.epam.filmrating.command.admin.user;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.exception.CommandAccessException;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.UserService;
import by.epam.filmrating.util.AccessInspector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code UserInfoCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for the transition to the user.jsp.
 * @author Dmitry Rafalovich
 */
public class UserInfoCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String PATH = "path.page.admin.user";
    private static final String ID = "id";
    private static final String USER = "user";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the index.jsp if access denied for the current user or
     *        missing 'Id' parameter in the {@code content};
     *        to the current page if an error occurred.
     *        to the user.jsp if there was no errors;
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
            AccessInspector.checkAdminAccess(currentUser);
        } catch (CommandAccessException e) {
            LOG.error("Access denied.", e);
            return PathManager.getProperty(INDEX_PATH);
        }

        if (content.getRequestParameters().get(ID) == null) {
            LOG.error("Exception in UserInfoCommand. Missing Id parameter.");
            return PathManager.getProperty(INDEX_PATH);
        }

        long id =  new Long(content.getRequestParameters().get(ID)[0]);
        User user;

        try {
            UserService userService = new UserService();
            user = userService.findById(id);
        } catch(ServiceException e) {
            LOG.error("Exception in UserInfoCommand", e);
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        content.setAttribute(USER, user);
        String fullPath = defineLastCommand(content);
        content.addSessionAttribute(CURRENT_PAGE, fullPath);
        return PathManager.getProperty(PATH);
    }
}
