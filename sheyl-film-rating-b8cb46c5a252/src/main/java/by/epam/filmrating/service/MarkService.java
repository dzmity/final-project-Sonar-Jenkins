package by.epam.filmrating.service;

import by.epam.filmrating.dao.MarkDAOImpl;
import by.epam.filmrating.entity.Mark;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;
import java.util.List;

/**
 * The {@code MarkService} class represents a layer-class between
 * the {@code MarkDAOImpl} class (DAO class) and servlet.
 * @author Dmitry Rafalovich
 */
public class MarkService extends AbstractService<Mark> {

    /**
     * Gives the list of {@code Mark} objects from the database.
     * @return {@link List} of marks
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public List<Mark> findAll() throws ServiceException {

        try (MarkDAOImpl markDAO = new MarkDAOImpl()) {
            return markDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException("Exception in MarkService.", e);
        }
    }

    /**
     * Adds definite mark to the database.
     * @param mark
     *        {@code Mark} object
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void create(Mark mark) throws ServiceException {

        try (MarkDAOImpl markDAO = new MarkDAOImpl()) {
            markDAO.create(mark);
        } catch (DAOException e) {
            throw new ServiceException("Exception in MarkService.", e);
        }
    }

    /**
     * Gives mark with specified {@code id} from the database.
     * @param id
     *        id of mark that must be found in the database
     * @return
     *        {@code Mark} object
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public Mark findById(long id) throws ServiceException {
        try (MarkDAOImpl markDAO = new MarkDAOImpl()) {
            return markDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in MarkService.", e);
        }
    }

    /**
     * Updates mark with specified {@code id} in the database.
     * @param id
     *        id of mark that must be updated in the database
     * @param mark
     *        new mark that must be added to the database replaced
     *        the old by this {@code id}
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void updateById(long id, Mark mark) throws ServiceException {
        try (MarkDAOImpl markDAO = new MarkDAOImpl()) {
            markDAO.updateById(id, mark);
        } catch (DAOException e) {
            throw new ServiceException("Exception in MarkService.", e);
        }
    }

    /**
     * Deletes definite mark from the database.
     * @param id
     *        id of mark that must be deleted from the database
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void deleteById(long id) throws ServiceException {
        try (MarkDAOImpl markDAO = new MarkDAOImpl()) {
            markDAO.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in MarkService.", e);
        }
    }
}
