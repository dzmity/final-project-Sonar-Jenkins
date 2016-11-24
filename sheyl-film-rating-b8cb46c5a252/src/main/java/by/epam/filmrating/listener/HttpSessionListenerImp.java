package by.epam.filmrating.listener;

import by.epam.filmrating.manager.PathManager;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * The {@code PageRedirectSecurityFilter} class  extends {@link HttpSessionListener} class.
 * @author Dmitry Rafalovich
 */
@WebListener()
public class HttpSessionListenerImp implements HttpSessionListener {

    private  static final String REAL_PATH = "realPath";
    private  static final String PATH = "path.page.index";
    private  static final String CURRENT_PAGE = "currentPage";
    private  static final String LOCALE = "locale";
    private  static final String ALTERNATIVE_LOCALE = "altLocale";
    private  static final String F5_SECURITY = "security";
    private  static final String RU = "RU";
    private  static final String EN = "EN";
    private  static final String NEW_CODE = "newCode";


    public HttpSessionListenerImp() {
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {

        String realPath =  se.getSession().getServletContext().getRealPath("");
        se.getSession().setAttribute(REAL_PATH, realPath);

        long newCode = (long)(Math.random() * Long.MAX_VALUE);
        se.getSession().setAttribute(NEW_CODE, newCode);

        String page = PathManager.getProperty(PATH);
        se.getSession().setAttribute(CURRENT_PAGE, page);
        se.getSession().setAttribute(LOCALE, RU);
        se.getSession().setAttribute(ALTERNATIVE_LOCALE, EN);
        se.getSession().setAttribute(F5_SECURITY, 0);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}

