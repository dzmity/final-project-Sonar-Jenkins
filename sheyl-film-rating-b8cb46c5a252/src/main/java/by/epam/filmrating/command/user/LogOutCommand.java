package by.epam.filmrating.command.user;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * The {@code LogOutCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for sign out user in web-application.
 * @author Dmitry Rafalovich
 */
public class LogOutCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private final static String PATH = "path.page.index";
    private static final String REQUEST = "request";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the index.jsp.
     */
    @Override
    public String execute(SessionRequestContent content) {

        try {
            UserService userService = new UserService();
            userService.checkBanForAll();
        } catch (ServiceException e) {
            LOG.error("Exception in ban checking operation.", e);
        }

        HttpServletRequest request = (HttpServletRequest) content.getRequestAttributes().get(REQUEST);
        request.getSession().invalidate();
        return PathManager.getProperty(PATH);
    }
}
