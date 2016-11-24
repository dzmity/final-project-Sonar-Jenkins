package by.epam.filmrating.dao;

import by.epam.filmrating.entity.Genre;
import by.epam.filmrating.exception.DAOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code GenreDAOImpl} class is participant in Data Access Object Pattern.
 * This class is responsible to get {@link Genre} objects from database.
 * In this class all the inherited methods are implemented.
 * @author Dmitry Rafalovich
 *
 */
public class GenreDAOImpl extends GenreDAO {

    private static final String GENRE_ID = "genre_id";
    private static final String GENRE = "genre";

    private final static String SQL_SELECT_ALL = "SELECT genre.genre_id, genre.genre FROM database1.genre";
    private static final String SQL_CREATE_GENRE = "INSERT INTO database1.genre (genre) VALUES (?)";
    private static final String SQL_DELETE_GENRE = "DELETE FROM database1.genre WHERE genre_id = ?";
    private static final String SQL_UPDATE_GENRE = "UPDATE database1.genre  SET genre =? WHERE genre_id = ?";

    private static final String SQL_SELECT_GENRE_BY_FILM_ID = "SELECT genre.genre FROM database1.genre" +
            " NATURAL JOIN film_genre WHERE film_id = ?";
    private static final String SQL_FIND_ID_BY_NAME = "SELECT genre.genre_id FROM genre WHERE genre = ?";

    @Override
    public List<Genre> findAll() throws DAOException {

        List<Genre> genres = new ArrayList<>();
        try(PreparedStatement statement = connector.prepareStatement(SQL_SELECT_ALL);
        ResultSet set = statement.executeQuery()) {

            while (set.next()) {
                Genre genre = new Genre();
                genre.setId(set.getLong(GENRE_ID));
                genre.setGenre(set.getString(GENRE));
                genres.add(genre);
            }
            return genres;

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during genres finding", e);
        }
    }

    @Override
    public void create(Genre genre) throws DAOException {
        try(PreparedStatement statement = connector.prepareStatement(SQL_CREATE_GENRE)) {
            statement.setString(1, genre.getGenre());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during genre creating", e);
        }
    }

    @Override
    public void updateById(long id, Genre entity) throws DAOException {

        try(PreparedStatement statement = connector.prepareStatement(SQL_UPDATE_GENRE)) {

            statement.setString(1,entity.getGenre());
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during genre updating", e);
        }
    }

    @Override
    public void deleteById(long id) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_DELETE_GENRE)) {
            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during genre deleting", e);
        }
    }

    @Override
    public List<Genre> findByFilmId(long id) throws DAOException{

        List<Genre> genres = new ArrayList<>();
        try(PreparedStatement statement = connector.prepareStatement(SQL_SELECT_GENRE_BY_FILM_ID)) {
            statement.setLong(1,id);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Genre genre = new Genre();
                genre.setGenre(set.getString(GENRE));
                genres.add(genre);
            }
            return genres;

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during genres finding by filmId", e);
        }
    }

    @Override
    public long findGenreId(String genre) throws DAOException {

        long id = -1;
        try (PreparedStatement statement = connector.prepareStatement(SQL_FIND_ID_BY_NAME)) {

            statement.setString(1, genre.trim());
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                id = set.getLong(GENRE_ID);
            }
            return id;
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during genre_id finding", e);
        }
    }
}
