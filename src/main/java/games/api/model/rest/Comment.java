package games.api.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;


public class Comment {

    @JsonProperty("user")
    private String user;

    @JsonProperty("message")
    private String message;

    @JsonProperty("dateCreated")
    private LocalDate dateCreated;

    @JsonProperty("likes")
    private Integer likes;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
