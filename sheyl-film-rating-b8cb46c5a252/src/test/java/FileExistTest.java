import org.junit.Test;

import java.io.File;
import static org.junit.Assert.assertTrue;

/**
 * Created by admin on 13.09.2016.
 */
public class FileExistTest {

    private static final String CONFIG_PATH = "src/main/resources/config.properties";
    private static final String APPLICATION_RESOURCE_PATH = "src/main/resources/applicationresource.properties";
    private static final String APPLICATION_RESOURCE_RU_PATH = "src/main/resources/applicationresource_ru.properties";
    private static final String MESSAGE_EN_PATH = "src/main/resources/message_en.properties";
    private static final String MESSAGE_RU_PATH = "src/main/resources/message_ru.properties";
    private static final String PAGE_PATH = "src/main/resources/path.properties";

    @Test
    public void checkConfigFile() {
        File file = new File(CONFIG_PATH);
        assertTrue(file.exists());
    }

    @Test
    public void checkApplicationFile() {
        File file = new File(APPLICATION_RESOURCE_PATH);
        assertTrue(file.exists());
        file = new File(APPLICATION_RESOURCE_RU_PATH);
        assertTrue(file.exists());
    }

    @Test
    public void checkMessageFile() {
        File file = new File(MESSAGE_EN_PATH);
        assertTrue(file.exists());
        file = new File(MESSAGE_RU_PATH);
        assertTrue(file.exists());
    }

    @Test
    public void checkPathFile() {
        File file = new File(PAGE_PATH);
        assertTrue(file.exists());
    }

}
