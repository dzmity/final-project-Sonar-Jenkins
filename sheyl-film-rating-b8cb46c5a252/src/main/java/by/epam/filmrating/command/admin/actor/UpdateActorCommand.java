package by.epam.filmrating.command.admin.actor;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.manager.MessageManager;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.util.AccessInspector;
import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.ApplicationException;
import by.epam.filmrating.exception.CommandAccessException;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.service.ActorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Locale;

/**
 * The {@code UpdateActorCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for updating {@link Actor} object.
 * @author Dmitry Rafalovich
 */
public class UpdateActorCommand extends CreationActor implements ActionCommand {

    private static final String ACTOR_ERROR = "actorError";
    private static final String ACTOR_KEY = "message.actor.update";
    private static final String LOCALE = "locale";
    private static final Logger LOG = LogManager.getLogger();
    private static final String ID = "id";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the index.jsp if access denied for the current user or
     *        missing 'Id' parameter in the {@code content};
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

        if (content.getRequestParameters().get(ID) == null) {
            LOG.error("Exception in UpdateActorCommand. Missing Id parameter.");
            return PathManager.getProperty(INDEX_PATH);
        }

        long id =  new Long(content.getRequestParameters().get(ID)[0]);

        Locale locale = new Locale((String) content.getSessionAttributes().get(LOCALE));

        try {
            ActorService actorService = new ActorService();
            Actor actor = create(content);
            actorService.updateById(id, actor);
        } catch (ServiceException e) {
            LOG.error("Exception in UpdateActorCommand", e);
        } catch (ApplicationException e) {
            content.setAttribute(ACTOR_ERROR, MessageManager.getProperty(ACTOR_KEY, locale));
            LOG.error("Wrong data format from actor.jsp", e);
        }
        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }
}
