package by.epam.filmrating.entity;

/**
 * The {@code Director} class is participant in Command Pattern.
 * The instances of this class represent a model.
 * @author Dmitry Rafalovich
 */
public class Director extends Entity {

    private String name;
    private String surname;
    private String photoPath;
    private Country country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

}
