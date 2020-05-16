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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class GameService {

    GameRepository repository;
    GameTransformer transformer;
    KeyIdGenerator keyIdGenerator;

    @Autowired
    public GameService(GameRepository repository, GameTransformer transformer, KeyIdGenerator keyIdGenerator) {

        this.repository = repository;
        this.transformer = transformer;
        this.keyIdGenerator = keyIdGenerator;
    }

    public Game create(Game rest) throws DataException {

        GameEntity entity = transformer.transform(rest);
        entity.setId(generateId(rest.getTitle()));

        try {
            repository.insert(entity);
        } catch (DuplicateKeyException e) {
            throw new DataException(e);
        }

        return rest;
    }

    public Game get(String gameTitle) throws DataException {

        GameEntity entity;

        try {
            entity = repository.findById(generateId(gameTitle)).orElse(null);
            return transformer.transform(entity);
        } catch (MongoException e) {
            throw new DataException(e);
        }
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
                return info.toString();
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

        report.setHighestLikedGame(getHighestLikedGame(allGames));

        report.setUserWithMostComments(getUserWithMostComments(allGames));

        return report;
    }

    private String getHighestLikedGame(List<Game> allGames) {

        return Collections.max(allGames, Comparator.comparingInt(Game::getLikes)).getTitle();
    }

    private String getUserWithMostComments(List<Game> allGames) {
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

        return maxEntry.getKey();
    }
}
