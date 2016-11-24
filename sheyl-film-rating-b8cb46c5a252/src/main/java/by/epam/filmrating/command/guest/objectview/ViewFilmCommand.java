package by.epam.filmrating.command.guest.objectview;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.FilmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code ViewFilmCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for displaying information about the {@link Film} object.
 * @author Dmitry Rafalovich
 */
public class ViewFilmCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String PATH = "path.page.film";
    private static final String ID = "id";
    private static final String FILM = "film";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the index.jsp if missing 'Id' parameter in the {@code content};
     *        to the user/actor.jsp if there was no errors;
     *        to the current page if an error occurred.
     */
    @Override
    public String execute(SessionRequestContent content){

        if (content.getRequestParameters().get(ID) == null) {
            LOG.error("Exception in ViewFilmCommand. Missing Id parameter.");
            return PathManager.getProperty(INDEX_PATH);
        }

        long id =  new Long(content.getRequestParameters().get(ID)[0]);

        try {
            FilmService filmService = new FilmService();
            Film film = filmService.findById(id);
            content.setAttribute(FILM, film);
        } catch (ServiceException e) {
            LOG.error("Exception in ViewFilmCommand", e);
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        String fullPath = defineLastCommand(content);
        content.addSessionAttribute(CURRENT_PAGE, fullPath);
        return PathManager.getProperty(PATH);
    }
}
