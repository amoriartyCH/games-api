package games.api.service;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoException;
import games.api.exception.DataException;
import games.api.model.entity.GameEntity;
import games.api.model.rest.Comment;
import games.api.model.rest.Game;
import games.api.model.rest.Report;
import games.api.repository.GameRepository;
import games.api.transformer.GameTransformer;
import games.api.utility.impl.KeyIdGenerator;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    GameRepository repository;
    GameTransformer transformer;
    KeyIdGenerator keyIdGenerator;

    public GameService(GameRepository repository, GameTransformer transformer, KeyIdGenerator keyIdGenerator) {

        this.repository = repository;
        this.transformer = transformer;
        this.keyIdGenerator = keyIdGenerator;
    }

    public String create(Game rest) throws DataException {

        StringBuilder info = new StringBuilder();

        GameEntity entity = transformer.transform(rest);
        entity.setId(generateId(rest.getTitle()));

        try {
            repository.insert(entity);
        } catch (DuplicateKeyException e) {
            info.append("failed, ID already exists");
            throw new DataException(e);
        }

        info.append("success, resource created");

        return info.toString();
    }

    public Game get(String gameTitle) throws DataException {

        Game game;
        GameEntity entity;

        try {
            entity = repository.findById(generateId(gameTitle)).orElse(null);
            game = transformer.transform(entity);
        } catch (MongoException e) {
            throw new DataException(e);
        }

        return game;
    }

    public List<Game> getAll() throws DataException {

        List<Game> allGames = new ArrayList<>();
        List<GameEntity> entities;

        try {
            entities = repository.findAll();

            for(GameEntity entity : entities) {
                allGames.add(transformer.transform(entity));
            }
        } catch (MongoException e) {
            throw new DataException(e);
        }

        return allGames;
    }

    public String delete(String gameTitle) throws DataException {

        StringBuilder info = new StringBuilder();

        String gameId = generateId(gameTitle);

        try {
            if(repository.existsById(gameId)) {
                repository.deleteById(gameId);
                info.append("successfully deleted game");
            } else {
                info.append("Game not found");
            }
        } catch (MongoException e) {
            info.append("failed");
            throw new DataException(e);
        }

        return info.toString();
    }

    private String generateId(String gameTitle) {
        return keyIdGenerator.generate(gameTitle);
    }

    //Part 2 - Report (Game with most likes and user with most comments).
    public Report getReport() throws DataException {

        Report report = new Report();

        List<Game> allGames = getAll();

        report.setHighestLikedGame(Collections.max(
                allGames,
                Comparator.comparingInt(Game::getLikes)).getTitle());

        HashMap<String, Integer> usersWithComments = new HashMap<>();

        for (Game g : allGames) {
            for (Comment c : g.getComments()) {
                Integer count = usersWithComments.get(c.getUser());

                if (count == null) {
                    usersWithComments.put(c.getUser(), 1);
                }
                else {
                    usersWithComments.put(c.getUser(), count + 1);
                }
            }
        }

        Map.Entry<String, Integer> maxEntry =
                Collections.max(usersWithComments.entrySet(), new Comparator<Map.Entry<String, Integer>>() {
                    public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                        return e1.getValue().compareTo(e2.getValue());
            }
        });

        report.setUserWithMostComments(maxEntry.getKey());

        return report;
    }
}
