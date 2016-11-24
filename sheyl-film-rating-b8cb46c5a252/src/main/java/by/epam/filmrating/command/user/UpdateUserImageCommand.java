package by.epam.filmrating.command.user;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.CommandAccessException;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.util.AccessInspector;
import by.epam.filmrating.util.ImageServerLoader;
import by.epam.filmrating.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.Part;
import java.io.File;

/**
 * The {@code UpdateUserImageCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for changing user image.
 * @author Dmitry Rafalovich
 */
public class UpdateUserImageCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String REAL_PATH = "realPath";
    private static final String CURRENT_USER = "currentUser";
    private static final String SAVE_DIR = "images";
    private static final String FOLDER = "user";
    private static final String PART = "part";
    private static final String FILE_TYPE = ".jpeg";

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

        String applicationPath = (String) content.getSessionAttributes().get(REAL_PATH);
        String fileName = "" + currentUser.getId();
        String savePath = applicationPath + File.separator + SAVE_DIR + File.separator + FOLDER + File.separator +
                fileName + FILE_TYPE;

        try {
            UserService userService = new UserService();
            Part part = (Part) content.getRequestAttributes().get(PART);
            ImageServerLoader.load(savePath, part);
            long id = currentUser.getId();
            userService.changePictureById(id, fileName);
            content.addSessionAttribute(CURRENT_USER, userService.findById(id));

        } catch (ServiceException e) {
            LOG.error("Exception in UpdateUserImageCommand", e);
        }
        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }
}
