package by.epam.filmrating.tag;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * The {@code AdminFooterTag} class represents the custom function {@code format}
 * in {@code customtags} library used in jsp-fills
 * @author Dmitry Rafalovich
 *
 */
public class TimerFunction {

    private final static String COLON = ":";

    private TimerFunction() {
    }

    /**
     * The method represents the function that formats {@code localDateTime} parameter
     * to the required form
     * @param localDateTime
     *        date-time data
     * @return date-time string in required format
     */
    public static String formatToTimer(LocalDateTime localDateTime) {

        LocalDateTime now = LocalDateTime.now();
        Duration between = Duration.between(now, localDateTime);
        long seconds = between.getSeconds();
        int days = (int) (seconds / (60 * 60 * 24));
        int ost = (int) (seconds % (60 * 60 * 24));
        int hour = (ost / (60 * 60));
        ost =  (ost % (60 * 60));
        int min = (ost / (60));
        int sec = (ost % (60));
        return days + COLON + hour + COLON + min + COLON + sec;
    }
}
