package by.epam.filmrating.command.admin.actor;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.util.AccessInspector;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.CommandAccessException;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.ActorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Comparator;
import java.util.List;

/**
 * The {@code FindActorsByAdminCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for the transition to the actors.jsp.
 * @author Dmitry Rafalovich
 */
public class FindActorsByAdminCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String PATH = "path.page.admin.actors";
    private static final String ACTORS = "actors";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the index.jsp if access denied for the current user;
     *        to the admin/actors.jsp if there was no errors;
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
            ActorService actorService = new ActorService();
            List<Actor> actors = actorService.findAll();
            Comparator<Actor> comp = Comparator.comparing(Actor::getSurname);
            actors.sort(comp);
            content.setAttribute(ACTORS, actors);
        } catch (ServiceException e) {
            LOG.error("Exception in FindActorsByAdminCommand", e);
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        String fullPath = defineLastCommand(content);
        content.addSessionAttribute(CURRENT_PAGE, fullPath);
        return PathManager.getProperty(PATH);
    }
}
