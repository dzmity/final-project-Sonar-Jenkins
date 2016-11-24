package by.epam.filmrating.command;

import by.epam.filmrating.command.admin.LoadImageCommand;
import by.epam.filmrating.command.admin.comment.BanCommentCommand;
import by.epam.filmrating.command.admin.comment.ViewCommentsCommand;
import by.epam.filmrating.command.admin.actor.*;
import by.epam.filmrating.command.admin.country.UpdateCountriesCommand;
import by.epam.filmrating.command.admin.country.UpdateGenresCommand;
import by.epam.filmrating.command.admin.country.ViewCountriesGenresCommand;
import by.epam.filmrating.command.admin.director.*;
import by.epam.filmrating.command.admin.film.*;
import by.epam.filmrating.command.admin.user.*;
import by.epam.filmrating.command.guest.*;
import by.epam.filmrating.command.guest.search.FindAllActorsCommand;
import by.epam.filmrating.command.guest.search.FindAllDirectorsCommand;
import by.epam.filmrating.command.guest.search.FindAllFilmsCommand;
import by.epam.filmrating.command.guest.objectview.ViewActorCommand;
import by.epam.filmrating.command.guest.objectview.ViewDirectorCommand;
import by.epam.filmrating.command.guest.objectview.ViewFilmCommand;
import by.epam.filmrating.command.guest.search.FindFilmsByYearCommand;
import by.epam.filmrating.command.user.*;
import by.epam.filmrating.command.guest.search.FindFilmsByActorCommand;
import by.epam.filmrating.command.guest.search.FindFilmsByDirectorCommand;
import by.epam.filmrating.command.guest.search.FindFilmsByGenreCommand;
import by.epam.filmrating.command.guest.search.FindFilmsByTitleCommand;

/**
 * The enumeration class {@code CommandEnum} includes enum constants
 * with names exactly matches the value of 'command' parameter in
 * request. Each member of class contains definite class that implements
 * {@link ActionCommand}, that realizes its own actions.
 * @author Dmitry Rafalovich
 *
 */
public enum CommandEnum {

