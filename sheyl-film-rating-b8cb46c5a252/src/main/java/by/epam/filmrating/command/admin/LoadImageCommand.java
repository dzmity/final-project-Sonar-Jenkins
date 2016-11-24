package by.epam.filmrating.command.admin;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.CommandAccessException;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.util.AccessInspector;
import by.epam.filmrating.util.ImageServerLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.Part;
import java.io.File;

/**
 * The {@code LoadImageCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for uploading images on the server.
 * @author Dmitry Rafalovich
 */
public class LoadImageCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String REAL_PATH = "realPath";
    private static final String SAVE_DIR = "images";
    private static final String FOLDER = "folder";
    private static final String PART = "part";
    private static final String NAME = "name";
    private static final String FILE_TYPE = ".jpg";

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

        User user = (User) content.getSessionAttributes().get(CURRENT_USER);
        try {
            AccessInspector.checkAdminAccess(user);
        } catch (CommandAccessException e) {
            LOG.error("Access denied.", e);
            return PathManager.getProperty(INDEX_PATH);
        }

        String applicationPath = (String) content.getSessionAttributes().get(REAL_PATH);
        String[] fileName = content.getRequestParameters().get(NAME);
        String[] folder = content.getRequestParameters().get(FOLDER);
        if (fileName == null || folder == null) {
            LOG.error("Check  parameters for <input> tags in user.jsp.");
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        String savePath = applicationPath + File.separator + SAVE_DIR + File.separator + folder[0] + File.separator +
                fileName[0] + FILE_TYPE;

        if (content.getRequestAttributes().get(PART) != null) {
            Part part = (Part) content.getRequestAttributes().get(PART);
            ImageServerLoader.load(savePath, part);
        }

        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }
}