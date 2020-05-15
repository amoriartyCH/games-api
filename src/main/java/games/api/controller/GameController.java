package games.api.controller;

import games.api.exception.DataException;
import games.api.model.rest.Game;
import games.api.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {

        List<Game> allGames = new ArrayList<>();

        try {
            allGames = gameService.getAll();

        } catch (DataException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok().body(allGames);
    }

    @GetMapping("/{GameTitle}")
    public ResponseEntity<Game> getSingleGame(@PathVariable("GameTitle") String gameTitle) {

        Game Game = new Game();

        try {
            Game = gameService.get(gameTitle);
        } catch (DataException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok().body(Game);
    }

    @PostMapping("/add")
    public ResponseEntity create(@Valid @RequestBody Game Game) {

        try {
            gameService.create(Game);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity update(@Valid @RequestBody Game Game) {

        try {
            gameService.update(Game);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (DataException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{GameTitle}")
    public ResponseEntity delete(@PathVariable("GameTitle") String gameTitle) {

        try {
            gameService.delete(gameTitle);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (DataException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
