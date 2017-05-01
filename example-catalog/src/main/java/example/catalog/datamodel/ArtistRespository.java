package example.catalog.datamodel;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by shiyoon on 4/20/17.
 */
public interface ArtistRespository extends CrudRepository<ArtistEntity, Long> {
}
