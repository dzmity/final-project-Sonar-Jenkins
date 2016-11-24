package by.epam.filmrating.command.admin.film;

import by.epam.filmrating.exception.ApplicationException;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.MessageManager;
import by.epam.filmrating.entity.*;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.util.FilmRatingRegEx;
import by.epam.filmrating.service.ActorService;
import by.epam.filmrating.service.CountryService;
import by.epam.filmrating.service.DirectorService;
import by.epam.filmrating.service.GenreService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The {@code CreationFilm} class is a helper class.
 * The class is responsible for creation {@link Film} object.
 * @author Dmitry Rafalovich
 */
class CreationFilm {

    static final String COUNTRY_ERROR = "countryError";
    static final String ACTOR_ERROR = "actorError";
    static final String DIRECTOR_ERROR = "directorError";
    static final String GENRE_ERROR = "genreError";

    private static final String LOCALE = "locale";
    private static final String COUNTRY_KEY = "message.film.update.country";
    private static final String ACTOR_KEY = "message.film.update.actor";
    private static final String DIRECTOR_KEY = "message.film.update.director";
    private static final String GENRE_KEY = "message.film.update.genre";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String RELEASE_YEAR = "year";
    private static final String LENGTH = "length";
    private static final String PICTURE_PATH = "picture";
    private static final String TRAILER_PATH = "trailer";
    private static final String COUNTRY = "country";
    private static final String DIRECTOR = "director";
    private static final String NUMBER = "number";
    private static final String ACTOR = "actor";
    private static final String GENRE = "genre";
    private static final int NOT_FOUND = -1;

    /**
     * Tne method is responsible for film creation.
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        an instance of {@link Film} class
     * @throws ApplicationException
     *        if missing necessary parameters in the {@code content} or
     *        this parameters have not been validated.
     * @throws ServiceException
     *        if exception occurred in the Service layer
     */
    Film create(SessionRequestContent content) throws ApplicationException, ServiceException{

        Locale locale = new Locale((String) content.getSessionAttributes().get(LOCALE));
        String[] title = content.getRequestParameters().get(TITLE);
        String[] description = content.getRequestParameters().get(DESCRIPTION);
        String[] sYear = content.getRequestParameters().get(RELEASE_YEAR);
        String[] sLength = content.getRequestParameters().get(LENGTH);
        String[] picturePath = content.getRequestParameters().get(PICTURE_PATH);
        String[] trailerPath = content.getRequestParameters().get(TRAILER_PATH);
        String[] countryName = content.getRequestParameters().get(COUNTRY);
        String[] directorName = content.getRequestParameters().get(DIRECTOR);
        String[] actors = content.getRequestParameters().get(ACTOR);
        String[] genres = content.getRequestParameters().get(GENRE);

        if(title == null || description == null || sYear == null || sLength == null || picturePath == null ||
                trailerPath == null || countryName == null || directorName == null || actors == null || genres == null) {
            throw new ApplicationException("All fields must be filled. Check 'required' parameter for " +
                    "all <input> tags in film.jsp.");
        }

        FilmRatingRegEx.checkData(RELEASE_YEAR, sYear[0]);
        FilmRatingRegEx.checkData(NUMBER, sLength[0]);

        Film film = new Film();
        film.setTitle(title[0]);
        film.setDescription(description[0]);
        film.setYear(Integer.parseInt(sYear[0]));
        film.setLength(Integer.parseInt(sLength[0]));
        film.setPicturePath(picturePath[0]);
        film.setTrailerPath(trailerPath[0]);


        CountryService countryService = new CountryService();
        long countryId = countryService.findCountryId(countryName[0].trim());
        if (countryId != NOT_FOUND) {
            Country country = new Country();
            country.setId(countryId);
            film.setCountry(country);
        } else {
            content.setAttribute(COUNTRY_ERROR, MessageManager.getProperty(COUNTRY_KEY, locale));
        }

        String[] directorParameters = directorName[0].trim().split(FilmRatingRegEx.GREEDY_SPACES);
        if (directorParameters.length == 2) {
            DirectorService directorService = new DirectorService();
            long directorId = directorService.findDirectorId(directorParameters[0], directorParameters[1]);
            if (directorId != NOT_FOUND) {
                Director director = new Director();
                director.setId(directorId);
                film.setDirector(director);
            } else {
                content.setAttribute(DIRECTOR_ERROR, MessageManager.getProperty(DIRECTOR_KEY, locale));
            }
        } else {
            content.setAttribute(DIRECTOR_ERROR, MessageManager.getProperty(DIRECTOR_KEY, locale));
        }


        List<Actor> filmActors = new ArrayList<>();
        for (String x : actors) {
            String actorName = x.trim();
            if (!actorName.isEmpty()) {
                String[] actorParameters = actorName.split(FilmRatingRegEx.GREEDY_SPACES);

                if (actorParameters.length >= 2) {
                    ActorService actorService = new ActorService();
                    StringBuilder actorSurname = new StringBuilder();
                    for (int i = 1; i < actorParameters.length; i++) {
                        actorSurname.append(actorParameters[i]);
                        actorSurname.append(" ");
                    }
                    long actorId = actorService.findActorId(actorParameters[0], actorSurname.toString().trim());
                    if (actorId != NOT_FOUND) {
                        Actor actor = new Actor();
                        actor.setId(actorId);
                        if (!filmActors.contains(actor)) {
                            filmActors.add(actor);
                        }
                    } else {
                        content.setAttribute(ACTOR_ERROR, MessageManager.getProperty(ACTOR_KEY, locale));
                    }
                } else {
                    content.setAttribute(ACTOR_ERROR, MessageManager.getProperty(ACTOR_KEY, locale));
                }
            }
        }
        film.setActors(filmActors);

        List<Genre> filmGenres = new ArrayList<>();
        for (String x : genres) {
            String genreName = x.trim();
            if (!genreName.isEmpty()) {
                GenreService genreService = new GenreService();
                long genreID = genreService.findGenreId(genreName);
                if (genreID != NOT_FOUND) {
                    Genre genre = new Genre();
                    genre.setId(genreID);
                    if (!filmGenres.contains(genre)) {
                        filmGenres.add(genre);
                    }
                } else {
                    content.setAttribute(GENRE_ERROR, MessageManager.getProperty(GENRE_KEY, locale));
                }
            }
        }
        film.setGenres(filmGenres);
        return film;
    }
}
