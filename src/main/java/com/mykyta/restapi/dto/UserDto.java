package com.mykyta.restapi.dto;

import com.mykyta.restapi.model.Event;
import com.mykyta.restapi.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {
    private Integer id;
    private String name;
    private List<EventDto> events;

    public UserDto() {
    }

    public UserDto(Integer id, String name, List<EventDto> events) {
        this.id = id;
        this.name = name;
        this.events = events;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EventDto> getEvents() {
        return events;
    }

    public void setEvents(List<EventDto> events) {
        this.events = events;
    }

    public static UserDto fromEntity(User user) {
        List<EventDto> eventDtos = user.getEvents().stream().map(EventDto::fromEntity).collect(Collectors.toList());
        return new UserDto(user.getId(), user.getName(), eventDtos);
    }
}
