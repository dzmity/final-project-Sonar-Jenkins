package by.epam.filmrating.command.guest;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;

/**
 * The {@code SignUpCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for the transition to the registration.jsp.
 * @author Dmitry Rafalovich
 */
public class SignUpCommand implements ActionCommand {

    private final static String PATH = "path.page.registration";
    private final static String BEFORE_LOGIN = "beforeLogin";
    private final static String LOGIN_COMMAND = "go_to_login";
    private final static String SIGN_UP_COMMAND = "sign_up";
    private final static String HREF_ACTION = "/controller?";
    private final static String HREF_COMMAND = "command=";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the registration.jsp
     */
    @Override
    public String execute(SessionRequestContent content) {

        String loginCommand = HREF_ACTION + HREF_COMMAND + LOGIN_COMMAND;
        String registrationCommand = HREF_ACTION + HREF_COMMAND + SIGN_UP_COMMAND;

        String lastCommand = (String) content.getSessionAttributes().get(CURRENT_PAGE);
        if (!lastCommand.startsWith(loginCommand) && !lastCommand.startsWith(registrationCommand)) {
            content.addSessionAttribute(BEFORE_LOGIN, lastCommand);
        }

        String fullPath = defineLastCommand(content);
        content.addSessionAttribute(CURRENT_PAGE, fullPath);
        return PathManager.getProperty(PATH);
    }
}
