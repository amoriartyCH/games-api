package games.api.transformer;

import games.api.model.entity.GameEntity;
import games.api.model.rest.Game;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class GameTransformer implements GenericTransformer<Game, GameEntity> {

    @Override
    public GameEntity transform(Game rest) {

        GameEntity entity = new GameEntity();

        BeanUtils.copyProperties(rest, entity);

        return entity;
    }

    @Override
    public Game transform(GameEntity entity) {

        Game rest = new Game();

        BeanUtils.copyProperties(entity, rest);

        return rest;
    }
}
