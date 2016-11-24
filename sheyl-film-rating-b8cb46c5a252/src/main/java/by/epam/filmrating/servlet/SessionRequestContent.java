package by.epam.filmrating.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * The {@code SessionRequestContent} class  is designed to store information
 * from an instance of the request. Class protects request from the incorrect
 * modification in the business logic of the application.
 * @author Dmitry Rafalovich
 */
public class SessionRequestContent {

    private static final Logger LOG = LogManager.getLogger();
    private static final String COMMAND = "command";
    private static final String LOG_OUT = "log_out";
    private static final String FILE = "file";
    private static final String PART = "part";
    private static final String REQUEST = "request";
    private static final String LOAD_IMAGE = "load_image";
    private static final String UPDATE_IMAGE = "update_user_image";

    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;

    public SessionRequestContent() {

        requestAttributes = new HashMap<>();
        sessionAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
    }

    public HashMap<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public void setAttribute(String key, Object value) {
        requestAttributes.put(key, value);
    }

    public HashMap<String, String[]> getRequestParameters() {
        return requestParameters;
    }

    public HashMap<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public void addSessionAttribute(String key, Object value) {
        sessionAttributes.put(key, value);
    }

    /**
     * The method extracts the necessary information from the request and stores
     * it in the class.
     * @param request
     *        an {@link HttpServletRequest} object that contains the request
     *        the client has made of the {@code Controller}
     */
    void extractValues(HttpServletRequest request) {

        if (request.getParameter(COMMAND) == null) {
            return;
        }

        if (request.getParameter(COMMAND).equals(LOG_OUT) ) {
            requestAttributes.put(REQUEST, request);
        }

        for (String key : request.getParameterMap().keySet()) {
            requestParameters.put(key, request.getParameterMap().get(key));
        }
        Enumeration<String> attributes = request.getSession().getAttributeNames();
        while (attributes.hasMoreElements()) {
            String key = attributes.nextElement();
            Object value = request.getSession().getAttribute(key);
            sessionAttributes.put(key, value);
        }

        if (request.getParameter(COMMAND).equals(LOAD_IMAGE) || request.getParameter(COMMAND).equals(UPDATE_IMAGE)) {
            try {
                requestAttributes.put(PART, request.getPart(FILE));

            } catch (ServletException | IOException e) {
                LOG.error("Exception in SessionRequestContent in 'extractValues' method.", e);
                requestAttributes.put(PART, null);
            }
        }
    }

    /**
     * The method fills the request the necessary information from the class before
     * the request is sent back to the user.
     * @param request
     *        an {@link HttpServletRequest} object that contains the request
     *        the client has made of the {@code Controller}
     */
    void insertAttributes(HttpServletRequest request) {

        for (String key: requestAttributes.keySet()) {
            request.setAttribute(key, requestAttributes.get(key));
        }
        if (request.getSession(false) != null) {
            for (String key: sessionAttributes.keySet()) {
                request.getSession().setAttribute(key, sessionAttributes.get(key));
            }
        }

    }
}
