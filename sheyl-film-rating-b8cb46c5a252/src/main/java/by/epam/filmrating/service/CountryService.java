package by.epam.filmrating.service;

import by.epam.filmrating.dao.CountryDAOImpl;
import by.epam.filmrating.entity.Country;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;
import java.util.List;

/**
 * The {@code CountryService} class represents a layer-class between
 * the {@code CountryDAOImpl} class (DAO class) and servlet.
 * @author Dmitry Rafalovich
 */
public class CountryService extends AbstractService<Country> {

    /**
     * Gives the list of {@code Country} objects from the database.
     * @return {@link List} of countries
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public List<Country> findAll() throws ServiceException {
        try (CountryDAOImpl countryDAO = new CountryDAOImpl()) {
            return countryDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException("Exception in CountryService.", e);
        }
    }

    /**
     * Adds definite country to the database.
     * @param country
     *        {@code Country} object
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void create(Country country) throws ServiceException {
        try (CountryDAOImpl countryDAO = new CountryDAOImpl()) {
            countryDAO.create(country);
        } catch (DAOException e) {
            throw new ServiceException("Exception in CountryService.", e);
        }
    }

    /**
     * Gives country with specified {@code id} from the database.
     * @param id
     *        id of country that must be found in the database
     * @return
     *        {@code Country} object
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public Country findById(long id) throws ServiceException {
        try (CountryDAOImpl countryDAO = new CountryDAOImpl()) {
            return countryDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in CountryService.", e);
        }
    }

    /**
     * Updates country with specified {@code id} in the database.
     * @param id
     *        id of country that must be updated in the database
     * @param country
     *        new country that must be added to the database replaced
     *        the old by this {@code id}
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void updateById(long id, Country country) throws ServiceException {

        try (CountryDAOImpl countryDAO = new CountryDAOImpl()) {
            countryDAO.updateById(id, country);
        } catch (DAOException e) {
            throw new ServiceException("Exception in CountryService.", e);
        }
    }

    /**
     * Deletes definite country from the database.
     * @param id
     *        id of country that must be deleted from the database
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void deleteById(long id) throws ServiceException {

        try (CountryDAOImpl countryDAO = new CountryDAOImpl()) {
            countryDAO.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in CountryService.", e);
        }
    }

    /**
     * Updates the list of {@code Country} objects in the database.
     * @param countries
     *        new countries that must be added to the database replaced
     *        the old by their id
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public void update(List<Country> countries) throws ServiceException {

        try (CountryDAOImpl countryDAO = new CountryDAOImpl()) {
            for (Country country : countries) {
                countryDAO.updateById(country.getId(), country);
            }
        } catch (DAOException e) {
            throw new ServiceException("Exception in CountryService.", e);
        }
    }

    /**
     * Finds country id by name in the database.
     * @param country
     *        country name
     * @return
     *        country id or -1 if country with this name not found
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public long findCountryId(String country) throws ServiceException {
        try (CountryDAOImpl countryDAO = new CountryDAOImpl()) {
            return countryDAO.findCountryId(country);
        } catch (DAOException e) {
            throw new ServiceException("Exception in CountryService.", e);
        }
    }
}
