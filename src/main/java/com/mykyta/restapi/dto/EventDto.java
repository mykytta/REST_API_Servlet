package com.mykyta.restapi.dto;

import com.mykyta.restapi.model.Event;

public class EventDto {

    private Integer id;
    private String name;
    private FileDto file;


    public EventDto() {
    }

    public EventDto(Integer id, String name, FileDto file) {
        this.id = id;
        this.name = name;
        this.file = file;
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

    public FileDto getFile() {
        return file;
    }

    public void setFile(FileDto file) {
        this.file = file;
    }

    public static EventDto fromEntity(Event event) {
        return new EventDto(event.getId(), event.getEventName(), FileDto.fromEntity(event.getFile()));
    }
}
