package by.epam.filmrating.util;

import by.epam.filmrating.exception.ApplicationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code FilmRatingRegEx} class stores regular expressions for input data
 * from web-pages and validates this data.
 * @author Dmitry Rafalovich
 *
 */
public class FilmRatingRegEx {

    public static final String GREEDY_SPACES = "\\s+";
    private static final String PASSW_REGEX = "^[-ёЁа-яА-Я\\w\\s]{6,20}$";
    private static final String NAME_REGEX = "^([ёЁа-яА-Я]+|[a-zA-Z]+)$";
    private static final String LOGIN_REGEX = "^[a-zA-ZёЁа-яА-Я][ёЁа-яА-Я\\w-]{2,9}$";
    private static final String EMAIL_REGEX = "([\\w\\.\\-]+)?\\w+@[\\w-]+(\\.\\w+)+";
    private static final String ID_REGEX = "^\\d{1,4}$";
    private static final String NUMBER_REGEX = "^\\d+$";
    private static final String CODE_REGEX = "^\\d{1,19}$";
    private static final String YEAR_REGEX = "(^19|20)\\d\\d$";
    private static final String DATE_REGEX = "(^19|20)\\d\\d-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\\d|3[0-1])$";

    private static final String LOGIN_FIELD = "login";
    private static final String PASS_FIELD = "password";
    private static final String NAME_FIELD = "name";
    private static final String EMAIL_FIELD = "email";
    private static final String ID_FIELD = "id";
    private static final String NUMBER_FIELD = "number";
    private static final String YEAR_FIELD = "year";
    private static final String DATE_FIELD = "date";
    private static final String CODE_FIELD = "code";
    /**
     * Method defines the way of data validation.
     * @param field
     *        string value, which use for find right regex expression.
     * @param data
     *        string value, which have to be validated.
     * @throws ApplicationException
     *        if (@code data} has wrong format;
     *        if string (@code field} has unknown value.
     *        if (@code data} == null;
     */
    public static void checkData(String field, String data) throws ApplicationException{
        String regexPattern;

        if (data == null) {
            throw new ApplicationException("Data == null!!");
        }

        switch (field) {
            case LOGIN_FIELD:
                regexPattern = LOGIN_REGEX;
                break;
            case PASS_FIELD:
                regexPattern = PASSW_REGEX;
                break;
            case NAME_FIELD:
                regexPattern = NAME_REGEX;
                break;
            case EMAIL_FIELD:
                regexPattern = EMAIL_REGEX;
                break;
            case ID_FIELD:
                regexPattern = ID_REGEX;
                break;
            case NUMBER_FIELD:
                regexPattern = NUMBER_REGEX;
                break;
            case CODE_FIELD:
                regexPattern = CODE_REGEX;
                break;
            case YEAR_FIELD:
                regexPattern = YEAR_REGEX;
                break;
            case DATE_FIELD:
                regexPattern = DATE_REGEX;
                break;
            default:
                throw new ApplicationException("Wrong field name from command");
        }

        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(data);
        if (!matcher.matches()) {
            throw new ApplicationException("Wrong field format from jsp.");
        }
    }
}

