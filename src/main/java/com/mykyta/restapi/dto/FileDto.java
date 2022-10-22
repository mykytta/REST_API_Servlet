package com.mykyta.restapi.dto;

import com.mykyta.restapi.model.File;

public class FileDto {
    private Integer id;
    private String name;
    private String path;


    public FileDto() {

    }

    public FileDto(Integer id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static FileDto fromEntity(File file) {
        return new FileDto(file.getId(), file.getFileName(), file.getFilePath());
    }

    public File toEntity() {
        return new File(this.id, this.name, this.path);
    }
}
