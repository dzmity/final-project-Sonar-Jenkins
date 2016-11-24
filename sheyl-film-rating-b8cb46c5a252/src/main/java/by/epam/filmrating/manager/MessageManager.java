package by.epam.filmrating.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The {@code MessageManager} class represents the way select
 *  messenger on right language from conformity file.
 *  @author Dmitry Rafalovich
 *
 */
public class MessageManager {

    private final static Logger LOG = LogManager.getLogger();
    private static final String LINK = "message";

    private MessageManager() {

    }

    /**
     * Gets a message for the given key on required language from message file.
     * @param key Name of desired message
     * @param locale Required language
     * @return the message for the given key or "" if key is null
     *         or no message for the given key can be found
     */
    public static String getProperty(String key, Locale locale) {

        if (key == null) {
            LOG.warn("MessageManager. Key can not be null.");
            return "";
        }

        if (locale == null) {
            LOG.warn("MessageManager. Locale can not be null.");
            locale = Locale.getDefault();
        }

        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(LINK, locale);
            return resourceBundle.getString(key);
        } catch(MissingResourceException e) {
            LOG.error("Message file not found or no object for the given key can be found");
            return "";
        }

    }
}
