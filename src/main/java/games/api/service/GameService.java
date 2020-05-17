package games.api.service;

import games.api.exception.DataException;
import games.api.model.rest.Game;
import games.api.model.rest.Report;

import java.util.List;

public interface GameService {

    /**
     * Creates a Game resource
     *
     * @return the created {@link Game} resource
     */
    Game create(Game g) throws DataException;

    /**
     * Fetches a single Game
     *
     * @return a {@link Game} resource
     */
    Game get(String title) throws DataException;

    /**
     * Fetches all Games
     *
     * @return a List of {@link Game} resources
     */
    List<Game> getAll() throws DataException;

    /**
     * Deletes a Game
     *
     * @return a {@link String} status
     */
    String delete(String title) throws DataException;

    /**
     * Fetches a Report
     *
     * @return a {@link Report} resource
     */
    Report getReport() throws DataException;
}
