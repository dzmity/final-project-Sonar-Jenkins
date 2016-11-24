package by.epam.filmrating.command.guest.search;

import by.epam.filmrating.entity.Entity;
import by.epam.filmrating.exception.ApplicationException;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.util.FilmRatingRegEx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code TablePager} class is a helper class.
 * The class is responsible for displaying information on the
 * jsp in a tabular format and page mode.
 * @author Dmitry Rafalovich
 */
class TablePager {

    private  static final Logger LOG = LogManager.getLogger();
    private  static final String HREF_ACTION = "/controller?";
    private  static final String HREF_COMMAND = "command=";
    private  static final String HREF_PAGE = "&page=";
    private  static final String PAGE = "page";
    private  static final String LIST_SIZE = "size";
    private  static final String NUMBER_FIELD = "number";
    private  static final String LEFT_COLUMN = "leftColumn";
    private  static final String RIGHT_COLUMN= "rightColumn";
    private  static final int FIRST_PAGE = 1;
    private  static final int DEFAULT_TABLE_SIZE = 10;
    private  static final int ROW_SIZE = 2;
    private  static final String LEFT_CLASS = "leftClass";
    private  static final String RIGHT_CLASS = "rightClass";
    private  static final String LEFT_HREF = "leftHref";
    private  static final String RIGHT_HREF = "rightHref";
    private  static final String DISABLED = "disabled";
    private  static final String NULL_HREF = "#";

    /**
     * The method fills {@cofe content} the necessary data for displaying
     * information according to page number on the jsp in a tabular format.
     * @param content
     *        object storing the necessary information from the request
     * @param tList
     *        list with model objects
     * @param part
     *        a part of command that will be installed on the left
     *        and right pager arrows
     * @param <T>
     *        parametrized type
     */
    static  <T extends Entity> void fillRequestWithAttributes(SessionRequestContent content, List<T> tList, String part) {

        int pageNumber = FIRST_PAGE;
        String page[] = content.getRequestParameters().get(PAGE);
        if (page != null) {
            try {
                FilmRatingRegEx.checkData(NUMBER_FIELD, page[0]);
                pageNumber = Integer.parseInt(page[0]);
            } catch (ApplicationException e) {
                LOG.error("Wrong format for 'page' parameter.", e);
            }
        }

        ArrayList<T> leftColumn = new ArrayList<>();
        ArrayList<T> rightColumn = new ArrayList<>();

        int listSize = tList.size();
        int pageCount = listSize / DEFAULT_TABLE_SIZE;
        if (listSize % DEFAULT_TABLE_SIZE != 0) {
            pageCount ++;
        }

        if (pageCount < pageNumber) {
            pageNumber = FIRST_PAGE;
        }

        int lineCount = DEFAULT_TABLE_SIZE / ROW_SIZE;

        if (listSize < pageNumber * DEFAULT_TABLE_SIZE) {

            if (listSize < (pageNumber * DEFAULT_TABLE_SIZE - lineCount )) {
                for (int i = (pageNumber - 1) * DEFAULT_TABLE_SIZE; i < listSize ; i++) {
                    leftColumn.add(tList.get(i));
                }
            } else {

                for (int i = (pageNumber - 1) * DEFAULT_TABLE_SIZE; i < pageNumber * DEFAULT_TABLE_SIZE - lineCount ; i++) {
                    leftColumn.add(tList.get(i));
                }
                for (int i = (pageNumber - 1) * DEFAULT_TABLE_SIZE + lineCount; i < listSize ; i++) {
                    rightColumn.add(tList.get(i));
                }
            }
        } else {

            for (int i = (pageNumber - 1) * DEFAULT_TABLE_SIZE; i < pageNumber * DEFAULT_TABLE_SIZE - lineCount ; i++) {
                    leftColumn.add(tList.get(i));
            }
            for (int i = (pageNumber - 1) * DEFAULT_TABLE_SIZE + lineCount; i < pageNumber * DEFAULT_TABLE_SIZE ; i++) {
                    rightColumn.add(tList.get(i));
            }
        }

        String command = HREF_ACTION + HREF_COMMAND + part + HREF_PAGE;

        String leftClass;
        String rightClass;
        String rightHref;
        String leftHref;
        int nextPage = pageNumber + 1;
        int previousPage = pageNumber - 1;

        if (listSize == 0) {
            leftClass = rightClass = DISABLED;
            leftHref = rightHref = NULL_HREF;

        } else if (pageNumber == FIRST_PAGE) {
            leftClass = DISABLED;
            leftHref = NULL_HREF;
            if (pageNumber == pageCount){
                rightClass = DISABLED;
                rightHref = NULL_HREF;
            } else {
                rightClass = "";
                rightHref = command + nextPage;
            }

        } else if (pageNumber == pageCount) {
            leftClass = "";
            leftHref = command + previousPage;
            rightClass = DISABLED;
            rightHref = NULL_HREF;
        } else {
            leftClass = rightClass = "";
            rightHref = command + nextPage;
            leftHref = command + previousPage;
        }

        content.setAttribute(RIGHT_COLUMN, rightColumn);
        content.setAttribute(LEFT_COLUMN, leftColumn);
        content.setAttribute(PAGE,pageNumber);
        content.setAttribute(LEFT_CLASS,leftClass);
        content.setAttribute(LEFT_HREF,leftHref);
        content.setAttribute(RIGHT_CLASS,rightClass);
        content.setAttribute(RIGHT_HREF,rightHref);
    }
}
