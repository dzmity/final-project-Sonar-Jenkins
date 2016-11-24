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
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * The {@code AddDirectorCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for creating new {@link Director} object.
 * @author Dmitry Rafalovich
 */
public class AddDirectorCommand extends CreationDirector implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String DIRECTOR_ERROR = "actorError";
    private static final String DIRECTOR_KEY = "message.director.creation";
    private static final String LOCALE = "locale";
    private static final String PATH = "path.page.admin.directors";
    private static final String DIRECTORS = "directors";
    private static final String HREF_ACTION = "/controller?";
    private static final String HREF_COMMAND = "command=";
    private static final String CURRENT_COMMAND = "find_directors_by_admin";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the index.jsp if access denied for the current user;
     *        to the admin/directors.jsp if there was no errors;
     *        to the current page if an error occurred.
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

        Locale locale = new Locale((String) content.getSessionAttributes().get(LOCALE));

        try {
            DirectorService directorService = new DirectorService();
            Director director = create(content);
            if (content.getRequestAttributes().get(COUNTRY_ERROR) != null) {
                return (String) content.getSessionAttributes().get(CURRENT_PAGE);
            }
            directorService.create(director);
            List<Director> directors = directorService.findAll();
            Comparator<Director> comp = Comparator.comparing(Director::getSurname);
            directors.sort(comp);
            content.setAttribute(DIRECTORS, directors);
            String command = HREF_ACTION + HREF_COMMAND + CURRENT_COMMAND;
            content.addSessionAttribute(CURRENT_PAGE, command);
            return PathManager.getProperty(PATH);

        } catch (ServiceException e) {
            LOG.error("Exception in AddDirectorCommand", e);
        } catch (ApplicationException e) {
            content.setAttribute(DIRECTOR_ERROR, MessageManager.getProperty(DIRECTOR_KEY, locale));
            LOG.error("Wrong data format from director.jsp", e);
        }
        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }
}