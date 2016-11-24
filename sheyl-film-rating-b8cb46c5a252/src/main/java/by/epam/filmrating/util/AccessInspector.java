package by.epam.filmrating.util;

import by.epam.filmrating.entity.Role;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.CommandAccessException;

/**
 * The {@code AccessInspector} class checks for access to the command
 * @author Dmitry Rafalovich
 *
 */
public class AccessInspector {

    /**
     * The method checks whether the user is an administrator
     * @param user
     *        The current user of Web-application
     * @throws CommandAccessException
     *        If the {@code user} is null or {@code user} not
     *        an administrator.
     */
    public static void checkAdminAccess(User user) throws CommandAccessException {

        if (user == null) {
            throw new CommandAccessException("Access denied. Invalid command for guest.");
        }

        if (user.getRole() != Role.ADMINISTRATOR) {
            throw new CommandAccessException("Access denied. Invalid command for user.");
        }

    }

    /**
     * The method checks whether the user is logged in the system
     * @param user
     *        The current user of Web-application
     * @throws CommandAccessException
     *        If the {@code user} is not registered
     */
    public static void checkRegistration(User user) throws CommandAccessException {

        if (user == null) {
            throw new CommandAccessException("Access denied. Invalid command for guest.");
        }
    }
}
