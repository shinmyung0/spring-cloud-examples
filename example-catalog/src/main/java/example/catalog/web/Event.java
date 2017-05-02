package example.catalog.web;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by shiny on 5/1/17.
 */
public class Event {
    private long id;
    private EventType type;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "MM-dd-yyyy hh:mm:ss")
    private Date date;

    private List<Artist> lineup;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artist> getLineup() {
        return lineup;
    }

    public void setLineup(List<Artist> lineup) {
        this.lineup = lineup;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
