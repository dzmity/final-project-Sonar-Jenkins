package by.epam.filmrating.dao;

import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.exception.DAOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ActorDAOImpl} class is participant in Data Access Object Pattern.
 * This class is responsible to get {@link Actor} objects from database.
 * In this class all the inherited methods are implemented.
 * @author Dmitry Rafalovich
 *
 */
public class ActorDAOImpl extends ActorDAO {

    private static final String ACTOR_ID = "actor_id";
    private static final String FIRST_NAME = "name";
    private static final String LAST_NAME = "surname";
    private static final String HEIGHT = "height";
    private static final String DATE_OF_BIRTH = "dob";
    private static final String PHOTO_PATH = "photo_path";

    private final static String SQL_SELECT_ALL = "SELECT actor.actor_id, actor.name, actor.surname, actor.dob," +
            " actor.height, actor.photo_path FROM database1.actor";
    private static final String SQL_CREATE_ACTOR = "INSERT INTO database1.actor (name, surname," +
            " height, dob, photo_path) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_ACTOR = "DELETE FROM database1.actor WHERE actor_id = ?";
    private static final String SQL_UPDATE_ACTOR = "UPDATE database1.actor  SET name =?, surname = ?, height = ?, dob= ?," +
            " photo_path = ? WHERE actor_id = ?";
    private static final String SQL_FIND_ACTOR_BY_ID = "SELECT actor.actor_id, actor.name, actor.surname, actor.dob," +
            " actor.height, actor.photo_path FROM database1.actor WHERE actor_id = ?";

    private static final String SQL_SELECT_ACTORS_BY_FILM_ID = "SELECT actor.actor_id, actor.name, actor.surname  FROM database1.actor" +
            " NATURAL JOIN film_actor WHERE film_id = ?";
    private static final String SQL_FIND_ID_BY_NAME_SURNAME = "SELECT actor.actor_id FROM actor WHERE name =? AND surname = ?";

    @Override
    public List<Actor> findAll() throws DAOException {

        List<Actor> actors = new ArrayList<>();
        try(PreparedStatement statement = connector.prepareStatement(SQL_SELECT_ALL);
        ResultSet set = statement.executeQuery()) {

            while (set.next()) {
                Actor actor = new Actor();
                actor.setId(set.getInt(ACTOR_ID));
                actor.setName(set.getString(FIRST_NAME));
                actor.setSurname(set.getString(LAST_NAME));
                actor.setHeight(set.getInt(HEIGHT));
                actor.setDateOfBirth(set.getDate(DATE_OF_BIRTH));
                actor.setPhotoPath(set.getString(PHOTO_PATH));
                actors.add(actor);
            }
            return actors;
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during actors finding", e);
        }
    }

    @Override
    public void create(Actor actor) throws DAOException {

        try(PreparedStatement statement = connector.prepareStatement(SQL_CREATE_ACTOR)) {

            statement.setString(1,actor.getName());
            statement.setString(2,actor.getSurname());
            statement.setInt(3,actor.getHeight());
            Date date = new Date(actor.getDateOfBirth().getTime());
            statement.setDate(4, date);
            statement.setString(5,actor.getPhotoPath());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during actor creating", e);
        }
    }

    @Override
    public Actor findById(long id) throws DAOException {

        Actor actor;

        try (PreparedStatement statement = connector.prepareStatement(SQL_FIND_ACTOR_BY_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                actor = new Actor();
                actor.setId(set.getLong(ACTOR_ID));
                actor.setName(set.getString(FIRST_NAME));
                actor.setSurname(set.getString(LAST_NAME));
                actor.setHeight(set.getInt(HEIGHT));
                actor.setDateOfBirth(set.getDate(DATE_OF_BIRTH));
                actor.setPhotoPath(set.getString(PHOTO_PATH));
                return actor;
            } else {
                throw new DAOException("Actor not found");
            }
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during actor deleting", e);
        }
    }

    @Override
    public void updateById(long id, Actor actor) throws DAOException {

        try(PreparedStatement statement = connector.prepareStatement(SQL_UPDATE_ACTOR)) {

            statement.setString(1,actor.getName());
            statement.setString(2,actor.getSurname());
            statement.setInt(3,actor.getHeight());
            Date date = new Date(actor.getDateOfBirth().getTime());
            statement.setDate(4, date);
            statement.setString(5,actor.getPhotoPath());
            statement.setLong(6, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during actor updating", e);
        }
    }

    @Override
    public void deleteById(long id) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_DELETE_ACTOR)) {
            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during actor deleting", e);
        }
    }

    @Override
    public List<Actor> findByFilmId(long id) throws DAOException {

        List<Actor> actors = new ArrayList<>();
        try(PreparedStatement statement = connector.prepareStatement(SQL_SELECT_ACTORS_BY_FILM_ID)) {
            statement.setLong(1,id);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Actor actor = new Actor();
                actor.setId(set.getLong(ACTOR_ID));
                actor.setName(set.getString(FIRST_NAME));
                actor.setSurname(set.getString(LAST_NAME));
                actors.add(actor);
            }
            return actors;

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during actors finding by filmId", e);
        }
    }

    @Override
    public long findActorId(String name, String surname) throws DAOException {

        long id = -1;
        try (PreparedStatement statement = connector.prepareStatement(SQL_FIND_ID_BY_NAME_SURNAME)) {

            statement.setString(1, name.trim());
            statement.setString(2, surname.trim());
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                id = set.getLong(ACTOR_ID);
            }
            return id;
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during actor_id finding", e);
        }
    }

}
