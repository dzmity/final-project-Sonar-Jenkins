package by.epam.filmrating.dao;

import by.epam.filmrating.entity.*;
import by.epam.filmrating.exception.DAOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code FilmDAOImpl} class is participant in Data Access Object Pattern.
 * This class is responsible to get {@link Film} objects from database.
 * In this class all the inherited methods are implemented.
 * @author Dmitry Rafalovich
 *
 */
public class FilmDAOImpl extends FilmDAO {

    private static final String FILM_ID = "film_id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String RELEASE_YEAR = "release_year";
    private static final String LENGTH = "length";
    private static final String PICTURE_PATH = "picture_path";
    private static final String TRAILER_PATH = "trailer_path";
    private static final String COUNTRY = "country";
    private static final String NAME = "name";
    private static final String DIRECTOR_ID = "director_id";
    private static final String SURNAME = "surname";

    private static final String SQL_SELECT_ALL_FILMS = "SELECT film.film_id, film.title, film.description, film.release_year, " +
            "film.length, film.picture_path, film.trailer_path, country.country, film.director_id, director.name, director.surname FROM film" +
            " NATURAL JOIN country INNER JOIN director ON director.director_id = film.director_id";

    private static final String SQL_ADD_FILM = "INSERT INTO database1.film (title, description, release_year, length," +
            "country_id, director_id, picture_path, film.trailer_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_ADD_FILM_ACTOR = "INSERT INTO database1.film_actor (film_id, actor_id) VALUES (?, ?)";

    private static final String SQL_ADD_FILM_GENRE = "INSERT INTO database1.film_genre (film_id, genre_id) VALUES (?, ?)";

    private static final String SQL_UPDATE_FILM = "UPDATE database1.film  SET title = ?, description = ?," +
            " release_year = ?, length = ?, country_id = ?, director_id = ?, picture_path = ?, trailer_path = ? WHERE film_id = ?";

    private static final String SQL_DELETE_FILM = "DELETE FROM database1.film WHERE film_id = ?";

    private static final String SQL_DELETE_FILM_ACTOR = "DELETE FROM database1.film_actor WHERE film_id = ?";

    private static final String SQL_DELETE_FILM_GENRE = "DELETE FROM database1.film_genre WHERE film_id = ?";

    private static final String SQL_FIND_FILM_BY_ID = "SELECT film.film_id, film.title, film.description, film.release_year, " +
            "film.length, film.picture_path, film.trailer_path, country.country, film.director_id, director.name, director.surname FROM film" +
            " NATURAL JOIN country INNER JOIN director ON director.director_id = film.director_id WHERE film_id = ?";

    private static final String SQL_FIND_FILM_BY_TITLE = "SELECT film.film_id, film.title, film.description, film.release_year, " +
            "film.length, film.picture_path, film.trailer_path, country.country, film.director_id, director.name, director.surname FROM film" +
            " NATURAL JOIN country INNER JOIN director ON director.director_id = film.director_id WHERE film.title LIKE ?";

    private static final String SQL_FIND_FILM_BY_ACTOR_ID = "SELECT film.film_id, film.title, film.description, film.release_year, " +
            "film.length, film.picture_path, film.trailer_path, country.country, film.director_id, director.name, director.surname FROM film" +
            " NATURAL JOIN country INNER JOIN director ON director.director_id = film.director_id NATURAL JOIN film_actor WHERE actor_id = ?";

    private static final String SQL_FIND_FILM_BY_GENRE_ID = "SELECT film.film_id, film.title, film.description, film.release_year, " +
            "film.length, film.picture_path, film.trailer_path, country.country, film.director_id, director.name, director.surname FROM film" +
            " NATURAL JOIN country INNER JOIN director ON director.director_id = film.director_id NATURAL JOIN film_genre WHERE genre_id = ?";

    private static final String SQL_FIND_FILM_BY_DIRECTOR_ID = "SELECT film.film_id, film.title, film.description, film.release_year, " +
            "film.length, film.picture_path, film.trailer_path, country.country, film.director_id, director.name, director.surname FROM film" +
            " NATURAL JOIN country INNER JOIN director ON director.director_id = film.director_id  WHERE film.director_id = ?";

    private static final String SQL_FIND_FILM_BY_YEAR = "SELECT film.film_id, film.title, film.description, film.release_year, " +
            "film.length, film.picture_path, film.trailer_path, country.country, film.director_id, director.name, director.surname FROM film" +
            " NATURAL JOIN country INNER JOIN director ON director.director_id = film.director_id WHERE film.release_year = ?";

    private static final String SQL_FIND_ID_BY_TITLE_DIRECTOR = "SELECT film.film_id FROM film WHERE title =? AND director_id = ?";

    @Override
    public List<Film> findAll() throws DAOException {

        try(PreparedStatement statement = connector.prepareStatement(SQL_SELECT_ALL_FILMS);
            ResultSet set = statement.executeQuery()) {

           return findFromSet(set);
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during film finding", e);
        }
    }

