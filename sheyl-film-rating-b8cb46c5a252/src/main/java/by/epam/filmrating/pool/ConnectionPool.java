package by.epam.filmrating.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The {@code ConnectionPool} class is participant in Singleton Pattern.
 * Pool of connections conceptually similar to any other form of object pooling.
 * Database connections are often expensive to create because of the overhead of establishing
 * a network connection and initializing a database connection session in the back end database.
 * In turn, connection session initialization often requires time consuming processing to
 * perform user authentication, establish transactional contexts and establish other aspects
 * of the session that are required for subsequent database usage.
 * JDBC database connections are both expensive to initially create and then maintain over time,
 * therefore it is needs to put connections in the pool.
 * @author Dmitry Rafalovich
 */
public class ConnectionPool {

    private final static Logger LOG = LogManager.getLogger();
    private final static String CONFIG_PATH = "config";
    private final static String URL = "database.url";
    private final static String USER = "user";
    private final static String PASSWORD = "password";
    private final static String USE_UNICODE = "useUnicode";
    private final static String CHARACTER_ENCODING = "characterEncoding";
    private final static String POOL_SIZE = "database.pool.size";
    private final static String CHECK__EXCEPTION_TIME = "database.checkingExceptionCount";
    private final static String CHECKING_TIME = "database.checkingTime";
    private final static String DEFAULT_USE_UNICODE = "true";
    private final static String DEFAULT_CHARACTER_ENCODING = "UTF-8";
    private final static int DEFAULT_POOL_SIZE = 20;
    private final static int DEFAULT_CHECKING_TIME = 2;
    private final static int DEFAULT_EXCEPTION_COUNT = 4;

    private static Properties databaseProperties;
    private static String databaseUrl;
    private int size;

    private static Lock lock = new ReentrantLock();
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static ConnectionPool instance;

    private  ArrayBlockingQueue<WrapperConnector> connectionQueue;
    private static ResourceBundle resourceBundle;


    private ConnectionPool() {

        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOG.fatal("JDBC Driver was not registered!!", e);
            throw new RuntimeException("JDBC Driver was not registered!!");
        }

        try {
            resourceBundle = ResourceBundle.getBundle(CONFIG_PATH);
        } catch(MissingResourceException e) {
            LOG.fatal("Database configuration file not found!!", e);
            throw new RuntimeException("Database configuration file not found!!");
        }

        String user;
        String pass;
        try {
            databaseUrl = resourceBundle.getString(URL);
            user = resourceBundle.getString(USER);
            pass = resourceBundle.getString(PASSWORD);
        } catch (MissingResourceException e) {
            LOG.fatal("Can't find necessary key in config.properties for pool creation. " , e);
            throw new RuntimeException("Can't find necessary key in config.properties for pool creation. ");
        }

        String encoding;
        String useUnicode;
        try {
            encoding =  resourceBundle.getString(CHARACTER_ENCODING);
            useUnicode =  resourceBundle.getString(USE_UNICODE);
        } catch (MissingResourceException | NumberFormatException e) {
            LOG.error("Missing 'useUnicode' or 'characterEncoding' key in config.properties. Check config.properties." , e);
            useUnicode = DEFAULT_USE_UNICODE;
            encoding = DEFAULT_CHARACTER_ENCODING;
        }

        databaseProperties = new Properties();
        databaseProperties.put(USER, user);
        databaseProperties.put(PASSWORD, pass);
        databaseProperties.put(USE_UNICODE, useUnicode);
        databaseProperties.put(CHARACTER_ENCODING, encoding);

        try {
            size = Integer.parseInt(resourceBundle.getString(POOL_SIZE));
        } catch (MissingResourceException | NumberFormatException e) {
            LOG.error("Can't find 'database.pool.size ' key in config.properties or value is not a number. " , e);
            size = DEFAULT_POOL_SIZE;
        }
        connectionQueue = new ArrayBlockingQueue<> (size);
        addConnectionInPool(size);
    }
    /**
     * The method {@code getInstance} creates pool of connections at the first call, and
     * returns a reference to the pool for first and subsequent calls.
     *        required quantity of {@link Connection} objects
     */
    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    isCreated.getAndSet(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * The method {@code addConnectionInPool} is responsible for the filling pool of necessary
     * {@code count} of connections. If it is impossible - application will be stop.
     * @param count
     *        required quantity of {@link Connection} objects
     */
    private  void addConnectionInPool(int count){

        int exceptionMaxCount;
        try {
            exceptionMaxCount = Integer.parseInt(resourceBundle.getString(CHECK__EXCEPTION_TIME));
        } catch (MissingResourceException | NumberFormatException e) {
            LOG.error("Can't find 'database.checkingExceptionCount' key in config.properties or value is not a number. " , e);
            exceptionMaxCount = DEFAULT_EXCEPTION_COUNT;
        }

        int exceptionCount = 0;
        int i = 0;
        WrapperConnector connector;
        Connection connection;

        while (i < count) {

            if (exceptionCount > exceptionMaxCount) {
                LOG.fatal("Impossible to create connection pool." +
                        " Please, check connection settings. ");
                throw new RuntimeException("Impossible to create connection pool." +
                        " Please, check connection settings. ");
            }

            try {
                connection = DriverManager.getConnection(databaseUrl, databaseProperties);
                connector = new WrapperConnector(connection);
                if (connection != null) {
                    connectionQueue.offer(connector);
                    i++;
                } else {
                    exceptionCount++;
                }

            } catch (SQLException e) {
                LOG.error("Connection was not created.", e);
                exceptionCount++;
            }
        }
    }

    /**
     * The method {@code takeConnection} is responsible for the taking connection
     * from the pool.
     */
    public WrapperConnector takeConnection(){

        WrapperConnector connector = null;
        try {
            connector =  connectionQueue.take();

        } catch (InterruptedException e) {
            LOG.error("Connection was not obtained from the pool.", e);
        }
        return connector;
    }

    /**
     * The method {@code closeConnection} is responsible for the returning connection
     * in the pool. If connection is not valid - creates new connection and puts in pool.
     */
    void closeConnection(WrapperConnector connection){
        int checkingTime;
        try {
            checkingTime = Integer.parseInt(resourceBundle.getString(CHECKING_TIME));
        } catch (MissingResourceException | NumberFormatException e) {
            LOG.error("Can't find 'database.checkingTime' key in config.properties or value is not a number. " , e);
            checkingTime = DEFAULT_CHECKING_TIME;
        }
        try {
            if (connection.isValid(checkingTime)) {
            connectionQueue.offer(connection);

            } else {
                LOG.error("Connection was not valid. Create new connection.");
                addConnectionInPool(1);
            }
        } catch (SQLException e) {
            LOG.error("Time for checking connection < 0. Please, check config settings.", e);
        }
    }

    /**
     * The method {@code closePool} is responsible for the closing all connections
     * in the pool.
     */
    public void closePool() {
        for (int i = 0; i < size; i++) {
            takeConnection().realClose();
        }
    }
}
