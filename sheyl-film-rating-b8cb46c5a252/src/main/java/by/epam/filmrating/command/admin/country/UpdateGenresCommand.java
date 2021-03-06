package by.epam.filmrating.command.admin.country;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.util.AccessInspector;
import by.epam.filmrating.entity.Genre;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.CommandAccessException;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.service.GenreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;

/**
 * The {@code UpdateGenresCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for updating and creating {@link Genre} objects.
 * @author Dmitry Rafalovich
 */
public class UpdateGenresCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String GENRE_ID = "genreId";
    private static final String GENRE = "genre";
    private static final String NEW_GENRE = "newGenre";

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

        User user = (User) content.getSessionAttributes().get(CURRENT_USER);
        try {
            AccessInspector.checkAdminAccess(user);
        } catch (CommandAccessException e) {
            LOG.error("Access denied.", e);
            return PathManager.getProperty(INDEX_PATH);
        }

        String[] genresId = content.getRequestParameters().get(GENRE_ID);
        String[] newGenres = content.getRequestParameters().get(NEW_GENRE);
        ArrayList<Genre> genres = new ArrayList<>();
        for (String x : genresId) {
            long id = new Long(x);
            String genreName = content.getRequestParameters().get(GENRE + id)[0];
            Genre genre = new Genre();
            genre.setId(id);
            genre.setGenre(genreName);
            genres.add(genre);
        }

        try {
            GenreService genreService = new GenreService();
            genreService.update(genres);
            for (String x : newGenres) {
                if (!x.isEmpty()) {
                    Genre genre = new Genre();
                    genre.setGenre(x);
                    genreService.create(genre);
                }
            }

        } catch (ServiceException e) {
            LOG.error("Exception in UpdateGenresCommand", e);
        }
        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }
}