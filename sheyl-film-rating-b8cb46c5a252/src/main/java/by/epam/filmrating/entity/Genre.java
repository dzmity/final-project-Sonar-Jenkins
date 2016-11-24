package by.epam.filmrating.entity;

/**
 * The {@code Genre} class is participant in Command Pattern.
 * The instances of this class represent a model.
 * @author Dmitry Rafalovich
 */
public class Genre extends Entity {

    private String genre;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        if (!super.equals(o)) return false;

        Genre genre1 = (Genre) o;

        return getGenre() != null ? getGenre().equals(genre1.getGenre()) : genre1.getGenre() == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getGenre() != null ? getGenre().hashCode() : 0);
        return result;
    }
}
