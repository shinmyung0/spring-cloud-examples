package example.catalog;

import example.catalog.datamodel.ArtistEntity;
import example.catalog.web.Artist;
import example.catalog.web.Gender;

/**
 * Created by shiyoon on 4/24/17.
 */
public class ControllerTestValues {

    static final long VALID_ARTIST_ID = 1L;
    static final long NONEXISTANT_SONG_ID = 99L;
    static final long NONEXISTANT_ARTIST_ID = 99L;
    static final long VALID_SONG_ID = 1L;

    static ArtistEntity getValidArtistEntity() {

        ArtistEntity entity = new ArtistEntity();
        entity.setId(VALID_ARTIST_ID);
        return entity;
    }

    static Artist getValidArtist() {
        Artist artist = new Artist();
        artist.setId(VALID_ARTIST_ID);
        return artist;
    }

    static Artist getNonExistantArtist() {
        Artist artist = new Artist();
        artist.setId(NONEXISTANT_ARTIST_ID);
        return artist;
    }

    static Artist getNewArtistWithoutId() {
        Artist artist = new Artist();
        artist.setAge(25);
        artist.setGender(Gender.Male);
        artist.setName("test");
        return artist;
    }


}
