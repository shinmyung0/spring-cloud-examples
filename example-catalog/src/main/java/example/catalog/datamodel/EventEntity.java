package example.catalog.datamodel;

import example.catalog.web.EventType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by shiny on 5/1/17.
 */
@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private EventType type;
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "events_artists",
               joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"))
    private List<ArtistEntity> lineup;

    public List<ArtistEntity> getLineup() {
        return lineup;
    }

    public void setLineup(List<ArtistEntity> lineup) {
        this.lineup = lineup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
