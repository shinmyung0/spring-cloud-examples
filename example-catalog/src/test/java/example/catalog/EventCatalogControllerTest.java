package example.catalog;

import example.catalog.datamodel.EventEntity;
import example.catalog.datamodel.EventRepository;
import example.catalog.exception.ResourceConflictException;
import example.catalog.exception.ResourceNotFoundException;
import example.catalog.web.Event;
import example.catalog.web.EventCatalogController;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by shiny on 5/1/17.
 */
public class EventCatalogControllerTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private EventRepository mockEventRepository;

    @Mock
    private EntityToDtoConverter mockConverter;

    private EventCatalogController eventCatalogController;
    private final long validEventId = 1L;
    private final long nonExistantEventId = 99L;

    @Before
    public void setUp() {
        eventCatalogController = new EventCatalogController(mockEventRepository, mockConverter);

        when(mockEventRepository.findAll()).thenReturn(new ArrayList<>());

        when(mockEventRepository.findOne(validEventId)).thenReturn(new EventEntity());
        when(mockEventRepository.exists(validEventId)).thenReturn(true);
        when(mockEventRepository.exists(nonExistantEventId)).thenReturn(false);
    }


    @Test
    public void gettingAllShouldCallRepositoryFindAll() {
        List<Event> events = eventCatalogController.getAllEvents();
        verify(mockEventRepository).findAll();
    }

    @Test
    public void gettingEventShouldCallFindOneAndConvert() {
        Event event = eventCatalogController.getEvent(validEventId);
        verify(mockEventRepository).findOne(validEventId);
        verify(mockConverter).convertEventFromEntity(any(EventEntity.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void gettingNonExistantEventShouldThrowException() {
        Event event = eventCatalogController.getEvent(nonExistantEventId);
    }

    @Test(expected = ResourceConflictException.class)
    public void registeringConflictingEventShouldThrowException() {
        Event newEvent = new Event();
        newEvent.setId(validEventId);
        Event result = eventCatalogController.registerEvent(newEvent);

    }

    @Test
    public void registeringNonExistingEventShouldConvertCheckExistenceSaveThenConvert() {
        Event newEvent = new Event();
        Event result = eventCatalogController.registerEvent(newEvent);

        verify(mockConverter).convertEventToEntity(any(Event.class));
        verify(mockEventRepository).exists(anyLong());
        verify(mockEventRepository).save(any(EventEntity.class));
        verify(mockConverter).convertEventFromEntity(any(EventEntity.class));

    }

    @Test(expected = ResourceNotFoundException.class)
    public void updatingNonExistingEventShouldThrowException() {
        Event update = new Event();
        update.setId(nonExistantEventId);
        Event result = eventCatalogController.updateEvent(update);


    }

    @Test
    public void updatingValidEventShouldCheckExistConvertSaveThenConvert() {
        Event update = new Event();
        update.setId(validEventId);
        Event result = eventCatalogController.updateEvent(update);

        verify(mockEventRepository).exists(validEventId);
        verify(mockConverter).convertEventToEntity(any(Event.class));
        verify(mockEventRepository).save(any(EventEntity.class));
        verify(mockConverter).convertEventFromEntity(any(EventEntity.class));


    }

    @Test(expected = ResourceNotFoundException.class)
    public void deletingNonExistentEventShouldThrowException() {
        eventCatalogController.deleteEvent(nonExistantEventId);
    }

    @Test
    public void deletingValidEventShouldCheckExistThenDelete() {
        eventCatalogController.deleteEvent(validEventId);

        verify(mockEventRepository).exists(validEventId);
        verify(mockEventRepository).delete(validEventId);
    }



}
