package by.epam.filmrating.service;

import by.epam.filmrating.dao.DirectorDAOImpl;
import by.epam.filmrating.entity.Director;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;
import java.util.List;

/**
 * The {@code DirectorService} class represents a layer-class between
 * the {@code DirectorDAOImpl} class (DAO class) and servlet.
 * @author Dmitry Rafalovich
 */
public class DirectorService extends AbstractService<Director> {

    /**
     * Gives the list of {@code Director} objects from the database.
     * @return {@link List} of directors
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public List<Director> findAll() throws ServiceException {
        try (DirectorDAOImpl directorDAO = new DirectorDAOImpl()) {
            return directorDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException("Exception in DirectorService.", e);
        }
    }

    /**
     * Adds definite director to the database.
     * @param director
     *        {@code Director} object
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void create(Director director) throws ServiceException {

        try (DirectorDAOImpl directorDAO = new DirectorDAOImpl()) {
            directorDAO.create(director);
        } catch (DAOException e) {
            throw new ServiceException("Exception in DirectorService.", e);
        }
    }

    /**
     * Gives director with specified {@code id} from the database.
     * @param id
     *        id of director that must be found in the database
     * @return
     *        {@code Director} object
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public Director findById(long id) throws ServiceException {

        try (DirectorDAOImpl directorDAO = new DirectorDAOImpl()) {
            return directorDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in DirectorService.", e);
        }
    }

    /**
     * Updates director with specified {@code id} in the database.
     * @param id
     *        id of director that must be updated in the database
     * @param director
     *        new director that must be added to the database replaced
     *        the old by this {@code id}
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void updateById(long id, Director director) throws ServiceException {

        try (DirectorDAOImpl directorDAO = new DirectorDAOImpl()) {
            directorDAO.updateById(id, director);
        } catch (DAOException e) {
            throw new ServiceException("Exception in DirectorService.", e);
        }
    }

    /**
     * Deletes definite director from the database.
     * @param id
     *        id of director that must be deleted from the database
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void deleteById(long id) throws ServiceException {

        try (DirectorDAOImpl directorDAO = new DirectorDAOImpl()) {
            directorDAO.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in DirectorService.", e);
        }
    }

    /**
     * Finds director id by {@code name} and {@code surname} in the database.
     * @param name
     *        director name
     * @param surname
     *        director surname
     * @return
     *        director id or -1 if director with this parameters not found
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public long findDirectorId(String name, String surname) throws ServiceException {

        try (DirectorDAOImpl directorDAO = new DirectorDAOImpl()) {
            return directorDAO.findDirectorId(name.trim(), surname.trim());
        } catch (DAOException e) {
            throw new ServiceException("Exception in DirectorService.", e);
        }
    }
}
