package example.catalog.datamodel;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by shiyoon on 4/24/17.
 */
public interface SongRepository extends CrudRepository<SongEntity, Long> {

    List<SongEntity> findAllByArtistId(Long id);
}
