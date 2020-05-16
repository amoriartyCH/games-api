package games.api.controller;

import games.api.exception.DataException;
import games.api.model.rest.Game;
import games.api.model.rest.Report;
import games.api.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private GameService gameService;

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {

        try {
            return ResponseEntity.ok().body(gameService.getAll());

        } catch (DataException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{GameTitle}")
    public ResponseEntity<Game> getSingleGame(@PathVariable("GameTitle") String gameTitle) {

        try {
            return ResponseEntity.ok().body(gameService.get(gameTitle));
        } catch (DataException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Game Game) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(gameService.create(Game));
        } catch (DataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{GameTitle}")
    public ResponseEntity delete(@PathVariable("GameTitle") String gameTitle) {

        try {
            String status = gameService.delete(gameTitle);

            if(status.equals("Game not found") || status.equals("failed")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (DataException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //PART 2 - Game with highest likes and user with most comments.
    @GetMapping("/report")
    public ResponseEntity<Report> getReport() {

        try {
            return ResponseEntity.ok().body(gameService.getReport());
        } catch (DataException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
