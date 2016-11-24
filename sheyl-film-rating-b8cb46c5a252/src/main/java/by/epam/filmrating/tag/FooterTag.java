package by.epam.filmrating.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.GregorianCalendar;

/**
 * The {@code AdminFooterTag} class represents the custom tag {@code footer}
 * in {@code customtags} library used in jsp-fills
 * @author Dmitry Rafalovich
 *
 */
public class FooterTag extends TagSupport {

    private static final String CONTAINER = "<div class=\"container\">" ;
    private static final String ROW = "<div class=\"row\">";
    private static final String NAVBAR = "<div class=\"navbar navbar-inverse navbar-static-bottom\">";
    private static final String FLUID_CONTAINER = "<div class=\"container-fluid\">";
    private static final String UL_NAVBAR_RIGHT = "<ul class=\"nav navbar-nav navbar-right\">";
    private static final String UL_NAVBAR = "<ul class=\"nav navbar-nav \">";
    private static final String OPEN_LI_A = "<li><a href=\"#\">";
    private static final String CALENDAR = "<span class=\"glyphicon glyphicon-calendar\"></span>";
    private static final String GLOBE = "<span class=\"glyphicon glyphicon-globe\"></span>";
    private static final String CLOSE_LI_A = "</a></li>";
    private static final String CLOSE_UL = "</ul>";
    private static final String CLOSE_DIV = "</div>";

    private String locale;

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int doStartTag() throws JspException {
        GregorianCalendar gc = new GregorianCalendar();
        try {
            JspWriter out = pageContext.getOut();
            out.write(CONTAINER);
            out.write(ROW);
            out.write(NAVBAR);
            out.write(FLUID_CONTAINER);
            out.write(UL_NAVBAR_RIGHT);
            out.write(OPEN_LI_A);
            out.write(CALENDAR);
            out.write("  " + gc.getTime());
            out.write(CLOSE_LI_A);
            out.write(OPEN_LI_A);
            out.write(GLOBE);
            out.write("  " + locale);
            out.write(CLOSE_LI_A);
            out.write(CLOSE_UL);
            out.write(UL_NAVBAR);
            out.write(OPEN_LI_A);
        }catch (IOException e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doAfterBody() throws JspException {

        try {
            JspWriter out = pageContext.getOut();
            out.write(CLOSE_LI_A);
            out.write(CLOSE_UL);
            out.write(CLOSE_DIV);
            out.write(CLOSE_DIV);
            out.write(CLOSE_DIV);
            out.write(CLOSE_DIV);

        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
