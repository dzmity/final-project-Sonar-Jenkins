package by.epam.filmrating.command.admin.film;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.exception.ApplicationException;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.util.AccessInspector;
import by.epam.filmrating.exception.CommandAccessException;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.entity.*;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code UpdateFilmCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for updating {@link Film} object.
 * @author Dmitry Rafalovich
 */
public class UpdateFilmCommand extends CreationFilm implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
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
            LOG.error("Exception in UpdateFilmCommand. Missing Id parameter.");
            return PathManager.getProperty(INDEX_PATH);
        }

        long id =  new Long(content.getRequestParameters().get(ID)[0]);

        try {
            Film film = create(content);
            if (content.getRequestAttributes().get(GENRE_ERROR) != null || content.getRequestAttributes().get(ACTOR_ERROR) != null
                    || content.getRequestAttributes().get(DIRECTOR_ERROR) != null || content.getRequestAttributes().get(COUNTRY_ERROR) != null) {
                return (String) content.getSessionAttributes().get(CURRENT_PAGE);
            }
            FilmService filmService = new FilmService();
            filmService.updateById(id, film);

        } catch (ServiceException e) {
            LOG.error("Exception in UpdateFilmCommand", e);
        } catch (ApplicationException e) {
            LOG.error("Wrong data format from film.jsp", e);
        }

        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }

}
