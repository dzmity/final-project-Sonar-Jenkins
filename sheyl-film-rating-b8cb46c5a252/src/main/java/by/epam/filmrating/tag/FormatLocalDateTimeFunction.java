package by.epam.filmrating.tag;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * The {@code AdminFooterTag} class represents the custom function {@code format}
 * in {@code customtags} library used in jsp-fills
 * @author Dmitry Rafalovich
 *
 */
public class FormatLocalDateTimeFunction {

    private FormatLocalDateTimeFunction() {
    }

    /**
     * The method represents the function that formats {@code localDateTime} parameter
     * to the form specified in {@code localDateTime}
     * @param localDateTime
     *        date-time data
     * @param pattern
     *        required format of date-time data
     * @param locale
     *        required language
     * @return date-time string in required format
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern, String locale) {

        Locale loc = new Locale(locale);
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern, loc));
    }
}
