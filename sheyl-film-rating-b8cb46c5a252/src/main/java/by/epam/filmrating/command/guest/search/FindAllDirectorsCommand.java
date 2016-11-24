package by.epam.filmrating.command.guest.search;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.entity.Director;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.DirectorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Comparator;
import java.util.List;

/**
 * The {@code FindAllDirectorsCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for displaying all {@link Director} objects
 * in a tabular format and page mode.
 * @author Dmitry Rafalovich
 */
public class FindAllDirectorsCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private  static final String PATH = "path.page.directors";
    private  static final String COMMAND = "find_all_directors";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the current page if an error occurred;
     *        to the user/directors.jsp if there was no errors.
     */
    @Override
    public String execute(SessionRequestContent content){

        try {
            DirectorService directorService = new DirectorService();
            List<Director> directors = directorService.findAll();
            TablePager.fillRequestWithAttributes(content, directors, COMMAND);
            Comparator<Director> comp = Comparator.comparing(Director::getSurname).thenComparing(Director::getName);
            directors.sort(comp);
        } catch (ServiceException e) {
            LOG.error("Exception in FindAllDirectorsCommand", e);
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        String fullPath = defineLastCommand(content);
        content.addSessionAttribute(CURRENT_PAGE, fullPath);
        return PathManager.getProperty(PATH);
    }
}
