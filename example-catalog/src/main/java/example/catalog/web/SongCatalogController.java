package example.catalog.web;

import example.catalog.EntityToDtoConverter;
import example.catalog.datamodel.SongEntity;
import example.catalog.datamodel.SongRepository;
import example.catalog.exception.ResourceConflictException;
import example.catalog.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiyoon on 4/24/17.
 */
@RestController
@RequestMapping("songs")
public class SongCatalogController {

    private SongRepository songRepository;
    private ArtistCatalogController artistCatalogController;
    private EntityToDtoConverter entityToDtoConverter;

    @Autowired
    public SongCatalogController(SongRepository songRepository,
                                 ArtistCatalogController artistCatalogController,
                                 EntityToDtoConverter entityToDtoConverter) {
        this.songRepository = songRepository;
        this.artistCatalogController = artistCatalogController;
        this.entityToDtoConverter = entityToDtoConverter;
    }

    @GetMapping
    public List<Song> getAllSongsForArtist(
            @RequestParam(value = "artistId", required=false, defaultValue = "0") long id) {
        Iterable<SongEntity> entities;
        if (id == 0) {
            entities = songRepository.findAll();
        } else {
            entities = songRepository.findAllByArtistId(id);
        }

        List<Song> songs = new ArrayList<>();
        for (SongEntity entity : entities) {
            Artist artist = artistCatalogController.getArtist(entity.getArtistId());
            Song song = entityToDtoConverter.convertSongFromEntity(entity, artist);
            songs.add(song);

        }

        return songs;

    }

    @GetMapping("/{id}")
    public Song getSong(@PathVariable("id") long id) {

        SongEntity entity = songRepository.findOne(id);
        if (entity == null) {
            throw new ResourceNotFoundException();
        }

        Artist artist = artistCatalogController.getArtist(entity.getArtistId());


        return entityToDtoConverter.convertSongFromEntity(entity, artist);
    }

    @PostMapping
    public Song registerSong(Song song) {
        long id = song.getId();
        if (id != 0L && songRepository.exists(id)) {
            throw new ResourceConflictException();
        }

        SongEntity registeredSong = songRepository.save(entityToDtoConverter.convertSongToEntity(song));

        Artist artist = artistCatalogController.getArtist(registeredSong.getArtistId());

        return entityToDtoConverter.convertSongFromEntity(registeredSong, artist);
    }

    @PutMapping
    public Song updateSong(Song song) {
        long id = song.getId();

        if (!songRepository.exists(id)) {
            throw new ResourceNotFoundException();
        }

        SongEntity updatedSong = songRepository.save(entityToDtoConverter.convertSongToEntity(song));

        Artist artist = artistCatalogController.getArtist(updatedSong.getArtistId());

        return entityToDtoConverter.convertSongFromEntity(updatedSong, artist);
    }

    @DeleteMapping("/{id}")
    public void deleteSong(@PathVariable("id") long id) {

        if (!songRepository.exists(id)) {
            throw new ResourceNotFoundException();
        }

        songRepository.delete(id);

    }



}
