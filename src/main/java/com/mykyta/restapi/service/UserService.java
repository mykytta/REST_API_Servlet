package com.mykyta.restapi.service;

import com.mykyta.restapi.model.User;
import com.mykyta.restapi.repository.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.getAll();
    }

    public User getUserById(Integer id){
        return userRepository.getById(id);
    }

    public User createUser(User user){
        return userRepository.create(user);
    }

    public User updateUser(User user){
        return  userRepository.update(user);
    }

    public void deleteUserById(Integer id){
        userRepository.deleteById(id);
    }
}
