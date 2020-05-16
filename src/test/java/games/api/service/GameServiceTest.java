package games.api.service;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoException;
import games.api.exception.DataException;
import games.api.model.entity.CommentEntity;
import games.api.model.entity.GameEntity;
import games.api.model.rest.Comment;
import games.api.model.rest.Game;
import games.api.repository.GameRepository;
import games.api.transformer.GameTransformer;
import games.api.utility.impl.KeyIdGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameServiceTest {

    @Mock
    private GameTransformer transformer;

    @Mock
    private GameRepository repository;

    @Mock
    private KeyIdGenerator generator;

    @InjectMocks
    private GameService service;

    private static final String GAME_TITLE = "gameTitle";
    private static final Integer RATING = 16;
    private static final String CREATOR_NAME = "creatorName";
    private static final String DESCRIPTION = "description";
    private static final Integer LIKES = 100;
    private static final String USER = "user";

    private static final LocalDate DATE = LocalDate.of(2019, 1, 1);
    private static final Long EPOCH_DATE = 1546330002000L;

    private List<String> PLATFORMS = new ArrayList<>();
    private List<Comment> COMMENTS_REST = new ArrayList<>();
    private List<CommentEntity> COMMENTS_ENTITY = new ArrayList<>();

    private static final String GENERATED_ID = "generatedId";

    @Test
    @DisplayName("Create game - Success Path")
    void createGameSuccess() throws DataException {

        Game game = createGameRest();
        GameEntity gameEntity = createGameEntity();

        when(transformer.transform(game)).thenReturn(gameEntity);
        when(generator.generate(GAME_TITLE)).thenReturn(GENERATED_ID);

        String response = service.create(game);

        assertEquals(response, "success, resource created");
        assertRepositoryInsertCalled(gameEntity);

    }

    @Test
    @DisplayName("Create game - Throws Exception Path")
    void createGameThrowsException() {

        Game game = createGameRest();
        GameEntity gameEntity = createGameEntity();
        String response = "failed";

        when(transformer.transform(game)).thenReturn(gameEntity);
        when(generator.generate(GAME_TITLE)).thenReturn(GENERATED_ID);
        when(repository.insert(gameEntity)).thenThrow(DuplicateKeyException.class);

        Throwable exception = assertThrows(DataException.class, () -> service.create(game));

        assertEquals(response, "failed");
        assertRepositoryInsertCalled(gameEntity);
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Delete game - Success Path")
    void deleteGameSuccess() throws DataException {

        when(generator.generate(GAME_TITLE)).thenReturn(GENERATED_ID);
        when(repository.existsById(GENERATED_ID)).thenReturn(true);

        String response = service.delete(GAME_TITLE);

        assertEquals(response, "successfully deleted game");
        assertRepositoryDeleteByIdCalled();
    }

    @Test
    @DisplayName("Delete game - No Movie Found Path")
    void deleteGameNoGameFound() throws DataException {

        when(generator.generate(GAME_TITLE)).thenReturn(GENERATED_ID);
        when(repository.existsById(GENERATED_ID)).thenReturn(false);

        String response = service.delete(GAME_TITLE);

        assertEquals(response, "Game not found");
    }

    @Test
    @DisplayName("Delete game - Throws Exception Path")
    void deleteGameThrowsException() throws DataException {

        when(generator.generate(GAME_TITLE)).thenReturn(GENERATED_ID);
        when(repository.existsById(GENERATED_ID)).thenReturn(true);
        doThrow(new MongoException("")).when(repository).deleteById(GENERATED_ID);

        Throwable exception = assertThrows(DataException.class, () -> service.delete(GAME_TITLE));

        assertNotNull(exception);
        assertRepositoryDeleteByIdCalled();
    }

    @Test
    @DisplayName("Get game - Success Path")
    void getMovieSuccess() throws DataException {

        Game game = createGameRest();
        GameEntity movieEntity = createGameEntity();

        when(transformer.transform(movieEntity)).thenReturn(game);
        when(generator.generate(GAME_TITLE)).thenReturn(GENERATED_ID);
        when(repository.findById(GENERATED_ID)).thenReturn(ofNullable(movieEntity));

        Game responseGame = service.get(GAME_TITLE);

        assertEquals(game.getTitle(), responseGame.getTitle());
        assertEquals(game.getAgeRating(), responseGame.getAgeRating());
        assertEquals(game.getCreator(), responseGame.getCreator());
        assertEquals(game.getPlatforms(), responseGame.getPlatforms());
        assertEquals(game.getComments(), responseGame.getComments());
        assertEquals(game.getDescription(), responseGame.getDescription());
        assertEquals(game.getLikes(), responseGame.getLikes());

        assertRepositoryFindByIdCalled();
    }

    @Test
    @DisplayName("Get game - Throws Exception Path")
    void getGameThrowsException() throws DataException {

        when(generator.generate(GAME_TITLE)).thenReturn(GENERATED_ID);
        doThrow(new MongoException("")).when(repository).findById(GENERATED_ID);

        Throwable exception = assertThrows(DataException.class, () -> service.get(GAME_TITLE));

        assertNotNull(exception);
        assertRepositoryFindByIdCalled();
    }

    @Test
    @DisplayName("Get all games - Success Path")
    void getAllGamesSuccess() throws DataException {

        List<GameEntity> gameEntities = new ArrayList<>();
        gameEntities.add(createGameEntity());
        Game gameRest = createGameRest();

        when(repository.findAll()).thenReturn(gameEntities);
        when(transformer.transform(gameEntities.get(0))).thenReturn(gameRest);

        List<Game> responseGames = service.getAll();

        assertEquals(gameEntities.get(0).getTitle(), responseGames.get(0).getTitle());
        assertEquals(gameEntities.get(0).getAgeRating(), responseGames.get(0).getAgeRating());
        assertEquals(gameEntities.get(0).getCreator(), responseGames.get(0).getCreator());
        assertEquals(gameEntities.get(0).getPlatforms(), responseGames.get(0).getPlatforms());
        assertEquals(gameEntities.get(0).getDescription(), responseGames.get(0).getDescription());
        assertEquals(gameEntities.get(0).getLikes(), responseGames.get(0).getLikes());
    }

    @Test
    @DisplayName("Get all games - Throws Exception Path")
    void findAllMoviesThrowsException() throws DataException {

        doThrow(new MongoException("")).when(repository).findAll();

        Throwable exception = assertThrows(DataException.class, () -> service.getAll());
        assertNotNull(exception);
    }

    private void assertRepositoryInsertCalled(GameEntity gameEntity) {
        verify(repository, times(1)).insert(gameEntity);
    }
    private void assertRepositoryFindByIdCalled() {
        verify(repository, times(1)).findById(GENERATED_ID);
    }
    private void assertRepositoryDeleteByIdCalled() {
        verify(repository, times(1)).deleteById(GENERATED_ID);
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

        COMMENTS_REST.add(createCommentRest());
        game.setComments(COMMENTS_REST);

        return game;
    }

    private GameEntity createGameEntity() {
        GameEntity gameEntity = new GameEntity();

        gameEntity.setTitle(GAME_TITLE);
        gameEntity.setAgeRating(RATING);
        gameEntity.setCreator(CREATOR_NAME);
        gameEntity.setDescription(DESCRIPTION);
        gameEntity.setLikes(LIKES);

        COMMENTS_ENTITY.add(createCommentEntity());
        gameEntity.setComments(COMMENTS_ENTITY);

        PLATFORMS.add("platform");
        gameEntity.setPlatforms(PLATFORMS);

        return gameEntity;
    }

    private Comment createCommentRest() {
        Comment comment = new Comment();

        comment.setLikes(LIKES);
        comment.setMessage(DESCRIPTION);
        comment.setUser(USER);
        comment.setDateCreated(DATE);

        return comment;
    }

    private CommentEntity createCommentEntity() {
        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setLikes(LIKES);
        commentEntity.setMessage(DESCRIPTION);
        commentEntity.setUser(USER);
        commentEntity.setDateCreated(EPOCH_DATE);

        return commentEntity;
    }
}
