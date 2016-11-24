package by.epam.filmrating.command.guest;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.servlet.SessionRequestContent;

/**
 * The {@code ChangeLanguageCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for changing language in the web -application.
 * @author Dmitry Rafalovich
 */
public class ChangeLanguageCommand implements ActionCommand {

    private  static final String LOCALE = "locale";
    private  static final String ALTERNATIVE_LOCALE = "altLocale";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the current page.
     */
    @Override
    public String execute(SessionRequestContent content) {

        String language = (String) content.getSessionAttributes().get(LOCALE);
        String altLanguage = (String) content.getSessionAttributes().get(ALTERNATIVE_LOCALE);

        content.addSessionAttribute(LOCALE,altLanguage);
        content.addSessionAttribute(ALTERNATIVE_LOCALE,language);

        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }
}
