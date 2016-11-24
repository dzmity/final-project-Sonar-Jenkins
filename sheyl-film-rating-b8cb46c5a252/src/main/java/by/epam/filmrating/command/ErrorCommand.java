package by.epam.filmrating.command;

import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;

/**
 * The {@code ErrorCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for the transition to the error.jsp.
 * @author Dmitry Rafalovich
 */
public class ErrorCommand implements ActionCommand {

    private static final String PATH = "path.page.error";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the error.jsp.
     */
    @Override
    public String execute(SessionRequestContent content) {

        String page = PathManager.getProperty(PATH);
        content.addSessionAttribute(CURRENT_PAGE, page);
        return page;
    }
}
