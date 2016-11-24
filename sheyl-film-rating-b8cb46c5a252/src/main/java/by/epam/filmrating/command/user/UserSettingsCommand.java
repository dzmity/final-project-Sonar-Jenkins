package by.epam.filmrating.command.user;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.CommandAccessException;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.service.UserService;
import by.epam.filmrating.util.AccessInspector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code UserSettingsCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for the transition to the settings.jsp.
 * @author Dmitry Rafalovich
 */
public class UserSettingsCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private final static String PATH = "path.page.settings";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the index.jsp if access denied for the current user;
     *        to the settings.jsp in other cases.
     */
    @Override
    public String execute(SessionRequestContent content) {

        User currentUser = (User) content.getSessionAttributes().get(CURRENT_USER);
        try {
            AccessInspector.checkRegistration(currentUser);
        } catch (CommandAccessException e) {
            LOG.error("Access denied.", e);
            return PathManager.getProperty(INDEX_PATH);
        }

        try {
            UserService userService = new UserService();
            userService.checkBanForAll();
            User user = userService.findById(currentUser.getId());
            content.addSessionAttribute(CURRENT_USER, user);
        } catch (ServiceException e) {
            LOG.error("Exception in ban checking operation.", e);
        }

        String fullPath = defineLastCommand(content);
        content.addSessionAttribute(CURRENT_PAGE, fullPath);
        return PathManager.getProperty(PATH);
    }
}
