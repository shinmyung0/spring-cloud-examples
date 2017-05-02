package example.catalog.datamodel;

import javax.persistence.*;

/**
 * Created by shiyoon on 4/24/17.
 */
@Entity
@Table(name = "songs")
public class SongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long artistId;
    private String title;
    private long length;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
