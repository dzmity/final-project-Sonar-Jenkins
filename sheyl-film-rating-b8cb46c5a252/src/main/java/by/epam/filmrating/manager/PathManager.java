package by.epam.filmrating.manager;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The {@code PathManager} class contains ResourceBundle.
 * Every value is the path to the specific jsp file.
 *  @author Dmitry Rafalovich
 *
 */
public class PathManager {

    private final static String LINK = "path";
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle(LINK, Locale.getDefault());

    private PathManager() {}

    /**
     * Gets a message for the given key from path file.
     * @param key Name of desired path
     * @return the path for the given key
     *
     */
    public static String getProperty(String key) {

       return resourceBundle.getString(key);
    }
}
