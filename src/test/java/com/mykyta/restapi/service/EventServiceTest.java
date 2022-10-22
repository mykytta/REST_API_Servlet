package com.mykyta.restapi.service;

import com.mykyta.restapi.model.Event;
import com.mykyta.restapi.model.File;
import com.mykyta.restapi.repository.EventRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class EventServiceTest extends TestCase {

    private final EventRepository eventRepository = Mockito.mock(EventRepository.class);

    private EventService eventService;

    @Before
    public void setUp(){
        eventService = new EventService(eventRepository);
    }

    public void testGetAllEvents() {
        List<Event> events = Arrays.asList(new Event(1, "Smth", new File(1, "Document", "/main"), null),
                new Event(1, "AddingUserInfo", new File(2, "UserInfo", "/bin"), null));

        given(eventRepository.getAll()).willReturn(events);
        List<Event> testResult = eventService.getAllEvents();
        assertNotNull(testResult);
        assertEquals(events, testResult);
        assertEquals(2, testResult.size());
    }

    public void testGetEventById() {
        given(eventRepository.getById(1)).willReturn(new Event(1, "Smth", new File(1, "Document", "/main"), null));
        Event testEvent = eventService.getEventById(1);
        assertNotNull(testEvent);
        assertEquals(eventRepository.getById(1), testEvent);
        assertNull(eventService.getEventById(2));
    }

    public void testCreateEvent() {
        Event expected = new Event(1, "Smth", new File(1, "Document", "/main"), null);
        given(eventRepository.create(expected)).willReturn(expected);
        Event actual = eventService.createEvent(expected);

        assertNotNull(expected);
        assertEquals(expected, actual);
    }

    public void testUpdateEvent() {
        Event withoutChangesExpected = new Event(1, "Smth", new File(1, "Document", "/main"), null);
        given(eventRepository.getById(1)).willReturn(withoutChangesExpected);
        given(eventRepository.update(withoutChangesExpected)).willReturn(withoutChangesExpected);
        Event withoutChangesActual = eventService.getEventById(1);

        assertNotNull(withoutChangesActual);
        assertEquals(withoutChangesExpected, withoutChangesActual);

        File file = new File(2, "Picture", "/");
        withoutChangesActual.setFile(file);
        eventService.updateEvent(withoutChangesActual);
        assertEquals(file, withoutChangesActual.getFile());
    }

    public void testDeleteEventById() {
        eventService.deleteEventById(2);
        verify(eventRepository).deleteById(2);
    }
}
