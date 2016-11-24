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

/**
 * The {@code ChangeActorCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible  for the transition to the actor.jsp for
 * updating {@class Actor} object.
 * @author Dmitry Rafalovich
 */
public class ChangeActorCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String PATH = "path.page.admin.actor";
    private static final String ID = "id";
    private static final String ACTOR = "actor";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the index.jsp if access denied for the current user or
     *        missing 'Id' parameter in the {@code content};
     *        to the admin/actor.jsp if there was no errors;
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

        if (content.getRequestParameters().get(ID) == null) {
            LOG.error("Exception in ChangeActorCommand. Missing Id parameter.");
            return PathManager.getProperty(INDEX_PATH);
        }

        long id =  new Long(content.getRequestParameters().get(ID)[0]);

        try {
            ActorService actorService = new ActorService();
            Actor actor = actorService.findById(id);
            content.setAttribute(ACTOR, actor);
        } catch (ServiceException e) {
            LOG.error("Exception in ChangeActorCommand", e);
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        String fullPath = defineLastCommand(content);
        content.addSessionAttribute(CURRENT_PAGE, fullPath);
        return PathManager.getProperty(PATH);
    }
}
