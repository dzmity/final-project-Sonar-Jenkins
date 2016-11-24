package by.epam.filmrating.command;

import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;

/**
 * The {@code EmptyCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for the transition to the index.jsp.
 * @author Dmitry Rafalovich
 */
public class EmptyCommand implements ActionCommand {

    private static final String PATH = "path.page.index";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the index.jsp.
     */
    @Override
    public String execute(SessionRequestContent content) {

        String page = PathManager.getProperty(PATH);
        content.addSessionAttribute(CURRENT_PAGE, page);
        return page;
    }
}
