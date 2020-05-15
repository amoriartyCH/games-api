package games.api.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class Game extends BaseRest {

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

}
