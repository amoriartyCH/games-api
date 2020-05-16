package games.api.transformer;

import games.api.model.entity.CommentEntity;
import games.api.model.rest.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentTransformerTest {

    private CommentTransformer transformer = new CommentTransformer();

    private static final String MESSAGE = "message";
    private static final Integer LIKES = 100;
    private static final String USER = "user";
    private static final LocalDate DATE = LocalDate.of(2019, 1, 1);
    private static final Long EPOCH_DATE = 1546330002000L;

    @Test
    @DisplayName("Transform Comment REST to ENTITY - Success")
    void transformFromRestToEntitySuccess() {

        Comment comment = createCommentRest();

        CommentEntity entity = transformer.transform(comment);

        assertEquals(entity.getLikes(), comment.getLikes());
        assertEquals(entity.getUser(), comment.getUser());
        assertEquals(entity.getMessage(), comment.getMessage());
    }

    @Test
    @DisplayName("Transform Comment ENTITY to REST - Success")
    void transformFromREntityToRestSuccess() {

        CommentEntity entity = createCommentEntity();

        Comment comment = transformer.transform(entity);

        assertEquals(comment.getDateCreated(), DATE);
        assertEquals(comment.getUser(), entity.getUser());
        assertEquals(comment.getLikes(), entity.getLikes());
        assertEquals(comment.getMessage(), entity.getMessage());
    }

    private Comment createCommentRest() {
        Comment comment = new Comment();

        comment.setLikes(LIKES);
        comment.setMessage(MESSAGE);
        comment.setUser(USER);
        comment.setDateCreated(DATE);

        return comment;
    }

    private CommentEntity createCommentEntity() {
        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setLikes(LIKES);
        commentEntity.setMessage(MESSAGE);
        commentEntity.setUser(USER);
        commentEntity.setDateCreated(EPOCH_DATE);

        return commentEntity;
    }
}