    @Override
    public void create(Film film) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_ADD_FILM)) {
            statement.setString(1, film.getTitle());
            statement.setString(2, film.getDescription());
            statement.setInt(3, film.getYear());
            statement.setInt(4, film.getLength());
            statement.setLong(5, film.getCountry().getId());
            statement.setLong(6, film.getDirector().getId());
            statement.setString(7, film.getPicturePath());
            statement.setString(8, film.getTrailerPath());
            statement.executeUpdate();


        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during film adding", e);
        }
    }

    @Override
    public Film findById(long id) throws DAOException {

        try(PreparedStatement statement = connector.prepareStatement(SQL_FIND_FILM_BY_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            List<Film> films = findFromSet(set);
            if (!films.isEmpty()) {
                return films.get(0);
            } else {
                throw new DAOException("Film not found");
            }

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during film finding by id", e);
        }
    }

    @Override
    public void updateById(long id, Film film) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_UPDATE_FILM)) {
            statement.setString(1, film.getTitle());
            statement.setString(2, film.getDescription());
            statement.setInt(3, film.getYear());
            statement.setInt(4, film.getLength());
            statement.setLong(5, film.getCountry().getId());
            statement.setLong(6, film.getDirector().getId());
            statement.setString(7, film.getPicturePath());
            statement.setString(8, film.getTrailerPath());
            statement.setLong(9, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during film updating", e);
        }
    }

    @Override
    public void deleteById(long id) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_DELETE_FILM)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during film deleting", e);
        }
    }

    @Override
    public void addFilmActorById(long filmId, long actorId) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_ADD_FILM_ACTOR)) {

            statement.setLong(1, filmId);
            statement.setLong(2, actorId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during film_actor adding", e);
        }
    }

    @Override
    public void addFilmGenreById(long filmId, long genreId) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_ADD_FILM_GENRE)) {

            statement.setLong(1, filmId);
            statement.setLong(2, genreId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during film_genre adding", e);
        }
    }

    @Override
    public void deleteFilmActorById(long id) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_DELETE_FILM_ACTOR)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during film_actor deleting", e);
        }
    }

    @Override
    public void deleteFilmGenreById(long id) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_DELETE_FILM_GENRE)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during film_genre deleting", e);
        }
    }

    @Override
    public long findFilmId(String title, long directorId) throws DAOException{

        try(PreparedStatement statement = connector.prepareStatement(SQL_FIND_ID_BY_TITLE_DIRECTOR)) {
            long id = -1;
            statement.setString(1, title);
            statement.setLong(2, directorId);
            ResultSet set = statement.executeQuery();
            if (set.next()){
                id = set.getLong(FILM_ID);
            }
            return id;

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during film_id finding ", e);
        }
    }

    @Override
    public List<Film> findFilmByTitle(String title) throws DAOException {


        try(PreparedStatement statement = connector.prepareStatement(SQL_FIND_FILM_BY_TITLE)) {
            statement.setString(1, "%" + title.trim() + "%");
            ResultSet set = statement.executeQuery();
            return findFromSet(set);

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during film finding by title", e);
        }
    }

    @Override
    public List<Film> findFilmByActorId(long id) throws DAOException {


        try(PreparedStatement statement = connector.prepareStatement(SQL_FIND_FILM_BY_ACTOR_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            return findFromSet(set);

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during film finding by actor_id", e);
        }
    }

    @Override
    public List<Film> findFilmByGenreId(long id) throws DAOException {


        try(PreparedStatement statement = connector.prepareStatement(SQL_FIND_FILM_BY_GENRE_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            return findFromSet(set);

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during film finding by genre_id", e);
        }
    }

    @Override
    public List<Film> findFilmByDirectorId(long id) throws DAOException {


        try(PreparedStatement statement = connector.prepareStatement(SQL_FIND_FILM_BY_DIRECTOR_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            return findFromSet(set);

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during film finding by director_id", e);
        }
    }

    @Override
    public List<Film> findFilmByYear(int year) throws DAOException {

        try(PreparedStatement statement = connector.prepareStatement(SQL_FIND_FILM_BY_YEAR)) {
            statement.setInt(1, year);
            ResultSet set = statement.executeQuery();
            return findFromSet(set);

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during film finding by year", e);
        }
    }

    /**
     * Create {@link List} of films operation.
     * @param set
     *        ResultSet object
     * @return
     *        {@link List} of films
     * @throws SQLException
     *        if a database access error occurs or this method is called on a closed result set.
     */
    private List<Film> findFromSet(ResultSet set) throws SQLException{

        List<Film> films = new ArrayList<>();
        while(set.next()) {
                Film film = new Film();
                long id = set.getLong(FILM_ID);
                film.setId(id);
                film.setDescription(set.getString(DESCRIPTION));
                film.setTitle(set.getString(TITLE));
                film.setPicturePath(set.getString(PICTURE_PATH));
                film.setTrailerPath(set.getString(TRAILER_PATH));
                film.setYear(set.getInt(RELEASE_YEAR));
                film.setLength(set.getInt(LENGTH));
                Country country = new Country();
                country.setCountry(set.getString(COUNTRY));
                film.setCountry(country);
                Director director = new Director();
                director.setId(set.getLong(DIRECTOR_ID));
                director.setName(set.getString(NAME));
                director.setSurname(set.getString(SURNAME));
                film.setDirector(director);
                films.add(film);
        }
        return films;
    }
}
