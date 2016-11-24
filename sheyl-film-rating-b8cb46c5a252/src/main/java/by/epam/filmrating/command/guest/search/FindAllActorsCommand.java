package by.epam.filmrating.command.guest.search;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.ActorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Comparator;
import java.util.List;

/**
 * The {@code FindAllActorsCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for displaying all {@link Actor} objects
 * in a tabular format and page mode.
 * @author Dmitry Rafalovich
 */
public class FindAllActorsCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String PATH = "path.page.actors";
    private static final String COMMAND = "find_all_actors";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the current page if an error occurred;
     *        to the user/actors.jsp if there was no errors.
     */
    @Override
    public String execute(SessionRequestContent content) {

        try {
            ActorService actorService = new ActorService();
            List<Actor> actors = actorService.findAll();
            Comparator<Actor> comp = Comparator.comparing(Actor::getSurname).thenComparing(Actor::getName);
            actors.sort(comp);
            TablePager.fillRequestWithAttributes(content, actors, COMMAND);
        } catch (ServiceException e) {
            LOG.error("Exception in FindAllActorsCommand", e);
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        String fullPath = defineLastCommand(content);
        content.addSessionAttribute(CURRENT_PAGE, fullPath);
        return PathManager.getProperty(PATH);
    }
}
