package games.api.model.entity;

import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

public class CommentEntity extends BaseEntity{

    @Field("user")
    private String user;

    @Field("message")
    private String message;

    @Field("dateCreated")
    private LocalDate dateCreated;

    @Field("likes")
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
