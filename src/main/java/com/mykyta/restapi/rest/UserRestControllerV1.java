package com.mykyta.restapi.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mykyta.restapi.dto.UserDto;
import com.mykyta.restapi.model.User;
import com.mykyta.restapi.repository.hibernate.HibernateUserRepository;
import com.mykyta.restapi.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UserRestControllerV1 extends HttpServlet {
    private UserService userService;

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    public void init() throws ServletException {
        userService = new UserService(new HibernateUserRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        if(pathInfo == null || pathInfo.equals("/")){
            List<User> users = userService.getAllUsers();
            String json = GSON.toJson(users.stream().map(UserDto::fromEntity).collect(Collectors.toList()));
            resp.getOutputStream().println(json);
            return;
        }

        Integer id = Integer.valueOf(req.getPathInfo().substring(1));
        User user = userService.getUserById(id);

        if(user == null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String json = GSON.toJson(UserDto.fromEntity(user));
        resp.getOutputStream().println(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");

        String userName = req.getParameter("userName");

        User user = new User(null, userName);
        userService.createUser(user);

        resp.setStatus(201);
        resp.getOutputStream().println(GSON.toJson(user));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        Integer userId = Integer.valueOf(req.getParameter("id"));
        String userName = req.getParameter("userName");

        if(userService.getUserById(userId) == null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "You can't update user with id " + userId + " because it doesn't exist");
            return;
        }

        User user = new User(userId, userName);
        userService.updateUser(user);
        resp.getOutputStream().println(GSON.toJson(user));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();


        Integer userId = Integer.valueOf(req.getParameter("id"));

        if(userService.getUserById(userId) == null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "You can't delete user with id " + userId + " because it doesn't exist");
            return;
        }

        User user = userService.getUserById(userId);

        userService.deleteUserById(userId);

        resp.getOutputStream().println(GSON.toJson(user));
    }
}
