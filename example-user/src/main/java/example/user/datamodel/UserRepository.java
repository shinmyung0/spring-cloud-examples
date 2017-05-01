package example.user.datamodel;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by shiny on 4/25/17.
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
