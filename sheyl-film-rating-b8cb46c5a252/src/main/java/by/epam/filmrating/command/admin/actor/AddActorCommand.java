package by.epam.filmrating.command.admin.actor;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.manager.MessageManager;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.util.AccessInspector;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.CommandAccessException;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.exception.ApplicationException;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.ActorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * The {@code AddActorCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for creating new {@link Actor} object.
 * @author Dmitry Rafalovich
 */
public class AddActorCommand extends CreationActor implements ActionCommand {

    private static final String ACTOR_ERROR = "actorError";
    private static final String ACTOR_KEY = "message.actor.creation";
    private static final String LOCALE = "locale";
    private static final Logger LOG = LogManager.getLogger();
    private static final String PATH = "path.page.admin.actors";
    private static final String ACTORS = "actors";
    private static final String HREF_ACTION = "/controller?";
    private static final String HREF_COMMAND = "command=";
    private static final String CURRENT_COMMAND = "find_actors_by_admin";


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

        Locale locale = new Locale((String) content.getSessionAttributes().get(LOCALE));

        try {
            ActorService actorService = new ActorService();
            Actor actor = create(content);
            actorService.create(actor);
            List<Actor> actors = actorService.findAll();
            Comparator<Actor> comp = Comparator.comparing(Actor::getSurname);
            actors.sort(comp);
            content.setAttribute(ACTORS, actors);
            String command = HREF_ACTION + HREF_COMMAND + CURRENT_COMMAND;
            content.addSessionAttribute(CURRENT_PAGE, command);
            return PathManager.getProperty(PATH);

        } catch (ServiceException e) {
            LOG.error("Exception in AddActorCommand", e);
        } catch (ApplicationException e) {
            content.setAttribute(ACTOR_ERROR, MessageManager.getProperty(ACTOR_KEY, locale));
            LOG.error("Wrong data format from actor.jsp. Check regex on the page. ", e);
        }
        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }
}
