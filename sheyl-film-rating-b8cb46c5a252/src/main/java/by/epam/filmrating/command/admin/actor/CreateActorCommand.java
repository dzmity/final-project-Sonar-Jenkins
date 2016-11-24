package by.epam.filmrating.command.admin.actor;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;

/**
 * The {@code CreateActorCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for the transition to the actor.jsp.
 * @author Dmitry Rafalovich
 */
public class CreateActorCommand implements ActionCommand {

    private static final String PATH = "path.page.admin.actor";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the admin/actor.jsp
     */
    @Override
    public String execute(SessionRequestContent content) {

        String fullPath = defineLastCommand(content);
        content.addSessionAttribute(CURRENT_PAGE, fullPath);
        return PathManager.getProperty(PATH);
    }
}
