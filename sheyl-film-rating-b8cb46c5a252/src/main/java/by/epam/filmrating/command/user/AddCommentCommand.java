package by.epam.filmrating.command.user;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.exception.CommandAccessException;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.entity.Comment;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.util.AccessInspector;
import by.epam.filmrating.util.ActionEvaluator;
import by.epam.filmrating.service.CommentService;
import by.epam.filmrating.service.FilmService;
import by.epam.filmrating.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code AddCommentCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for creating new {@link Comment} object for
 * the film.
 * @author Dmitry Rafalovich
 */
public class AddCommentCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String PATH = "path.page.film";
    private static final String FILM = "film";
    private static final String COMMENT = "comment";
    private static final String FILM_ID = "filmId";
    private static final String USER_ID = "userId";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the index.jsp if access denied for the current user;
     *        to the user/film.jsp if there was no errors;
     *        to the current page if an error occurred.
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
        String textField[] = content.getRequestParameters().get(COMMENT);

        if (textField == null) {
            LOG.error("'Comment' field must be filled. Check 'required' parameter for " +
                    "this <input> tag in film.jsp.");
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        String text = textField[0];
        String filmId = content.getRequestParameters().get(FILM_ID)[0];
        long id = Integer.parseInt(filmId);
        String userId = content.getRequestParameters().get(USER_ID)[0];

        try {
            CommentService commentService = new CommentService();
            FilmService filmService = new FilmService();
            Comment comment = new Comment();
            User user = new User();
            user.setId(Integer.parseInt(userId));
            Film film = new Film();
            film.setId(id);
            comment.setText(text);
            comment.setUser(user);
            comment.setFilm(film);
            commentService.create(comment);
            film = filmService.findById(id);
            content.setAttribute(FILM, film);
            UserService userService = new UserService();
            userService.addMarkById(currentUser.getId(), ActionEvaluator.COMMENT);
            return PathManager.getProperty(PATH);

        } catch (ServiceException e) {
            LOG.error("Exception in AddCommentCommand", e);
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }
    }
}
