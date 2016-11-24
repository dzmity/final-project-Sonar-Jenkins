package by.epam.filmrating.command;

import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.FilmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;

/**
 * The {@code StartCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for the transition to the start.jsp and displaying
 * top films by rating.
 * @author Dmitry Rafalovich
 */
public class StartCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final int TOP_SIZE = 10;
    private static final String PATH = "path.page.start";
    private static final String PATH_ERROR = "path.page.error";
    private static final String TOP_FILMS = "topFilms";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the error.jsp if an error occurred.
     *        to the start.jsp in other cases.
     */
    @Override
    public String execute(SessionRequestContent content) {

        try {
            FilmService filmService = new FilmService();
            List<Film> films = filmService.findAll();
            Comparator<Film> comp = Comparator.comparing(Film::getRating).thenComparingInt(Film::getMarksCount).reversed().thenComparing(Film::getTitle);
            films.sort(comp);
            HashMap<Integer, Film> topFilms = new HashMap<>();
            for (int i = 1; i <= TOP_SIZE; i++) {
                topFilms.put(i, films.get(i - 1));
            }
            content.setAttribute(TOP_FILMS, topFilms);

        } catch (ServiceException e) {
            LOG.error("Exception in StartCommand", e);
            return PathManager.getProperty(PATH_ERROR);
        }
        String fullPath = defineLastCommand(content);
        content.addSessionAttribute(CURRENT_PAGE, fullPath);
        return PathManager.getProperty(PATH);
    }
}
