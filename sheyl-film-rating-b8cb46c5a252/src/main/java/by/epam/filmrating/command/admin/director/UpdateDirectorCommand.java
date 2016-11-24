package by.epam.filmrating.command.admin.director;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.exception.ApplicationException;
import by.epam.filmrating.manager.MessageManager;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.util.AccessInspector;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.CommandAccessException;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.entity.Director;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.DirectorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Locale;

/**
 * The {@code UpdateDirectorCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for updating {@link Director} object.
 * @author Dmitry Rafalovich
 */
public class UpdateDirectorCommand extends CreationDirector implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String DIRECTOR_ERROR = "actorError";
    private static final String DIRECTOR_KEY = "message.director.update";
    private static final String LOCALE = "locale";
    private static final String ID = "id";

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
            LOG.error("Exception in UpdateDirectorCommand. Missing Id parameter.");
            return PathManager.getProperty(INDEX_PATH);
        }

        long id =  new Long(content.getRequestParameters().get(ID)[0]);

        Locale locale = new Locale((String) content.getSessionAttributes().get(LOCALE));

        try {
            DirectorService directorService = new DirectorService();
            Director director = create(content);
            if (content.getRequestAttributes().get(COUNTRY_ERROR) != null) {
                return (String) content.getSessionAttributes().get(CURRENT_PAGE);
            }
            directorService.updateById(id, director);
        } catch (ServiceException e) {
            LOG.error("Exception in UpdateDirectorCommand", e);
        } catch (ApplicationException e) {
            content.setAttribute(DIRECTOR_ERROR, MessageManager.getProperty(DIRECTOR_KEY, locale));
            LOG.error("Wrong data format from director.jsp", e);
        }
        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }
}