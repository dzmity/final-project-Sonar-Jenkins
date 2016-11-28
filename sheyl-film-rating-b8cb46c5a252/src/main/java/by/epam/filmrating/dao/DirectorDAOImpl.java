package by.epam.filmrating.dao;

import by.epam.filmrating.entity.Country;
import by.epam.filmrating.entity.Director;
import by.epam.filmrating.exception.DAOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code DirectorDAOImpl} class is participant in Data Access Object Pattern.
 * This class is responsible to get {@link Director} objects from database.
 * In this class all the inherited methods are implemented.
 * @author Dmitry Rafalovich
 *
 */
public class DirectorDAOImpl extends DirectorDAO {

    private static final String DIRECTOR_ID = "director_id";
    private static final String FIRST_NAME = "name";
    private static final String LAST_NAME = "surname";
    private static final String PHOTO_PATH = "photo_path";
    private static final String COUNTRY = "country";

    private static final String SQL_SELECT_ALL = "SELECT director.director_id, director.name, director.surname, director.photo_path, " +
            " country.country FROM database1.director NATURAL JOIN country";
    private static final String SQL_CREATE_DIRECTOR = "INSERT INTO database1.director (name, surname, photo_path, country_id)" +
            " VALUES (?, ?, ?, ?)";
    private static final String SQL_DELETE_DIRECTOR = "DELETE FROM database1.director WHERE director_id = ?";
    private static final String SQL_UPDATE_DIRECTOR = "UPDATE database1.director SET name = ?, surname = ?, photo_path = ?, " +
            " country_id = ? WHERE director_id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT director.director_id, director.name, director.surname, director.photo_path, " +
            " country.country FROM database1.director NATURAL JOIN country WHERE director_id = ?";
    private static final String SQL_FIND_ID_BY_NAME_SURNAME = "SELECT director.director_id FROM director WHERE name =? AND surname = ?";

    @Override
    public List<Director> findAll() throws DAOException {

         List<Director> directors = new ArrayList<>();
        try (PreparedStatement statement = connector.prepareStatement(SQL_SELECT_ALL);
             ResultSet set = statement.executeQuery()) {

            while (set.next()) {
                Director director = new Director();
                director.setId(set.getLong(DIRECTOR_ID));
                director.setName(set.getString(FIRST_NAME));
                director.setSurname(set.getString(LAST_NAME));
                director.setPhotoPath(set.getString(PHOTO_PATH));
                Country country = new Country();
                country.setCountry(set.getString(COUNTRY));
                director.setCountry(country);
                directors.add(director);
            }
            return directors;

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during directors finding", e);
        }
    }

    @Override
    public void create(Director director) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_CREATE_DIRECTOR)) {
            statement.setString(1, director.getName());
            statement.setString(2, director.getSurname());
            statement.setString(3,  director.getPhotoPath());
            statement.setLong(4,  director.getCountry().getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during director creating", e);
        }
    }

    @Override
    public Director findById(long id) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_FIND_BY_ID)) {

            Director director;
            statement.setLong(1, id);
            ResultSet set =  statement.executeQuery();

            if (set.next()) {
                director = new Director();
                director.setId(set.getLong(DIRECTOR_ID));
                director.setName(set.getString(FIRST_NAME));
                director.setSurname(set.getString(LAST_NAME));
                director.setPhotoPath(set.getString(PHOTO_PATH));
                Country country = new Country();
                country.setCountry(set.getString(COUNTRY));
                director.setCountry(country);
                return director;
            } else {
                throw new DAOException("Director not found");
            }

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during director finding by id", e);
        }
    }

    @Override
    public void updateById(long id, Director director) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_UPDATE_DIRECTOR)) {

            statement.setString(1, director.getName());
            statement.setString(2, director.getSurname());
            statement.setString(3,  director.getPhotoPath());
            statement.setLong(4,  director.getCountry().getId());
            statement.setLong(5, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during director updating", e);
        }
    }

    @Override
    public void deleteById(long id) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_DELETE_DIRECTOR)) {
            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during director deleting", e);
        }
    }

    @Override
    public long findDirectorId(String name, String surname) throws DAOException {

        long id = -1;
        try (PreparedStatement statement = connector.prepareStatement(SQL_FIND_ID_BY_NAME_SURNAME)) {

            statement.setString(1, name.trim());
            statement.setString(2, surname);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                id = set.getLong(DIRECTOR_ID);
            }
            return id;
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during director_id finding", e);
        }
    }
}
