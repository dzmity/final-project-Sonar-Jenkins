package by.epam.filmrating.dao;

import by.epam.filmrating.entity.Country;
import by.epam.filmrating.exception.DAOException;

/**
 * The {@code CountryDAO} class is participant in Data Access Object Pattern.
 * This class defines individual operations to be performed on a {@link Country} objects.
 * @author Dmitry Rafalovich
 *
 */
public abstract class CountryDAO extends AbstractDAO<Country> {

    @Override
    public Country findById(long id) throws DAOException {
        throw new DAOException("Unsupported operation.");
    }

    /**
     * Find country id  by name operation
     * @param country
     *        country name
     * @return
     *        country id or -1 if country with this name not found
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract long findCountryId(String country) throws DAOException;


}
