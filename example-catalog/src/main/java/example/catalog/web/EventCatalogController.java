package example.catalog.web;

import example.catalog.EntityToDtoConverter;
import example.catalog.datamodel.EventEntity;
import example.catalog.datamodel.EventRepository;
import example.catalog.exception.ResourceConflictException;
import example.catalog.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiny on 5/1/17.
 */
@RestController
@RequestMapping("/events")
public class EventCatalogController {

    private final Logger logger = LoggerFactory.getLogger(EventCatalogController.class);

    private EventRepository eventRepository;
    private EntityToDtoConverter entityToDtoConverter;

    @Autowired
    public EventCatalogController(EventRepository eventRepository, EntityToDtoConverter entityToDtoConverter) {
        this.eventRepository = eventRepository;
        this.entityToDtoConverter = entityToDtoConverter;
    }


    @GetMapping
    public List<Event> getAllEvents() {

        Iterable<EventEntity> eventEntities = eventRepository.findAll();
        List<Event> events = new ArrayList<>();
        for (EventEntity e : eventEntities) {
            events.add(entityToDtoConverter.convertEventFromEntity(e));
        }

        return events;
    }

    @GetMapping("/{id}")
    public Event getEvent(@PathVariable("id") long id) {

        EventEntity eventEntity = eventRepository.findOne(id);
        if (eventEntity == null) {
            throw new ResourceNotFoundException();
        }

        return entityToDtoConverter.convertEventFromEntity(eventEntity);
    }

    @PostMapping
    public Event registerEvent(Event event) {

        long id = event.getId();
        if (eventRepository.exists(id)) {
            throw new ResourceConflictException();
        }

        EventEntity eventEntity = entityToDtoConverter.convertEventToEntity(event);
        EventEntity registered = eventRepository.save(eventEntity);

        return entityToDtoConverter.convertEventFromEntity(registered);
    }

    @PutMapping
    public Event updateEvent(Event event) {

        long id = event.getId();
        if (!eventRepository.exists(id)) {
            throw new ResourceNotFoundException();
        }

        EventEntity eventEntity = entityToDtoConverter.convertEventToEntity(event);
        EventEntity updated = eventRepository.save(eventEntity);
        return entityToDtoConverter.convertEventFromEntity(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable("id") long id) {
        if (!eventRepository.exists(id)) {
            throw new ResourceNotFoundException();
        }

        eventRepository.delete(id);
    }


}
