package example.catalog.web;

import example.catalog.datamodel.ArtistEntity;
import example.catalog.datamodel.ArtistRespository;
import example.catalog.exception.ResourceConflictException;
import example.catalog.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiyoon on 4/20/17.
 */
@RestController
@RequestMapping("/artists")
public class ArtistCatalogController {

    private final ArtistRespository artistRespository;

    private static Logger log = LoggerFactory.getLogger(ArtistCatalogController.class);

    @Autowired
    public ArtistCatalogController(ArtistRespository artistRespository) {
        this.artistRespository = artistRespository;
    }


    @GetMapping
    public List<Artist> getAllArtists() {

        log.info("Getting all Artists...");

        Iterable<ArtistEntity> entities = artistRespository.findAll();
        List<Artist> allArtists = new ArrayList<>();
        for (ArtistEntity entity : entities) {
            Artist artist = convertArtistFromEntity(entity);
            allArtists.add(artist);
        }
        return allArtists;
    }

    @GetMapping("/{id}")
    public Artist getArtist(@PathVariable("id") Long id) {
        ArtistEntity artistEntity = artistRespository.findOne(id);

        if (artistEntity == null) {
            throw new ResourceNotFoundException();
        }

        return convertArtistFromEntity(artistEntity);
    }

    @PostMapping
    public Artist registerArtist(@RequestBody Artist artist) {
        long id = artist.getId();
        if (id != 0 && artistRespository.exists(id)) {
            throw new ResourceConflictException();
        }

        ArtistEntity saved = artistRespository.save(convertArtistToEntity(artist));

        return convertArtistFromEntity(saved);
    }

    @PutMapping
    public Artist updateArtist(@RequestBody Artist artist) {
        long id = artist.getId();
        if (!artistRespository.exists(id)) {
            throw new ResourceNotFoundException();
        }

        ArtistEntity saved = artistRespository.save(convertArtistToEntity(artist));
        return convertArtistFromEntity(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteArtist(@PathVariable long id) {
        if (!artistRespository.exists(id)) {
            throw new ResourceNotFoundException();
        }
        artistRespository.delete(id);
    }


    private Artist convertArtistFromEntity(ArtistEntity entity) {

        if (entity == null) {
            return null;
        }

        Artist artist = new Artist();
        artist.setId(entity.getId());
        artist.setName(entity.getName());
        artist.setAge(entity.getAge());
        artist.setGender(entity.getGender());
        return artist;
    }

    private ArtistEntity convertArtistToEntity(Artist artist) {

        if (artist == null) {
            return null;
        }

        ArtistEntity entity = new ArtistEntity();
        entity.setId(artist.getId());
        entity.setName(artist.getName());
        entity.setAge(artist.getAge());
        entity.setGender(artist.getGender());
        return entity;
    }
}
