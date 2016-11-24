package by.epam.filmrating.command.admin.country;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.entity.Country;
import by.epam.filmrating.entity.Genre;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.service.CountryService;
import by.epam.filmrating.service.GenreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

/**
 * The {@code ViewCountriesGenresCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible  for the transition to the country_genre.jsp.
 * @author Dmitry Rafalovich
 */
public class ViewCountriesGenresCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String PATH = "path.page.admin.auxiliary";
    private static final String COUNTRIES = "countries";
    private static final String GENRES = "genres";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the current page if an error occurred;
     *        to the country_genre.jsp if there was no errors.
     */
    @Override
    public String execute(SessionRequestContent content) {

        try {
            CountryService countryService = new CountryService();
            GenreService genreService = new GenreService();
            List<Country> countries = countryService.findAll();
            List<Genre> genres = genreService.findAll();
            content.setAttribute(COUNTRIES, countries);
            content.setAttribute(GENRES, genres);

        } catch (ServiceException e) {
            LOG.error("Exception in ViewCountriesGenresCommand", e);
            return (String) content.getSessionAttributes().get(CURRENT_PAGE);
        }

        String fullPath = defineLastCommand(content);
        content.addSessionAttribute(CURRENT_PAGE, fullPath);
        return PathManager.getProperty(PATH);
    }
}
