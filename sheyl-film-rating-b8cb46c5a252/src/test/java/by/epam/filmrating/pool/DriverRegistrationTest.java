package by.epam.filmrating.pool;

import org.junit.Test;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by admin on 13.09.2016.
 */
public class DriverRegistrationTest {

    @Test
    public void checkConfigFile() throws SQLException{
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
    }
}
