package games.api.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Document(collection = "games")
public class GameEntity {

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("by")
    private String creator;

    @Field("platform")
    private List<String> platforms;

    @Field("age_rating")
    private Integer ageRating;

    @Field("likes")
    private Integer likes;

    @Field("comments")
    private List<CommentEntity> comments;

    @Field("links")
    private Map<String, Object> links;

    public Map<String, Object> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Object> links) {
        this.links = links;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }

    public Integer getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(Integer ageRating) {
        this.ageRating = ageRating;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }
}
