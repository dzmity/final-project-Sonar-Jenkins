package by.epam.filmrating.service;

import by.epam.filmrating.dao.*;
import by.epam.filmrating.entity.*;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The {@code FilmService} class represents a layer-class between
 * the {@code FilmDAOImpl} class (DAO class) and servlet.
 * @author Dmitry Rafalovich
 */
public class FilmService extends AbstractService<Film> {

    /**
     * Gives the list of {@code Film} objects from the database.
     * @return {@link List} of films
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public List<Film> findAll() throws ServiceException {
        try (FilmDAOImpl filmDAO = new FilmDAOImpl()) {
            List<Film> films =  filmDAO.findAll();
            setGenresActorsComments(films);
            return films;

        } catch (DAOException e) {
            throw new ServiceException("Exception in FilmService.", e);
        }
    }

    /**
     * Adds definite film to the database.
     * @param film
     *        {@code Film} object
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void create(Film film) throws ServiceException {

        try (FilmDAOImpl filmDAO = new FilmDAOImpl()) {
            filmDAO.create(film);
            long id = filmDAO.findFilmId(film.getTitle(),film.getDirector().getId());
            for (Actor actor : film.getActors()) {
                filmDAO.addFilmActorById(id, actor.getId());
            }
            filmDAO.deleteFilmGenreById(id);
            for (Genre genre : film.getGenres()) {
                filmDAO.addFilmGenreById(id, genre.getId());
            }
        } catch (DAOException e) {
            throw new ServiceException("Exception in FilmService.", e);
        }
    }

    /**
     * Updates film with specified {@code id} in the database.
     * @param id
     *        id of film that must be updated in the database
     * @param film
     *        new film that must be added to the database replaced
     *        the old by specified {@code id}
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void updateById(long id, Film film) throws ServiceException {

        try (FilmDAOImpl filmDAO = new FilmDAOImpl()) {
            filmDAO.updateById(id, film);
            filmDAO.deleteFilmActorById(id);
            for (Actor actor : film.getActors()) {
                filmDAO.addFilmActorById(id, actor.getId());
            }
            filmDAO.deleteFilmGenreById(id);
            for (Genre genre : film.getGenres()) {
                filmDAO.addFilmGenreById(id, genre.getId());
            }
        } catch (DAOException e) {
            throw new ServiceException("Exception in FilmService.", e);
        }
    }

    /**
     * Deletes definite film from the database.
     * @param id
     *        id of film that must be deleted from the database
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void deleteById(long id) throws ServiceException {

        try (FilmDAOImpl filmDAO = new FilmDAOImpl()) {
            filmDAO.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in FilmService.", e);
        }
    }

    /**
     * Fills {@code Film} objects from {@code films} appropriate values
     * of genres, actors, comments and marks.
     * @param films
     *        {@link List} of films
     * @throws DAOException
     *        if a {@code DAOException} occurs.
     */
    private void setGenresActorsComments(List<Film> films) throws DAOException{

        int coeff = 10;

        try (GenreDAOImpl genreDAO = new GenreDAOImpl();
             ActorDAOImpl actorDAO = new ActorDAOImpl();
             CommentDAOImpl commentDAO = new CommentDAOImpl();
             MarkDAOImpl markDAO = new MarkDAOImpl()) {
            for (Film x: films) {
                long id = x.getId();
                List<Actor> actors = actorDAO.findByFilmId(id);
                x.setActors(actors);
                List<Genre> genres = genreDAO.findByFilmId(id);
                x.setGenres(genres);
                List<Comment> comments = commentDAO.findCommentsByFilmId(id);
                Comparator<Comment> comp = Comparator.comparing(Comment::getDate).reversed();
                comments.sort(comp);
                x.setComments(comments);
                Number[] ratingData = markDAO.findRatingByFilmId(id);
                x.setMarksCount((int) ratingData[0]);
                double mark = (double) ratingData[1] * coeff;
                x.setRating(new BigDecimal(mark).setScale(2, RoundingMode.UP).doubleValue());
            }
        }
    }

    /**
     * Gives film with specified {@code id} from the database.
     * @param id
     *        id of film that must be found in the database
     * @return
     *        {@code Film} object
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public Film findById(long id) throws ServiceException {

        try (FilmDAOImpl filmDAO = new FilmDAOImpl()) {
            List<Film> films = new ArrayList<>();
            films.add(filmDAO.findById(id));
            setGenresActorsComments(films);
            return films.get(0);
        } catch (DAOException e) {
            throw new ServiceException("Exception in FilmService.", e);
        }
    }

    /**
     * Finds {@link Film} objects in the database by {@code title}.
     * @param title
     *        film title
     * @return {@link List} of films
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public List<Film> findFilmByTitle(String title) throws ServiceException {

        try (FilmDAOImpl filmDAO = new FilmDAOImpl()) {
            List<Film> films =  filmDAO.findFilmByTitle(title.trim());
            setGenresActorsComments(films);
            return films;
        } catch (DAOException e) {
            throw new ServiceException("Exception in FilmService.", e);
        }
    }

    /**
     * Finds {@link Film} objects in the database by actor.
     * @param id
     *        actor id
     * @return {@link List} of films
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public List<Film> findFilmByActorId(long id) throws ServiceException {

        try (FilmDAOImpl filmDAO = new FilmDAOImpl()) {
            List<Film> films =  filmDAO.findFilmByActorId(id);
            setGenresActorsComments(films);
            return films;

        } catch (DAOException e) {
            throw new ServiceException("Exception in FilmService.", e);
        }
    }

    /**
     * Finds {@link Film} objects in the database by genre.
     * @param id
     *        genre id
     * @return {@link List} of films
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public List<Film> findFilmByGenreId(long id) throws ServiceException {

        try (FilmDAOImpl filmDAO = new FilmDAOImpl()) {

            List<Film> films =  filmDAO.findFilmByGenreId(id);
            setGenresActorsComments(films);
            return films;

        } catch (DAOException e) {
            throw new ServiceException("Exception in FilmService.", e);
        }
    }

    /**
     * Finds {@link Film} objects in the database by director.
     * @param id
     *        director id
     * @return {@link List} of films
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public List<Film> findFilmByDirectorId(long id) throws ServiceException {

        try (FilmDAOImpl filmDAO = new FilmDAOImpl()) {
            List<Film> films =  filmDAO.findFilmByDirectorId(id);
            setGenresActorsComments(films);
            return films;

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Finds {@link Film} objects in the database by year.
     * @param year
     *        release year of film
     * @return {@link List} of films
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public List<Film> findFilmByYear(int year) throws ServiceException {

        try (FilmDAOImpl filmDAO = new FilmDAOImpl()) {
            List<Film> films =  filmDAO.findFilmByYear(year);
            setGenresActorsComments(films);
            return films;
        } catch (DAOException e) {
            throw new ServiceException("Exception in FilmService.", e);
        }
    }
}
