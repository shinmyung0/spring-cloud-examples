package example.catalog.datamodel;

import org.springframework.data.repository.CrudRepository;


/**
 * Created by shiny on 5/1/17.
 */
public interface EventRepository extends CrudRepository<EventEntity, Long> {
}
