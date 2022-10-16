package com.mykyta.restapi.service;

import com.mykyta.restapi.model.File;
import com.mykyta.restapi.repository.FileRepository;

import java.util.List;


public class FileService {
    private FileRepository fileRepository;

    public FileService(FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

    public List<File> getAllFiles(){
        return fileRepository.getAll();
    }

    public File getFileById(Integer id){
        return fileRepository.getById(id);
    }

    public File createFile(File file){;
        return fileRepository.create(file);
    }

    public File updateFile(File file){
        return  fileRepository.update(file);
    }

    public void deleteFileById(Integer id){
        fileRepository.deleteById(id);
    }
}
