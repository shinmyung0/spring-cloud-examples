package example.catalog.web;

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

    @Autowired
    public SongCatalogController(SongRepository songRepository, ArtistCatalogController artistCatalogController) {
        this.songRepository = songRepository;
        this.artistCatalogController = artistCatalogController;
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
            Song song = convertSongFromEntity(entity);
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


        return convertSongFromEntity(entity);
    }

    @PostMapping
    public Song registerSong(Song song) {
        long id = song.getId();
        if (id != 0L && songRepository.exists(id)) {
            throw new ResourceConflictException();
        }

        SongEntity registeredSong = songRepository.save(convertSongToEntity(song));

        return convertSongFromEntity(registeredSong);
    }

    @PutMapping
    public Song updateSong(Song song) {
        long id = song.getId();

        if (!songRepository.exists(id)) {
            throw new ResourceNotFoundException();
        }

        SongEntity updatedSong = songRepository.save(convertSongToEntity(song));

        return convertSongFromEntity(updatedSong);
    }

    @DeleteMapping("/{id}")
    public void deleteSong(@PathVariable("id") long id) {

        if (!songRepository.exists(id)) {
            throw new ResourceNotFoundException();
        }

        songRepository.delete(id);

    }

    private Song convertSongFromEntity(SongEntity entity) {

        if (entity == null) {
            return null;
        }

        Song song = new Song();
        song.setId(entity.getId());
        Artist artist = artistCatalogController.getArtist(entity.getArtistId());
        song.setArtist(artist);
        song.setTitle(entity.getTitle());
        song.setLength(entity.getLength());
        return song;
    }

    private SongEntity convertSongToEntity(Song song) {

        if (song == null) {
            return null;
        }

        SongEntity entity = new SongEntity();
        entity.setId(song.getId());
        if (song.getArtist() != null) {
            entity.setArtistId(song.getArtist().getId());
        }
        entity.setTitle(song.getTitle());
        entity.setLength(song.getLength());
        return entity;
    }

}
