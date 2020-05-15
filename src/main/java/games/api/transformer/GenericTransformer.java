package games.api.transformer;

import games.api.model.entity.BaseEntity;
import games.api.model.rest.BaseRest;

public interface GenericTransformer<R extends BaseRest, E extends BaseEntity> {

    //Transform a Rest object into an Entity object for DB storage.
    E transform(R rest);

    //Transform an Entity object into a Rest object for application use.
    R transform(E entity);

}
