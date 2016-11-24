package by.epam.filmrating.command.admin.film;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.exception.ApplicationException;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.util.AccessInspector;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.CommandAccessException;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.FilmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Comparator;
import java.util.List;

/**
 * The {@code AddFilmCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for creating new {@link Film} object.
 * @author Dmitry Rafalovich
 */
public class AddFilmCommand extends CreationFilm implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String PATH = "path.page.admin.films";
    private static final String FILMS = "films";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the index.jsp if access denied for the current user;
     *        to the admin/films.jsp if there was no errors;
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

        try {
            Film film = create(content);
            if (content.getRequestAttributes().get(GENRE_ERROR) != null || content.getRequestAttributes().get(ACTOR_ERROR) != null
                    || content.getRequestAttributes().get(DIRECTOR_ERROR) != null || content.getRequestAttributes().get(COUNTRY_ERROR) != null) {
                return (String) content.getSessionAttributes().get(CURRENT_PAGE);
            }
            FilmService filmService = new FilmService();
            filmService.create(film);
            List<Film> films = filmService.findAll();
            Comparator<Film> comp = Comparator.comparing(Film::getTitle);
            films.sort(comp);
            content.setAttribute(FILMS, films);
            return PathManager.getProperty(PATH);
        } catch (ServiceException e) {
            LOG.error("Exception in AddFilmCommand", e);
        } catch (ApplicationException e) {
            LOG.error("Wrong data format from film.jsp", e);
        }
        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }
}
