package by.epam.filmrating.entity;

import java.util.Date;

/**
 * The {@code Actor} class is participant in Command Pattern.
 * The instances of this class represent a model.
 * @author Dmitry Rafalovich
 */
public class Actor extends Entity {

    private String name;
    private String surname;
    private int height;
    private  Date dateOfBirth;
    private String photoPath;

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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor)) return false;
        if (!super.equals(o)) return false;

        Actor actor = (Actor) o;

        if (getHeight() != actor.getHeight()) return false;
        if (getName() != null ? !getName().equals(actor.getName()) : actor.getName() != null) return false;
        if (getSurname() != null ? !getSurname().equals(actor.getSurname()) : actor.getSurname() != null) return false;
        if (getDateOfBirth() != null ? !getDateOfBirth().equals(actor.getDateOfBirth()) : actor.getDateOfBirth() != null)
            return false;
        return getPhotoPath() != null ? getPhotoPath().equals(actor.getPhotoPath()) : actor.getPhotoPath() == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        result = 31 * result + getHeight();
        result = 31 * result + (getDateOfBirth() != null ? getDateOfBirth().hashCode() : 0);
        result = 31 * result + (getPhotoPath() != null ? getPhotoPath().hashCode() : 0);
        return result;
    }
}
