package games.api.transformer;

import games.api.model.entity.CommentEntity;
import games.api.model.rest.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Component
public class CommentTransformer {

    public CommentEntity transform(Comment rest) {

        CommentEntity entity = new CommentEntity();

        BeanUtils.copyProperties(rest, entity);

        Instant instant = rest.getDateCreated().atStartOfDay(ZoneId.systemDefault()).toInstant();
        entity.setDateCreated(instant.toEpochMilli());

        return entity;
    }

    public Comment transform(CommentEntity entity) {
        Comment rest = new Comment();

        BeanUtils.copyProperties(entity, rest);

        LocalDate dateCreate =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(entity.getDateCreated()), ZoneId.systemDefault()).toLocalDate();
        rest.setDateCreated(dateCreate);

        return rest;
    }
}
