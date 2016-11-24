package by.epam.filmrating.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The {@code ImageServerLoader} class is designed for downloading images to the server
 * @author Dmitry Rafalovich
 *
 */
public class ImageServerLoader {

    private static final Logger LOG = LogManager.getLogger();
    private static final int BUFFER_SIZE = 4048;

    /**
     * The method uploads a file to the specified path
     * @param path
     *        the path where the file is loaded
     * @param part
     *        file that was received within
     *        a multipart/form-data POST request
     */
    public static void load(String path, Part part){

        try(InputStream in = part.getInputStream();
            FileOutputStream fos = new FileOutputStream(path)) {

            byte buffer[] = new byte[ BUFFER_SIZE ];
            int n;
            while ((n = in.read( buffer )) > 0) {
                fos.write( buffer, 0, n );
            }

        } catch (IOException e) {
            LOG.error("File was not created.");
        }
    }
}
