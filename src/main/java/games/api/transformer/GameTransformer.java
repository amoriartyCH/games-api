package games.api.transformer;

import games.api.model.entity.CommentEntity;
import games.api.model.entity.GameEntity;
import games.api.model.rest.Comment;
import games.api.model.rest.Game;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameTransformer {

    @Autowired
    CommentTransformer commentTransformer;

    public GameEntity transform(Game rest) {

        GameEntity entity = new GameEntity();

        BeanUtils.copyProperties(rest, entity);

        //Transform comments using a dedicated transformer as epoch conversion is needed
        if(!rest.getComments().isEmpty()) {
            List<CommentEntity> commentEntityList = new ArrayList<>();

            for (Comment c: rest.getComments()) {
                commentEntityList.add(commentTransformer.transform(c));
            }

            entity.setComments(commentEntityList);
        }

        return entity;
    }

    public Game transform(GameEntity entity) {

        Game rest = new Game();

        BeanUtils.copyProperties(entity, rest);

        //Transform comments using a dedicated transformer as epoch conversion is needed
        if(!entity.getComments().isEmpty()) {
            List<Comment> commentList = new ArrayList<>();

            for (CommentEntity ce: entity.getComments()) {
                commentList.add(commentTransformer.transform(ce));
            }

            rest.setComments(commentList);
        }

        return rest;
    }
}
