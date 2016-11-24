package by.epam.filmrating.command.admin.country;

import by.epam.filmrating.command.ActionCommand;
import by.epam.filmrating.servlet.SessionRequestContent;
import by.epam.filmrating.util.AccessInspector;
import by.epam.filmrating.entity.Country;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.CommandAccessException;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.manager.PathManager;
import by.epam.filmrating.service.CountryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;

/**
 * The {@code UpdateCountriesCommand} class is participant in Command Pattern.
 * It is a concrete class extends the {@link ActionCommand}.
 * The class is responsible for updating and creating {@link Country} objects.
 * @author Dmitry Rafalovich
 */
public class UpdateCountriesCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger();
    private static final String ID = "id";
    private static final String COUNTRY = "country";
    private static final String NEW_COUNTRY = "newCountry";

    /**
     *
     * @param content
     *        object storing the necessary information from the request
     * @return
     *        to the index.jsp if access denied for the current user;
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

        String[] countriesId = content.getRequestParameters().get(ID);
        String[] newCountries = content.getRequestParameters().get(NEW_COUNTRY);
        ArrayList<Country> countries = new ArrayList<>();

        for (String x : countriesId) {
            long id = new Long(x);
            String countryName = content.getRequestParameters().get(COUNTRY + id)[0];
            Country country = new Country();
            country.setId(id);
            country.setCountry(countryName);
            countries.add(country);
        }

        try {
            CountryService countryService = new CountryService();
            countryService.update(countries);
            for (String x : newCountries) {
                if (!x.isEmpty()) {
                    Country country = new Country();
                    country.setCountry(x);
                    countryService.create(country);
                }
            }

        } catch (ServiceException e) {
            LOG.error("Exception in UpdateCountriesCommand", e);
        }
        return (String) content.getSessionAttributes().get(CURRENT_PAGE);
    }
}
