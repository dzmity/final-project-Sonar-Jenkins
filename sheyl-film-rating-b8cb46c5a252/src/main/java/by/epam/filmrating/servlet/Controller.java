package by.epam.filmrating.servlet;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.factory.ActionFactory;
import by.epam.filmrating.pool.ConnectionPool;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The {@code Controller} class is instance of  abstract {@link HttpServlet} class.
 * The class is participant in MVC Pattern. It controls the data flow into
 * model object and updates the view whenever data changes. It keeps view and model separate.
 * @author Dmitry Rafalovich
 *
 */
@WebServlet("/controller")
@MultipartConfig
public class Controller extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);

    }

    /**
     * The method called by the server {@code Controller} to handle  GET and POST requests.
     * @param request
     *        an {@link HttpServletRequest} object that contains the request
     *        the client has made of the {@code Controller}
     * @param response
     *        an {@link HttpServletResponse} object that contains the response
     *        the {@code Controller} sends to the client
     * @throws ServletException
     *        if the request could not be handled
     * @throws IOException
     *        if an input or output error is detected when {@code Controller} handles the request
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        ActionFactory client = new ActionFactory();
        SessionRequestContent content = new SessionRequestContent();
        content.extractValues(request);
        ActionCommand command = client.defineCommand(content);
        String page = command.execute(content);
        content.insertAttributes(request);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().closePool();
    }
}
