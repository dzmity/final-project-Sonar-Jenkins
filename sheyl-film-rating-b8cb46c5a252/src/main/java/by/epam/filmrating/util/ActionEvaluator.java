package by.epam.filmrating.util;

/**
 * The {@code ActionEvaluator} class stores the values of the action assessment
 * produced by registered user
 * @author Dmitry Rafalovich
 *
 */
public class ActionEvaluator {

    /** The scoring of the film*/
    public static final int MARK = 1;

    /** Writing a comment for the film*/
    public static final int COMMENT = 2;

    /** Ban comment by administrator */
    public static final int BAN_COMMENT = -3;

    /** Ban user by administrator */
    public static final int BAN_USER = -5;

    /**
     * The value of the user's rating, after which his status rises to EXPERIENCED
     * @see by.epam.filmrating.entity.Status
     */
    public static final int BEGINNER_MAX_SUM = 99;

    /**
     * The value of the user's rating, after which his status rises to MASTER
     * @see by.epam.filmrating.entity.Status
     */
    public static final int EXPERIENCED_MAX_SUM = 199;

}
