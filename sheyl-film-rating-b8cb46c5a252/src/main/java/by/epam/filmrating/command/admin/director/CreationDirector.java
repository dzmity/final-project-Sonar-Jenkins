package by.epam.filmrating.command.admin.director;

import by.epam.filmrating.exception.ApplicationException;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.MessageManager;
import by.epam.filmrating.entity.Country;
import by.epam.filmrating.entity.Director;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.CountryService;
import by.epam.filmrating.util.FilmRatingRegEx;
import java.util.Locale;

/**
 * The {@code CreationDirector} class is a helper class.
 * The class is responsible for creation {@link Director} object.
 * @author Dmitry Rafalovich
 */
class CreationDirector {

    static final String COUNTRY_ERROR = "countryError";
    private static final String COUNTRY_KEY = "message.film.update.country";
    private static final int NOT_FOUND = -1;
    private static final String LOCALE = "locale";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String COUNTRY = "country";
    private static final String PHOTO_PATH = "photo";

    /**
     * Tne method is responsible for director creation.
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        an instance of {@link Director} class
     * @throws ApplicationException
     *        if missing necessary parameters in the {@code content} or
     *        this parameters have not been validated.
     * @throws ServiceException
     *        if exception occurred in the {@link CountryService}
     */
    Director create(SessionRequestContent content) throws ApplicationException, ServiceException{

        Locale locale = new Locale((String) content.getSessionAttributes().get(LOCALE));

        String[] name = content.getRequestParameters().get(NAME);
        String[] surname = content.getRequestParameters().get(SURNAME);
        String[] photoPath = content.getRequestParameters().get(PHOTO_PATH);
        String[] countryName = content.getRequestParameters().get(COUNTRY);

        if (name == null || surname == null || countryName == null|| photoPath == null) {
            throw new ApplicationException("All fields must be filled. Check 'required' parameter for " +
                    "all <input> tags in director.jsp.");
        }

        FilmRatingRegEx.checkData(NAME, name[0]);

        Director director = new Director();
        director.setName(name[0]);
        director.setSurname(surname[0]);
        director.setPhotoPath(photoPath[0]);


        CountryService countryService = new CountryService();
        long countryId = countryService.findCountryId(countryName[0]);
        if (countryId != NOT_FOUND) {

            Country country = new Country();
            country.setId(countryId);
            director.setCountry(country);
        } else {
                content.setAttribute(COUNTRY_ERROR, MessageManager.getProperty(COUNTRY_KEY, locale));
         }
        return director;
    }
}
