package example.catalog;

import example.catalog.datamodel.ArtistEntity;
import example.catalog.datamodel.EventEntity;
import example.catalog.datamodel.SongEntity;
import example.catalog.web.Artist;
import example.catalog.web.Event;
import example.catalog.web.Song;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiny on 5/1/17.
 */
@Component
public class EntityToDtoConverter {

    public Artist convertArtistFromEntity(ArtistEntity entity) {

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

    public ArtistEntity convertArtistToEntity(Artist artist) {

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

    public Song convertSongFromEntity(SongEntity entity, Artist artist) {

        if (entity == null) {
            return null;
        }

        Song song = new Song();
        song.setId(entity.getId());
        song.setArtist(artist);
        song.setTitle(entity.getTitle());
        song.setLength(entity.getLength());
        return song;
    }

    public SongEntity convertSongToEntity(Song song) {

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

    public Event convertEventFromEntity(EventEntity entity) {
        Event event = new Event();
        event.setId(entity.getId());
        event.setName(entity.getName());
        event.setType(entity.getType());
        event.setDate(entity.getDate());
        List<Artist> lineup = new ArrayList<>();
        for (ArtistEntity a : entity.getLineup()) {
            lineup.add(convertArtistFromEntity(a));
        }
        event.setLineup(lineup);
        return event;
    }

    public EventEntity convertEventToEntity(Event event) {
        EventEntity entity = new EventEntity();
        entity.setId(event.getId());
        entity.setName(event.getName());
        entity.setType(event.getType());
        entity.setDate(event.getDate());
        List<ArtistEntity> lineup = new ArrayList<>();
        for (Artist a : event.getLineup()) {
            lineup.add(convertArtistToEntity(a));
        }
        entity.setLineup(lineup);
        return entity;
    }
}
