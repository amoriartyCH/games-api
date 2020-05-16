package games.api.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class Game {

    @JsonProperty("title")
    @Length(min = 1, max = 30, message = "Outside of letter range constraints (min 1, max 30)")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("by")
    private String creator;

    @JsonProperty("platform")
    private List<String> platforms;

    @JsonProperty("age_rating")
    private Integer ageRating;

    @JsonProperty("likes")
    private Integer likes;

    @JsonProperty("comments")
    private List<Comment> comments;

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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
