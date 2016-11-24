package by.epam.filmrating.command.guest.search;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.manager.MessageManager;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.util.FilmRatingRegEx;
import by.epam.filmrating.service.DirectorService;
import by.epam.filmrating.service.FilmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Locale;

/**
 * The {@code FindFilmsByDirectorCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for searching {@link Film} objects by {@code Director} and
 * displaying their in a tabular format and page mode.
 * @author Dmitry Rafalovich
 */
public class FindFilmsByDirectorCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String LOCALE = "locale";
    private static final String SEARCH_ERROR = "searchError";
    private static final String DIRECTOR_KEY = "message.film.findByDirector";
    private static final String PARAMETER_COMMAND = "command=";
    private static final String RESULT_ERROR = "resultError";
    private static final String RESULT_KEY = "message.film.result";
    private static final String FIND_ALL_FILMS_COMMAND = "find_all_films";
    private static final String PATH = "path.page.films";
    private static final String COMMAND = "find_films_by_director";
    private static final String PARAMETER = "&word=";
    private static final String SEARCH_WORD = "word";
    private static final int NOT_FOUND = -1;

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the current page if an error occurred;
     *        command address 'find_all_films' if director data has wrong format;
     *        to the user/films.jsp if there was no errors.
     */
    @Override
    public String execute(SessionRequestContent content) {

        Locale locale = new Locale((String) content.getSessionAttributes().get(LOCALE));

        String[] director = content.getRequestParameters().get(SEARCH_WORD);

        if (director == null) {
            LOG.error("'Search' field must be filled. Check 'required' parameter for " +
                    "this <input> tag in the films.jsp.");
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        String[] directorParameters = director[0].trim().split(FilmRatingRegEx.GREEDY_SPACES);

        try {
            FilmService filmService = new FilmService();
            DirectorService directorService = new DirectorService();
            if (directorParameters.length == 2) {
                long directorId = directorService.findDirectorId(directorParameters[0], directorParameters[1]);
                if (directorId != NOT_FOUND) {
                    String command = COMMAND + PARAMETER + directorParameters[0] + " " + directorParameters[1];
                    List<Film> films = filmService.findFilmByDirectorId(directorId);
                    TablePager.fillRequestWithAttributes(content, films, command);
                } else {
                    content.setAttribute(RESULT_ERROR, MessageManager.getProperty(RESULT_KEY, locale));
                }
            } else {
                content.setAttribute(SEARCH_ERROR, MessageManager.getProperty(DIRECTOR_KEY, locale));
                return CONTROLLER_PATH + PARAMETER_COMMAND + FIND_ALL_FILMS_COMMAND;
            }
        } catch (ServiceException e) {
            LOG.error("Exception in FindFilmsByDirectorCommand", e);
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        String fullPath = defineLastCommand(content);
        content.addSessionAttribute(CURRENT_PAGE, fullPath);
        return PathManager.getProperty(PATH);
    }
}