package games.api.model.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public class CommentEntity {

    @Field("user")
    private String user;

    @Field("message")
    private String message;

    @Field("dateCreated")
    private Long dateCreated;

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

    public Long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
