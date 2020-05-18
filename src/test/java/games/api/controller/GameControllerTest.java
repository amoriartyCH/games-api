package games.api.controller;

import games.api.exception.DataException;
import games.api.model.rest.Game;
import games.api.model.rest.Report;
import games.api.service.GameServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameControllerTest {

    @Mock
    private GameServiceImpl gameServiceImpl;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private GameController controller;

    private static final String GAME_TITLE = "gameTitle";

    @Test
    @DisplayName("Get all games - Success Path")
    void getAllGamesSuccess() throws DataException {

        List<Game> games = new ArrayList<>();
        when(gameServiceImpl.getAll()).thenReturn(games);

        ResponseEntity<List<Game>> response = controller.getAllGames();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Get all games - Throws Exception Path")
    void getAllGamesThrowsException() throws DataException {

        doThrow(new DataException("")).when(gameServiceImpl).getAll();

        ResponseEntity<List<Game>> response = controller.getAllGames();

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Get a single game - Success Path")
    void getASingleGameSuccess() throws DataException {

        Game game = new Game();
        when(gameServiceImpl.get(GAME_TITLE)).thenReturn(game);

        ResponseEntity<Game> response = controller.getSingleGame(GAME_TITLE);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Get a single game - Throws Exception Path")
    void getASingleGameThrowsException() throws DataException {

        doThrow(new DataException("")).when(gameServiceImpl).get(GAME_TITLE);

        ResponseEntity<Game> response = controller.getSingleGame(GAME_TITLE);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Create a game - Success Path")
    void createAGameSuccess() throws DataException {

        Game game = new Game();

        when(gameServiceImpl.create(game)).thenReturn(game);

        ResponseEntity<Game> response = controller.create(game, bindingResult);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Create a game - Throws Exception Path")
    void createAGameThrowsException() throws DataException {

        Game game = new Game();
        doThrow(new DataException("")).when(gameServiceImpl).create(game);

        ResponseEntity<Game> response = controller.create(game, bindingResult);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Delete a game - Success Path")
    void deleteAGameSuccess() throws DataException{
        when(gameServiceImpl.delete(GAME_TITLE)).thenReturn("success");

        ResponseEntity<Game> response = controller.delete(GAME_TITLE);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Delete a game - Throws Exception Path")
    void deleteAGameThrowsException() throws DataException {

        doThrow(new DataException("")).when(gameServiceImpl).delete(GAME_TITLE);

        ResponseEntity<Game> response = controller.delete(GAME_TITLE);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Get report - Success Path")
    void getReportSuccess() throws DataException {

        Report report = new Report();
        when(gameServiceImpl.getReport()).thenReturn(report);

        ResponseEntity<Report> response = controller.getReport();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Get report - Throws Exception Path")
    void getReportThrowsException() throws DataException {

        doThrow(new DataException("")).when(gameServiceImpl).getReport();

        ResponseEntity<Report> response = controller.getReport();

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

}
