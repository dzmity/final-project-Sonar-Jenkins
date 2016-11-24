package by.epam.filmrating.command.guest.search;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.manager.MessageManager;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.util.FilmRatingRegEx;
import by.epam.filmrating.service.ActorService;
import by.epam.filmrating.service.FilmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Locale;

/**
 * The {@code FindFilmsByActorCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for searching {@link Film} objects by {@code Actor} and
 * displaying their in a tabular format and page mode.
 * @author Dmitry Rafalovich
 */
public class FindFilmsByActorCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String LOCALE = "locale";
    private static final String PARAMETER_COMMAND = "command=";
    private static final String SEARCH_ERROR = "searchError";
    private static final String ACTOR_KEY = "message.film.findByActor";
    private static final String RESULT_ERROR = "resultError";
    private static final String RESULT_KEY = "message.film.result";
    private static final String PATH = "path.page.films";
    private static final String COMMAND = "find_films_by_actor";
    private static final String FIND_ALL_FILMS_COMMAND = "find_all_films";
    private static final String PARAMETER = "&word=";
    private static final String SEARCH_WORD = "word";
    private static final int NOT_FOUND = -1;

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the current page if an error occurred;
     *        command address 'find_all_films' if actor data has wrong format;
     *        to the user/films.jsp if there was no errors.
     */
    @Override
    public String execute(SessionRequestContent content) {

        Locale locale = new Locale((String) content.getSessionAttributes().get(LOCALE));
        FilmService filmService = new FilmService();
        ActorService actorService = new ActorService();
        String[] actor = content.getRequestParameters().get(SEARCH_WORD);

        if (actor == null) {
            LOG.error("'Search' field must be filled. Check 'required' parameter for " +
                    "this <input> tag in the films.jsp.");
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        String[] actorParameters = actor[0].trim().split(FilmRatingRegEx.GREEDY_SPACES);

        try {

            if (actorParameters.length >= 2) {
                StringBuilder actorSurname = new StringBuilder();
                for (int i = 1; i < actorParameters.length; i++) {
                    actorSurname.append(actorParameters[i]);
                    actorSurname.append(" ");
                }
                long actorId = actorService.findActorId(actorParameters[0], actorSurname.toString().trim());
                if (actorId != NOT_FOUND) {
                    String command = COMMAND + PARAMETER + actorParameters[0] + " " + actorSurname.toString();
                    List<Film> films = filmService.findFilmByActorId(actorId);
                    TablePager.fillRequestWithAttributes(content, films, command);
                } else {
                    content.setAttribute(RESULT_ERROR, MessageManager.getProperty(RESULT_KEY, locale));
                }
            } else {
                content.setAttribute(SEARCH_ERROR, MessageManager.getProperty(ACTOR_KEY, locale));
                return CONTROLLER_PATH + PARAMETER_COMMAND + FIND_ALL_FILMS_COMMAND;
            }
        } catch (ServiceException e) {
            LOG.error("Exception in FindFilmsByActorCommand", e);
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        String fullPath = defineLastCommand(content);
        content.addSessionAttribute(CURRENT_PAGE, fullPath);
        return PathManager.getProperty(PATH);
    }
}
