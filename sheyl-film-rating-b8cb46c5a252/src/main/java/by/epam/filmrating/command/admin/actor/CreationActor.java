package by.epam.filmrating.command.admin.actor;

import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.exception.ApplicationException;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.util.FilmRatingRegEx;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The {@code CreationActor} class is a helper class.
 * The class is responsible for creation {@link Actor} object.
 * @author Dmitry Rafalovich
 */
 class CreationActor {

    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String HEIGHT = "height";
    private static final String NUMBER = "number";
    private static final String DOB = "dob";
    private static final String DATE = "date";
    private static final String PHOTO_PATH = "photo";
    private static final String FORMAT = "yyyy-MM-dd";

    /**
     * Tne method is responsible for actor creation.
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        an instance of {@link Actor} class
     * @throws ApplicationException
     *        if missing necessary parameters in the {@code content} or
     *        this parameters have not been validated.
     */
    Actor create(SessionRequestContent content) throws ApplicationException {

        String[] name = content.getRequestParameters().get(NAME);
        String[] surname = content.getRequestParameters().get(SURNAME);
        String[] sHeight = content.getRequestParameters().get(HEIGHT);
        String[] sDate = content.getRequestParameters().get(DOB);
        String[] photoPath = content.getRequestParameters().get(PHOTO_PATH);

        if (name == null || surname == null || sHeight == null || sDate == null || photoPath == null) {
            throw new ApplicationException("All fields must be filled. Check 'required' parameter for " +
                    "all <input> tags in actor.jsp.");
        }

        FilmRatingRegEx.checkData(NAME, name[0].trim());
        FilmRatingRegEx.checkData(NUMBER, sHeight[0]);
        FilmRatingRegEx.checkData(DATE, sDate[0]);

        Actor actor = new Actor();

        actor.setName(name[0]);
        actor.setSurname(surname[0]);
        actor.setHeight(Integer.parseInt(sHeight[0]));
        actor.setPhotoPath(photoPath[0]);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT);
        Date date;
        try {
            date = simpleDateFormat.parse(sDate[0]);
        } catch (ParseException e) {
            throw new ApplicationException("Wrong date format. Needed yyyy-MM-dd", e);
        }
        actor.setDateOfBirth(date);
        return actor;
    }

}
