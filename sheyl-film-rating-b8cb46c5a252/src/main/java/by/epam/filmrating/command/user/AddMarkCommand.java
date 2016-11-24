package by.epam.filmrating.command.user;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.exception.CommandAccessException;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.MessageManager;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.entity.Mark;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.util.AccessInspector;
import by.epam.filmrating.util.ActionEvaluator;
import by.epam.filmrating.service.MarkService;
import by.epam.filmrating.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Locale;

/**
 * The {@code AddMarkCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for creating new {@link Mark} object for
 * the film.
 * @author Dmitry Rafalovich
 */
public class AddMarkCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String RATING = "rating";
    private static final String FILM_ID = "filmId";
    private static final String CURRENT_USER = "currentUser";
    private static final String MARK_ERROR = "markError";
    private static final String MARK_KEY = "message.film.mark";
    private static final String LOCALE = "locale";

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

        User currentUser = (User) content.getSessionAttributes().get(CURRENT_USER);
        try {
            AccessInspector.checkRegistration(currentUser);
        } catch (CommandAccessException e) {
            LOG.error("Access denied.", e);
            return PathManager.getProperty(INDEX_PATH);
        }

        Locale locale = new Locale((String) content.getSessionAttributes().get(LOCALE));
        long filmId = new Long(content.getRequestParameters().get(FILM_ID)[0]);
        int rating = Integer.parseInt(content.getRequestParameters().get(RATING)[0]);
        Mark mark = new Mark();
        mark.setMark(rating);
        Film film = new Film();
        film.setId(filmId);
        mark.setFilm(film);
        mark.setUser(currentUser);

        try {
            MarkService markService = new MarkService();
            markService.create(mark);
            UserService userService = new UserService();
            userService.addMarkById(currentUser.getId(), ActionEvaluator.MARK);
        } catch (ServiceException e) {
            LOG.error("Exception in AddMarkCommand", e);
            content.setAttribute(MARK_ERROR, MessageManager.getProperty(MARK_KEY, locale));
        }

        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }
}
