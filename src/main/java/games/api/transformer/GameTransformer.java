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
    private CommentTransformer commentTransformer;

    public GameEntity transform(Game rest) {

        GameEntity entity = new GameEntity();

        BeanUtils.copyProperties(rest, entity);

        if(rest.getComments() != null) {
            List<CommentEntity> commentEntityList = new ArrayList<>();

            for (Comment c: rest.getComments()) {
                commentEntityList.add(commentTransformer.transform(c));
            }

            entity.setComments(commentEntityList);
        }

        return entity;
    }

    public Game transform(GameEntity entity) {

        if(entity == null) {
            return null;
        }

        Game rest = new Game();

        BeanUtils.copyProperties(entity, rest);

        if(entity.getComments() != null) {
            List<Comment> commentList = new ArrayList<>();

            for (CommentEntity ce: entity.getComments()) {
                commentList.add(commentTransformer.transform(ce));
            }

            rest.setComments(commentList);
        }

        return rest;
    }
}
