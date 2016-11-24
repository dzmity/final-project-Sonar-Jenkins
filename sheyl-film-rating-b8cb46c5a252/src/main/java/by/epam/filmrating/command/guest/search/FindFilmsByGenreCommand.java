package by.epam.filmrating.command.guest.search;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.manager.MessageManager;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.FilmService;
import by.epam.filmrating.service.GenreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Locale;

/**
 * The {@code FindFilmsByGenreCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for searching {@link Film} objects by {@code Genre} and
 * displaying their in a tabular format and page mode.
 * @author Dmitry Rafalovich
 */
public class FindFilmsByGenreCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String LOCALE = "locale";
    private static final String RESULT_ERROR = "resultError";
    private static final String RESULT_KEY = "message.film.result";
    private static final String PATH = "path.page.films";
    private static final String COMMAND = "find_films_by_genre";
    private static final String PARAMETER = "&word=";
    private static final String SEARCH_WORD = "word";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the current page if an error occurred;
     *        to the user/films.jsp if there was no errors.
     */
    @Override
    public String execute(SessionRequestContent content) {

        Locale locale = new Locale((String) content.getSessionAttributes().get(LOCALE));
        String[] genreName = content.getRequestParameters().get(SEARCH_WORD);

        if (genreName == null) {
            LOG.error("'Search' field must be filled. Check 'required' parameter for " +
                    "this <input> tag in the films.jsp.");
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        try {
            FilmService filmService = new FilmService();
            GenreService genreService = new GenreService();
            long genreId = genreService.findGenreId(genreName[0]);
            List<Film> films = filmService.findFilmByGenreId(genreId);
            if (!films.isEmpty()) {
                String command = COMMAND + PARAMETER + genreName[0];
                TablePager.fillRequestWithAttributes(content, films, command);
            } else {
                content.setAttribute(RESULT_ERROR, MessageManager.getProperty(RESULT_KEY, locale));
            }

        } catch (ServiceException e) {
            LOG.error("Exception in FindFilmsByGenreCommand", e);
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        String fullPath = defineLastCommand(content);
        content.addSessionAttribute(CURRENT_PAGE, fullPath);
        return PathManager.getProperty(PATH);
    }
}