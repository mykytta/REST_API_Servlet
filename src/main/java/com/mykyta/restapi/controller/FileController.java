package com.mykyta.restapi.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mykyta.restapi.model.File;
import com.mykyta.restapi.repository.rest.RestFileRepository;
import com.mykyta.restapi.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FileController extends HttpServlet {

    FileService fileService;

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    public void init() throws ServletException {
        fileService = new FileService(new RestFileRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        if(pathInfo == null || pathInfo.equals("/")){
            String json = GSON.toJson(fileService.getAllFiles());
            resp.getOutputStream().println(json);
            return;
        }

        Integer id = Integer.valueOf(req.getPathInfo().substring(1));
        if(fileService.getFileById(id) == null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String json = GSON.toJson(fileService.getFileById(id));
        resp.getOutputStream().println(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");


        String fileName = req.getParameter("fileName");
        String filePath = req.getParameter("filePath");

        File file = new File(null, fileName,filePath);
        fileService.createFile(file);

        resp.setStatus(201);
        resp.getOutputStream().println(GSON.toJson(file));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        Integer fileId = Integer.valueOf(req.getParameter("id"));
        String fileName = req.getParameter("fileName");
        String filePath = req.getParameter("filePath");

        if(fileService.getFileById(fileId) == null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "You can't update file with id " + fileId + " because it doesn't exist");
            return;
        }

        File file = new File(fileId, fileName, filePath);
        fileService.updateFile(file);
        resp.getOutputStream().println(GSON.toJson(file));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();


        Integer fileId = Integer.valueOf(req.getParameter("id"));

        if(fileService.getFileById(fileId) == null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "You can't delete file with id " + fileId + " because it doesn't exist");
            return;
        }

        File file = fileService.getFileById(fileId);

        fileService.deleteFileById(fileId);

        resp.getOutputStream().println(GSON.toJson(file));
    }
}
