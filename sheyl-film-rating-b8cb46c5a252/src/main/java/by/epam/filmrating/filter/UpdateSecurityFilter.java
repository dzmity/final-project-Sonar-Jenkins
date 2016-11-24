package by.epam.filmrating.filter;

import by.epam.filmrating.exception.ApplicationException;
import by.epam.filmrating.util.FilmRatingRegEx;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The {@code UpdateSecurityFilter} class  extends {@link Filter} class.
 * The class is responsible for protection against violations
 * of the web-application when it is clicked F5.
 * @author Dmitry Rafalovich
 */
@WebFilter(urlPatterns = {"/*"},
        initParams = {@WebInitParam(name = "CODE", value = "code"),
                @WebInitParam(name = "NEW_CODE", value = "newCode"),
                @WebInitParam(name = "CURRENT_PAGE", value = "currentPage")})
public class UpdateSecurityFilter implements Filter{

    private static final String CODE = "CODE";
    private static final String NEW_CODE = "NEW_CODE";
    private static final String CURRENT_PAGE = "CURRENT_PAGE";

    private String code;
    private String newCode;
    private String currentPage;

    public void init(FilterConfig fConfig) throws ServletException {

        code = fConfig.getInitParameter(CODE);
        newCode = fConfig.getInitParameter(NEW_CODE);
        currentPage = fConfig.getInitParameter(CURRENT_PAGE);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String codeParameter = request.getParameter(code);

        if (codeParameter != null) {

            String page = (String) request.getSession().getAttribute(currentPage);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);

            try {
                FilmRatingRegEx.checkData(code, codeParameter);
                long requestCode = new Long(codeParameter);
                long sessionCode =  (long) request.getSession().getAttribute(newCode);
                if (requestCode == sessionCode) {
                    long code = (long)(Math.random() * Long.MAX_VALUE);
                    request.getSession().setAttribute(newCode, code);
                } else {
                    dispatcher.forward(request, response);
                    return;
                }
            } catch (ApplicationException e){
                dispatcher.forward(request, response);
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
