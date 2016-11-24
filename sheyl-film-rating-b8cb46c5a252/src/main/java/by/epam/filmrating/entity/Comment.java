package by.epam.filmrating.entity;

import java.time.LocalDateTime;

/**
 * The {@code Comment} class is participant in Command Pattern.
 * The instances of this class represent a model.
 * @author Dmitry Rafalovich
 */
public class Comment extends Entity {

    private String text;
    private LocalDateTime date;
    private User user;
    private Film film;

    public Comment() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

}
