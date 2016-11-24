package by.epam.filmrating.service;

import by.epam.filmrating.dao.GenreDAOImpl;
import by.epam.filmrating.entity.Genre;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;
import java.util.List;

/**
 * The {@code GenreService} class represents a layer-class between
 * the {@code GenreDAOImpl} class (DAO class) and servlet.
 * @author Dmitry Rafalovich
 */
public class GenreService extends AbstractService<Genre> {

    /**
     * Gives the list of {@code Genre} objects from the database.
     * @return {@link List} of genres
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public List<Genre> findAll() throws ServiceException {
        try (GenreDAOImpl genreDAO = new GenreDAOImpl()) {
            return genreDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException("Exception in GenreService.", e);
        }
    }

    /**
     * Adds definite genre to the database.
     * @param genre
     *        {@code Genre} object
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void create(Genre genre) throws ServiceException {

        try (GenreDAOImpl genreDAO = new GenreDAOImpl()) {
            genreDAO.create(genre);
        } catch (DAOException e) {
            throw new ServiceException("Exception in GenreService.", e);
        }
    }

    /**
     * Gives genre with specified {@code id} from the database.
     * @param id
     *        id of genre that must be found in the database
     * @return
     *        {@code Genre} object
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public Genre findById(long id) throws ServiceException {

        try (GenreDAOImpl genreDAO = new GenreDAOImpl()) {
            return genreDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in GenreService.", e);
        }
    }

    /**
     * Updates genre with specified {@code id} in the database.
     * @param id
     *        id of genre that must be updated in the database
     * @param genre
     *        new genre that must be added to the database replaced
     *        the old by this {@code id}
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void updateById(long id, Genre genre) throws ServiceException {
        try (GenreDAOImpl genreDAO = new GenreDAOImpl()) {
            genreDAO.updateById(id, genre);
        } catch (DAOException e) {
            throw new ServiceException("Exception in GenreService.", e);
        }
    }

    /**
     * Deletes definite genre from the database.
     * @param id
     *        id of genre that must be deleted from the database
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void deleteById(long id) throws ServiceException {
        try (GenreDAOImpl genreDAO = new GenreDAOImpl()) {
            genreDAO.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in GenreService.", e);
        }
    }

    /**
     * Updates the list of {@code Genre} objects in the database.
     * @param genres
     *        new genres that must be added to the database replaced
     *        the old by their id
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public void update(List<Genre> genres) throws ServiceException {
        try (GenreDAOImpl genreDAO = new GenreDAOImpl()) {
            for (Genre genre : genres) {
                genreDAO.updateById(genre.getId(), genre);
            }
        } catch (DAOException e) {
            throw new ServiceException("Exception in GenreService.", e);
        }
    }

    /**
     * Finds genre id by name in the database.
     * @param genre
     *        genre name
     * @return
     *        genre id or -1 if genre with this name not found
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public long findGenreId(String genre) throws ServiceException {
        try (GenreDAOImpl genreDAO = new GenreDAOImpl()) {
            return genreDAO.findGenreId(genre.trim());
        } catch (DAOException e) {
            throw new ServiceException("Exception in GenreService.", e);
        }
    }
}
