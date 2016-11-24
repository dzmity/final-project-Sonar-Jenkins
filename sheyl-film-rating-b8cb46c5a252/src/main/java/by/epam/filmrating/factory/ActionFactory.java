package by.epam.filmrating.factory;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.command.CommandEnum;
import by.epam.filmrating.command.EmptyCommand;
import by.epam.filmrating.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *  The {@code ActionFactory} class is main participant in Factory Method Pattern.
 *  It is created object without exposing the creation logic to the client
 *  and refer to newly created object using a common interface.
 *  @author Dmitry Rafalovich
 */
public class ActionFactory {

    private static final Logger LOG = LogManager.getLogger();
    private static final String COMMAND = "command";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        concrete class extends the {@link ActionCommand}
     */
    public ActionCommand defineCommand(SessionRequestContent content) {

        ActionCommand current = new EmptyCommand();

        String[] action = content.getRequestParameters().get(COMMAND);
        if (action == null ) {
            return current;
        }

        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action[0].toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
           LOG.error("Unsupported command.", e);
        }
        return current;
    }
}
