package games.api.transformer;

import games.api.model.entity.CommentEntity;
import games.api.model.entity.GameEntity;
import games.api.model.rest.Comment;
import games.api.model.rest.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameTransformerTest {

    private GameTransformer transformer = new GameTransformer();

    private static final String GAME_TITLE = "gameTitle";
    private static final Integer RATING = 16;
    private static final String CREATOR_NAME = "creatorName";
    private static final String DESCRIPTION = "description";
    private static final Integer LIKES = 100;

    private List<String> PLATFORMS = new ArrayList<>();

    @Test
    @DisplayName("Transform game REST to ENTITY - Success")
    void transformFromRestToEntitySuccess() {

        Game game = createGameRest();

        GameEntity entity = transformer.transform(game);

        assertEquals(game.getTitle(), entity.getTitle());
        assertEquals(game.getAgeRating(), entity.getAgeRating());
        assertEquals(game.getCreator(), entity.getCreator());
        assertEquals(game.getPlatforms(), entity.getPlatforms());
        assertEquals(game.getDescription(), entity.getDescription());
        assertEquals(game.getLikes(), entity.getLikes());
    }

    @Test
    @DisplayName("Transform game ENTITY to REST - Success")
    void transformFromEntityToRestSuccess() {

        GameEntity entity = createGameEntity();

        Game game = transformer.transform(entity);

        assertEquals(game.getTitle(), entity.getTitle());
        assertEquals(game.getAgeRating(), entity.getAgeRating());
        assertEquals(game.getCreator(), entity.getCreator());
        assertEquals(game.getPlatforms(), entity.getPlatforms());
        assertEquals(game.getDescription(), entity.getDescription());
        assertEquals(game.getLikes(), entity.getLikes());
    }

    private Game createGameRest() {
        Game game = new Game();

        game.setTitle(GAME_TITLE);
        game.setAgeRating(RATING);
        game.setCreator(CREATOR_NAME);
        game.setDescription(DESCRIPTION);
        game.setLikes(LIKES);

        PLATFORMS.add("platform");
        game.setPlatforms(PLATFORMS);

        return game;
    }

    private GameEntity createGameEntity() {
        GameEntity gameEntity = new GameEntity();

        gameEntity.setTitle(GAME_TITLE);
        gameEntity.setAgeRating(RATING);
        gameEntity.setCreator(CREATOR_NAME);
        gameEntity.setDescription(DESCRIPTION);
        gameEntity.setLikes(LIKES);

        PLATFORMS.add("platform");
        gameEntity.setPlatforms(PLATFORMS);

        return gameEntity;
    }
}
