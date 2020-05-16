package games.api.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Report {

    @JsonProperty("highest-liked-game")
    String highestLikedGame;

    @JsonProperty("user-with-most-comments")
    String userWithMostComments;

    public String getHighestLikedGame() {
        return highestLikedGame;
    }

    public void setHighestLikedGame(String highestLikedGame) {
        this.highestLikedGame = highestLikedGame;
    }

    public String getUserWithMostComments() {
        return userWithMostComments;
    }

    public void setUserWithMostComments(String userWithMostComments) {
        this.userWithMostComments = userWithMostComments;
    }
}
