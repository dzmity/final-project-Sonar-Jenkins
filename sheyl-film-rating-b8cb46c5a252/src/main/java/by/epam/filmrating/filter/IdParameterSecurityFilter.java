package by.epam.filmrating.filter;

import by.epam.filmrating.exception.ApplicationException;
import by.epam.filmrating.util.FilmRatingRegEx;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * The {@code IdParameterSecurityFilter} class  extends {@link Filter} class.
 * The class is responsible for validation {@code id} parameter in the
 * {@code request}.
 * @author Dmitry Rafalovich
 */
@WebFilter(urlPatterns = {"/controller"},
        initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class IdParameterSecurityFilter implements Filter {

    private static final String ID = "id";
    private static final String INDEX_PATH = "INDEX_PATH";
    private String indexPath;

    public void init(FilterConfig fConfig) throws ServletException {
        indexPath = fConfig.getInitParameter(INDEX_PATH);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Map<String, String[]> parameters  = request.getParameterMap();

        for (Map.Entry<String, String[]> param : parameters.entrySet()) {

            if (ID.equals(param.getKey())) {

                try {
                    FilmRatingRegEx.checkData(ID, param.getValue()[0]);
                } catch (ApplicationException e){

                    RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(indexPath);
                    dispatcher.forward(request, response);
                    return;
                }
                break;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {


    }
}
