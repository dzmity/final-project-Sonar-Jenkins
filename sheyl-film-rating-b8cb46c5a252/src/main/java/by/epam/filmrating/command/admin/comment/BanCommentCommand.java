package by.epam.filmrating.command.admin.comment;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.entity.Comment;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.util.AccessInspector;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.CommandAccessException;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.util.ActionEvaluator;
import by.epam.filmrating.service.CommentService;
import by.epam.filmrating.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code BanCommentCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for banning comments.
 * @author Dmitry Rafalovich
 */
public class BanCommentCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String ID = "id";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the index.jsp if access denied for the current user or
     *        missing 'Id' parameter in the {@code content};
     *        to the current page in other cases.
     */
    @Override
    public String execute(SessionRequestContent content) {

        User user = (User) content.getSessionAttributes().get(CURRENT_USER);
        try {
            AccessInspector.checkAdminAccess(user);
        } catch (CommandAccessException e) {
            LOG.error("Access denied.", e);
            return PathManager.getProperty(INDEX_PATH);
        }

        if (content.getRequestParameters().get(ID) == null) {
            LOG.error("Exception in BanCommentCommand. Missing Id parameter.");
            return PathManager.getProperty(INDEX_PATH);
        }

        long id =  new Long(content.getRequestParameters().get(ID)[0]);


        try {
            CommentService commentService = new CommentService();
            commentService.banComment(id);
            Comment comment = commentService.findById(id);
            UserService userService = new UserService();
            userService.addMarkById(comment.getUser().getId(), ActionEvaluator.BAN_COMMENT);

        } catch (ServiceException e) {
            LOG.error("Exception in BanCommentCommand", e);
        }

        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }
}
