package com.mykyta.restapi.service;

import com.mykyta.restapi.model.Event;
import com.mykyta.restapi.repository.EventRepository;

import java.util.List;

public class EventService {
    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents(){
        return eventRepository.getAll();
    }

    public Event getEventById(Integer id){
        return eventRepository.getById(id);
    }

    public Event createEvent(Event event){
        return eventRepository.create(event);
    }

    public Event updateEvent(Event event){
        return  eventRepository.update(event);
    }

    public void deleteEventById(Integer id){
        eventRepository.deleteById(id);
    }
}
