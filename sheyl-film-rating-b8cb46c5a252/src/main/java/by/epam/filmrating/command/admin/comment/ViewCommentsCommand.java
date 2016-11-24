package by.epam.filmrating.command.admin.comment;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.exception.ApplicationException;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.entity.Comment;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.util.FilmRatingRegEx;
import by.epam.filmrating.service.CommentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDate;
import java.util.*;

/**
 * The {@code ViewCommentsCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible  for the transition to the comments.jsp.
 * @author Dmitry Rafalovich
 */
public class ViewCommentsCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String PATH = "path.page.admin.comments";
    private static final String FIRST_DATE = "firstDate";
    private static final String LAST_DATE = "lastDate";
    private static final String DATE = "date";
    private static final String COMMENTS = "comments";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the current page if a {@link ServiceException} occurred;
     *        to the comments.jsp in other cases.
     */
    @Override
    public String execute(SessionRequestContent content) {

        LocalDate firstDate;
        LocalDate lastDate;
        String[]  date1 = content.getRequestParameters().get(FIRST_DATE);
        String[] date2 = content.getRequestParameters().get(LAST_DATE);

        if (date1 != null && date2 != null) {
            try {
                FilmRatingRegEx.checkData(DATE, date1[0]);
                FilmRatingRegEx.checkData(DATE, date2[0]);
                firstDate = LocalDate.parse(date1[0]);
                lastDate = LocalDate.parse(date2[0]);
            } catch (ApplicationException e) {
                LOG.error("Exception in ViewCommentsCommand. Wrong date format from comments.jsp. Check regex!!");
                firstDate =  LocalDate.now();
                lastDate = firstDate.plusDays(1);
            }
        } else {
            firstDate =  LocalDate.now();
            lastDate = firstDate.plusDays(1);
        }

        try {
            CommentService commentService = new CommentService();
            List<Comment> comments = commentService.findCommentsByDate(firstDate, lastDate);
            content.setAttribute(FIRST_DATE, firstDate);
            content.setAttribute(LAST_DATE, lastDate);
            content.setAttribute(COMMENTS, comments);
            String fullPath = defineLastCommand(content);
            content.addSessionAttribute(CURRENT_PAGE, fullPath);
            return PathManager.getProperty(PATH);

        } catch(ServiceException e) {
            LOG.error("Exception in ViewCommentsCommand", e);
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }
    }
}
