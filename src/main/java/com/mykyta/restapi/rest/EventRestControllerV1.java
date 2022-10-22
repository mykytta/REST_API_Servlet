package com.mykyta.restapi.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mykyta.restapi.dto.EventDto;
import com.mykyta.restapi.model.Event;
import com.mykyta.restapi.model.File;
import com.mykyta.restapi.model.User;
import com.mykyta.restapi.repository.hibernate.HibernateEventRepository;
import com.mykyta.restapi.repository.hibernate.HibernateFileRepository;
import com.mykyta.restapi.repository.hibernate.HibernateUserRepository;
import com.mykyta.restapi.service.EventService;
import com.mykyta.restapi.service.FileService;
import com.mykyta.restapi.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class EventRestControllerV1 extends HttpServlet {
    private  EventService eventService;
    private UserService userService;
    private FileService fileService;

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    public void init() throws ServletException {
        eventService = new EventService(new HibernateEventRepository());
        userService = new UserService(new HibernateUserRepository());
        fileService = new FileService(new HibernateFileRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        if(pathInfo == null || pathInfo.equals("/")){
            List<Event> events = eventService.getAllEvents();
            String json = GSON.toJson(events.stream().map(EventDto::fromEntity).collect(Collectors.toList()));
            resp.getOutputStream().println(json);
            return;
        }

        Integer id = Integer.valueOf(req.getPathInfo().substring(1));
        Event event = eventService.getEventById(id);
        if(event == null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String json = GSON.toJson(EventDto.fromEntity(event));
        resp.getOutputStream().println(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");

        String eventName = req.getParameter("eventName");
        Integer fileId = Integer.valueOf(req.getParameter("fileId"));
        if(fileService.getFileById(fileId) == null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "You can't create event with this fileId");
            return;
        }
        File file = fileService.getFileById(fileId);
        Integer userId = Integer.valueOf(req.getParameter("userId"));
        if(userService.getUserById(userId) == null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "You can't create user with this userId");
            return;
        }
        User user = userService.getUserById(userId);


        Event event = new Event(null, eventName, file, user);
        eventService.createEvent(event);

        resp.setStatus(201);
        resp.getOutputStream().println(GSON.toJson(file));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();


        Integer eventId = Integer.valueOf(req.getParameter("id"));
        String eventName = req.getParameter("eventName");
        Integer fileId = Integer.valueOf(req.getParameter("fileId"));
        if(fileService.getFileById(fileId) == null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "You can't update event with this fileId");
            return;
        }
        File file = fileService.getFileById(fileId);
        Integer userId = Integer.valueOf(req.getParameter("userId"));
        if(userService.getUserById(userId) == null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "You can't update user with this userId");
            return;
        }
        User user = userService.getUserById(userId);

        Event event = new Event(eventId,eventName, file, user);
        eventService.updateEvent(event);
        resp.getOutputStream().println(GSON.toJson(file));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();


        Integer eventId = Integer.valueOf(req.getParameter("id"));

        if(fileService.getFileById(eventId) == null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "You can't delete event with id" + eventId + " because it doesn't exist");
            return;
        }

        Event event = eventService.getEventById(eventId);

        eventService.deleteEventById(eventId);

        resp.getOutputStream().println(GSON.toJson(event));
    }
}
