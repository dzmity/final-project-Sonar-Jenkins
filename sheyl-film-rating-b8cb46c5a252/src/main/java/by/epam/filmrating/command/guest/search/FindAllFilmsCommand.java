package by.epam.filmrating.command.guest.search;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.FilmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Comparator;
import java.util.List;

/**
 * The {@code FindAllFilmsCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for displaying all {@link Film} objects
 * in a tabular format and page mode.
 * @author Dmitry Rafalovich
 */
public class FindAllFilmsCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String PATH = "path.page.films";
    private static final String COMMAND = "find_all_films";

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

        try {
            FilmService filmService = new FilmService();
            List<Film> films = filmService.findAll();
            Comparator<Film> comp = Comparator.comparing(Film::getRating).reversed().thenComparing(Film::getTitle);
            films.sort(comp);
            TablePager.fillRequestWithAttributes(content, films, COMMAND);
        } catch (ServiceException e) {
            LOG.error("Exception in FindAllFilmsCommand", e);
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        String fullPath = defineLastCommand(content);
        content.addSessionAttribute(CURRENT_PAGE, fullPath);
        return PathManager.getProperty(PATH);
    }
}
