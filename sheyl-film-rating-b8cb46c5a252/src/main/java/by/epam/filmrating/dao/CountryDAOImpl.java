package by.epam.filmrating.dao;

import by.epam.filmrating.entity.Country;
import by.epam.filmrating.exception.DAOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code CountryDAOImpl} class is participant in Data Access Object Pattern.
 * This class is responsible to get {@link Country} objects from database.
 * In this class all the inherited methods are implemented.
 * @author Dmitry Rafalovich
 *
 */
public class CountryDAOImpl extends CountryDAO {

    private static final String COUNTRY_ID = "country_id";
    private static final String COUNTRY = "country";

    private final static String SQL_SELECT_ALL = "SELECT country.country_id, country.country FROM database1.country";
    private static final String SQL_CREATE_COUNTRY = "INSERT INTO database1.country (country) VALUES (?)";
    private static final String SQL_DELETE_COUNTRY = "DELETE FROM database1.country WHERE country_id = ?";
    private static final String SQL_UPDATE_COUNTRY = "UPDATE database1.country  SET country =? WHERE country_id = ?";
    private static final String SQL_FIND_ID_BY_NAME = "SELECT country.country_id FROM country WHERE country = ?";

    @Override
    public void create(Country country) throws DAOException {
        try (PreparedStatement statement = connector.prepareStatement(SQL_CREATE_COUNTRY)) {
            statement.setString(1, country.getCountry());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during country creating", e);
        }
    }

    @Override
    public void updateById(long id, Country country) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_UPDATE_COUNTRY)) {

            statement.setString(1, country.getCountry());
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during country updating", e);
        }
    }

    @Override
    public void deleteById(long id) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_DELETE_COUNTRY)) {
            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during country deleting", e);
        }
    }

    @Override
    public List<Country> findAll() throws DAOException {

        List<Country> countries = new ArrayList<>();
        try (PreparedStatement statement = connector.prepareStatement(SQL_SELECT_ALL);
             ResultSet set = statement.executeQuery()) {

            while (set.next()) {
                Country country = new Country();
                country.setId(set.getLong(COUNTRY_ID));
                country.setCountry(set.getString(COUNTRY));
                countries.add(country);
            }
            return countries;

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during countries finding", e);
        }
    }

    @Override
    public long findCountryId(String country) throws DAOException {

        long id = -1;
        try (PreparedStatement statement = connector.prepareStatement(SQL_FIND_ID_BY_NAME)) {

            statement.setString(1, country.trim());
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                id = set.getLong(COUNTRY_ID);
            }
            return id;
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during country_id finding", e);
        }
    }

}