    /**
     * Contains {@code StartCommand} class.
     */
    START {
        {
            this.command = new StartCommand();
        }
    },
    /**
     * Contains {@code ViewFilmCommand} class.
     */
    VIEW_FILM{
        {
            this.command = new ViewFilmCommand();
        }
    },
    /**
     * Contains {@code AddCommentCommand} class.
     */
    ADD_COMMENT{
        {
            this.command = new AddCommentCommand();
        }
    },
    /**
     * Contains {@code ViewActorCommand} class.
     */
    VIEW_ACTOR {
        {
            this.command = new ViewActorCommand();
        }

    },
    /**
     * Contains {@code ViewDirectorCommand} class.
     */
    VIEW_DIRECTOR {
        {
            this.command = new ViewDirectorCommand();
        }
    },
    /**
     * Contains {@code ChangeLanguageCommand} class.
     */
    CHANGE_LANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }
    },
    /**
     * Contains {@code FindAllDirectorsCommand} class.
     */
    FIND_ALL_DIRECTORS {
        {
            this.command = new FindAllDirectorsCommand();
        }
    },
    /**
     * Contains {@code FindAllActorsCommand} class.
     */
    FIND_ALL_ACTORS {
        {
            this.command = new FindAllActorsCommand();
        }
    },
    /**
     * Contains {@code FindAllFilmsCommand} class.
     */
    FIND_ALL_FILMS {
        {
            this.command = new FindAllFilmsCommand();
        }
    },
    /**
     * Contains {@code FindFilmsByAdminCommand} class.
     */
    FIND_FILMS_BY_ADMIN {
        {
            this.command = new FindFilmsByAdminCommand();
        }
    },
    /**
     * Contains {@code ChangeFilmCommand} class.
     */
    CHANGE_FILM{
        {
            this.command = new ChangeFilmCommand();
        }
    },
    /**
     * Contains {@code UpdateFilmCommand} class.
     */
    UPDATE_FILM{
        {
            this.command = new UpdateFilmCommand();
        }
    },
    /**
     * Contains {@code DeleteFilmCommand} class.
     */
    DELETE_FILM{
        {
            this.command = new DeleteFilmCommand();
        }
    },
    /**
     * Contains {@code CreateFilmCommand} class.
     */
    CREATE_FILM{
        {
            this.command = new CreateFilmCommand();
        }
    },
    /**
     * Contains {@code AddFilmCommand} class.
     */
    ADD_FILM{
        {
            this.command = new AddFilmCommand();
        }
    },
    /**
     * Contains {@code FindActorsByAdminCommand} class.
     */
    FIND_ACTORS_BY_ADMIN {
        {
            this.command = new FindActorsByAdminCommand();
        }
    },
    /**
     * Contains {@code ChangeActorCommand} class.
     */
    CHANGE_ACTOR{
        {
            this.command = new ChangeActorCommand();
        }
    },
    /**
     * Contains {@code UpdateActorCommand} class.
     */
    UPDATE_ACTOR{
        {
            this.command = new UpdateActorCommand();
        }
    },
    /**
     * Contains {@code DeleteActorCommand} class.
     */
    DELETE_ACTOR{
        {
            this.command = new DeleteActorCommand();
        }
    },
    /**
     * Contains {@code CreateActorCommand} class.
     */
    CREATE_ACTOR{
        {
            this.command = new CreateActorCommand();
        }
    },
    /**
     * Contains {@code AddActorCommand} class.
     */
    ADD_ACTOR{
        {
            this.command = new AddActorCommand();
        }
    },
    /**
     * Contains {@code FindDirectorsByAdminCommand} class.
     */
    FIND_DIRECTORS_BY_ADMIN {
        {
            this.command = new FindDirectorsByAdminCommand();
        }
    },
    /**
     * Contains {@code ChangeDirectorCommand} class.
     */
    CHANGE_DIRECTOR{
        {
            this.command = new ChangeDirectorCommand();
        }
    },
    /**
     * Contains {@code UpdateDirectorCommand} class.
     */
    UPDATE_DIRECTOR{
        {
            this.command = new UpdateDirectorCommand();
        }
    },
    /**
     * Contains {@code DeleteDirectorCommand} class.
     */
    DELETE_DIRECTOR{
        {
            this.command = new DeleteDirectorCommand();
        }
    },
    /**
     * Contains {@code CreateDirectorCommand} class.
     */
    CREATE_DIRECTOR{
        {
            this.command = new CreateDirectorCommand();
        }
    },
    /**
     * Contains {@code AddDirectorCommand} class.
     */
    ADD_DIRECTOR{
        {
            this.command = new AddDirectorCommand();
        }
    },
    /**
     * Contains {@code ViewCommentsCommand} class.
     */
    VIEW_COMMENTS{
        {
            this.command = new ViewCommentsCommand();
        }
    },
    /**
     * Contains {@code BanCommentCommand} class.
     */
    BAN_COMMENT{
        {
            this.command = new BanCommentCommand();
        }
    },
    /**
     * Contains {@code UserInfoCommand} class.
     */
    USER_INFO{
        {
            this.command = new UserInfoCommand();
        }
    },
    /**
     * Contains {@code FindAllUsersCommand} class.
     */
    FIND_ALL_USERS{
        {
            this.command = new FindAllUsersCommand();
        }
    },
    /**
     * Contains {@code BanUserCommand} class.
     */
    BAN_USER{
        {
            this.command = new BanUserCommand();
        }
    },
    /**
     * Contains {@code LiftBanCommand} class.
     */
    LIFT_BAN_USER{
        {
            this.command = new LiftBanCommand();
        }
    },
    /**
     * Contains {@code UpgradeStatusCommand} class.
     */
    UPGRADE_STATUS{
        {
            this.command = new UpgradeStatusCommand();
        }
    },
    /**
     * Contains {@code ViewCountriesGenresCommand} class.
     */
    VIEW_COUNTRIES_GENRES{
        {
            this.command = new ViewCountriesGenresCommand();
        }
    },
    /**
     * Contains {@code UpdateCountriesCommand} class.
     */
    UPDATE_COUNTRIES{
        {
            this.command = new UpdateCountriesCommand();
        }
    },
    /**
     * Contains {@code UpdateGenresCommand} class.
     */
    UPDATE_GENRES{
        {
            this.command = new UpdateGenresCommand();
        }
    },
    /**
     * Contains {@code SignUpCommand} class.
     */
     SIGN_UP{
        {
            this.command = new SignUpCommand();
        }
    },
    /**
     * Contains {@code RegistrationCommand} class.
     */
    REGISTRATION{
        {
            this.command = new RegistrationCommand();
        }
    },
    /**
     * Contains {@code GoToLoginCommand} class.
     */
    GO_TO_LOGIN{
        {
            this.command = new GoToLoginCommand();
        }
    },
    /**
     * Contains {@code LogInCommand} class.
     */
    LOG_IN{
        {
            this.command = new LogInCommand();
        }
    },
    /**
     * Contains {@code LogOutCommand} class.
     */
    LOG_OUT {
        {
            this.command = new LogOutCommand();
        }
    },
    /**
     * Contains {@code AddMarkCommand} class.
     */
    ADD_MARK{
        {
            this.command = new AddMarkCommand();
        }
    },
    /**
     * Contains {@code UserSettingsCommand} class.
     */
    USER_SETTINGS{
        {
            this.command = new UserSettingsCommand();
        }
    },
    /**
     * Contains {@code UpdateUserCommand} class.
     */
    UPDATE_USER{
        {
            this.command = new UpdateUserCommand();
        }
    },
    /**
     * Contains {@code ChangePasswordCommand} class.
     */
    CHANGE_PASSWORD{
        {
            this.command = new ChangePasswordCommand();
        }
    },
    /**
     * Contains {@code UpdateUserImageCommand} class.
     */
    UPDATE_USER_IMAGE{
        {
            this.command = new UpdateUserImageCommand();
        }
    },
    /**
     * Contains {@code LoadImageCommand} class.
     */
    LOAD_IMAGE{
        {
            this.command = new LoadImageCommand();
        }
    },
    /**
     * Contains {@code FindFilmsByYearCommand} class.
     */
    FIND_FILMS_BY_YEAR{
        {
            this.command = new FindFilmsByYearCommand();
        }
    },
    /**
     * Contains {@code FindFilmsByGenreCommand} class.
     */
    FIND_FILMS_BY_GENRE{
        {
            this.command = new FindFilmsByGenreCommand();
        }
    },
    /**
     * Contains {@code FindFilmsByDirectorCommand} class.
     */
    FIND_FILMS_BY_DIRECTOR{
        {
            this.command = new FindFilmsByDirectorCommand();
        }
    },
    /**
     * Contains {@code FindFilmsByActorCommand} class.
     */
    FIND_FILMS_BY_ACTOR{
        {
            this.command = new FindFilmsByActorCommand();
        }
    },
    /**
     * Contains {@code FindFilmsByTitleCommand} class.
     */
    FIND_FILMS_BY_TITLE{
        {
            this.command = new FindFilmsByTitleCommand();
        }
    };

    ActionCommand command;

    /**
     *  The method returns field {@code command} of enum constant.
     * @return an instance of definite command class
     */
    public ActionCommand getCurrentCommand() {
        return command;
    }
}
