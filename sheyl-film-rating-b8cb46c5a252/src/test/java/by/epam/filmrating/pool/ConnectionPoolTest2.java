package by.epam.filmrating.pool;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by admin on 12.09.2016.
 */
public class ConnectionPoolTest2 {

    private static ConnectionPool pool;
    private static WrapperConnector connector;

    @BeforeClass
    public static void initPool() throws Exception {
        pool = ConnectionPool.getInstance();
        connector = pool.takeConnection();
    }



    @Test(timeout = 1)
    public void takeConnection() {
        Assert.assertNotNull( pool.takeConnection());

    }

    @Test(timeout = 1)
    public void returnConnection() {
        pool.closeConnection(connector);

    }
}
