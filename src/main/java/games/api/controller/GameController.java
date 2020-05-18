package games.api.controller;

import games.api.exception.DataException;
import games.api.model.rest.Game;
import games.api.model.rest.Report;
import games.api.service.GameServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameServiceImpl gameServiceImpl;

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {

        try {
            return ResponseEntity.ok().body(gameServiceImpl.getAll());
        } catch (DataException e) {
            logger.error("Encountered an error trying to retrieve all games, Data exception thrown: " +
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{GameTitle}")
    public ResponseEntity<Game> getSingleGame(@PathVariable("GameTitle") String gameTitle) {

        try {
            Game game = gameServiceImpl.get(gameTitle);

            if(game == null) {
                logger.error("Failed to retrieve game, null returned from service");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                return ResponseEntity.ok().body(game);
            }
        } catch (DataException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Game Game, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            logger.error("User has tried to submit a request body with more than 30 characters for the title of the game");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title more than 30 characters");
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(gameServiceImpl.create(Game));
        } catch (DataException e) {
            logger.error("Encountered an error trying to create a game resource: " +
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{GameTitle}")
    public ResponseEntity delete(@PathVariable("GameTitle") String gameTitle) {

        try {
            String status = gameServiceImpl.delete(gameTitle);

            if(status.equals("Game not found") || status.equals("failed")) {
                logger.error("No game exists for the title the user provided: " +
                        gameTitle);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (DataException e) {
            logger.error("Encountered an error trying to delete a game resource: " +
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //PART 2 - Game with highest likes and user with most comments.
    @GetMapping("/report")
    public ResponseEntity<Report> getReport() {

        try {
            return ResponseEntity.ok().body(gameServiceImpl.getReport());
        } catch (DataException e) {
            logger.error("Encountered an error trying to generate a report: " +
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
