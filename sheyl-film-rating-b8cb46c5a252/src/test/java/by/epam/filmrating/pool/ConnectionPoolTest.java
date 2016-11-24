package by.epam.filmrating.pool;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 12.09.2016.
 */
public class ConnectionPoolTest {

    private static final int SIZE = 20;
    private static List<WrapperConnector> connectors = new ArrayList<>();
    private static ConnectionPool pool;
    private static WrapperConnector connector;

    @Test(timeout = 2700)
    public void getInstance() throws Exception {
        pool = ConnectionPool.getInstance();
    }

    @Test
    public void checkPoolSize() {
        for (int i = 0; i < SIZE; i++) {
            connector = pool.takeConnection();
            Assert.assertNotNull(connector);
            connector.realClose();
            connectors.add(connector);
        }
    }

    @Test
    public void checkConnectorExchange() {
        for (WrapperConnector x : connectors) {
            pool.closeConnection(x);
        }
    }


    @Test
    public void recheckPoolSize() {
        connectors = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            connector = pool.takeConnection();
            Assert.assertNotNull(connector);
            connectors.add(connector);
        }
    }

    @Test
    public void returnConnections() {
        for (WrapperConnector x : connectors) {
            pool.closeConnection(x);
        }
    }

    @Test(timeout = 1)
    public void takeConnection() {
        connector = pool.takeConnection();
        Assert.assertNotNull(connector);
    }

    @Test(timeout = 1)
    public void returnConnection() {
        pool.closeConnection(connector);
    }

    @AfterClass
    public static void closeConnectionPool() {
        pool.closePool();
    }
}