package by.epam.filmrating.command;

import by.epam.filmrating.servlet.SessionRequestContent;

/**
 * The {@code ActionCommand} class is participant in Command Pattern.
 * The class defines the main object-command interface.
 * @author Dmitry Rafalovich
 */
public interface ActionCommand {

    String CODE = "code";
    String DAYS = "days";
    String INDEX_PATH = "path.page.index";
    String CURRENT_PAGE = "currentPage";
    String CURRENT_USER = "currentUser";
    String COMMAND = "command";
    String CONTROLLER_PATH = "/controller?";
    String EQUAL_SIGN = "=";
    String AND_SIGN = "&";

    /**
     * The method method executes request that came to the controller.
     * This method fills the {@code content} the necessary data that will be subsequently
     * included in the request object sent from the server.
     * @param content
     *        object storing the necessary information from the request
     * @return address of the page or command address which will be sent response to the request
     */
    String execute(SessionRequestContent content);

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return current command that came to the controller
     */
    default String defineLastCommand(SessionRequestContent content) {

        StringBuilder fullPath = new StringBuilder(CONTROLLER_PATH);
        String command = content.getRequestParameters().get(COMMAND)[0];
        fullPath.append(COMMAND);
        fullPath.append(EQUAL_SIGN);
        fullPath.append(command);
        fullPath.append(AND_SIGN);

        for (String parameter : content.getRequestParameters().keySet()) {
            if (CODE.equals(parameter) || DAYS.equals(parameter) || COMMAND.equals(parameter)) {
                continue;
            }
            fullPath.append(parameter);
            fullPath.append(EQUAL_SIGN);
            fullPath.append(content.getRequestParameters().get(parameter)[0]);
            fullPath.append(AND_SIGN);
        }
        fullPath.deleteCharAt(fullPath.length() - 1);
        return fullPath.toString();
    }
}
