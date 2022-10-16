package com.mykyta.restapi.service;

import com.mykyta.restapi.model.File;
import com.mykyta.restapi.model.User;
import com.mykyta.restapi.repository.UserRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class UserServiceTest extends TestCase {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);

    private UserService userService;

    @Before
    public void setUp(){
        userService = new UserService(userRepository);
    }

    public void testGetAllUsers() {
        List<User> users = Arrays.asList(new User(1, "Vasyl"),
                new User(2, "Petro"));

        given(userRepository.getAll()).willReturn(users);
        List<User> testResult = userService.getAllUsers();
        assertNotNull(testResult);
        assertEquals(users, testResult);
        assertEquals(2, testResult.size());
    }

    public void testGetUserById() {
        given(userRepository.getById(1)).willReturn(new User(1, "Vasyl"));
        User userTest = userService.getUserById(1);
        assertNotNull(userTest);
        assertEquals(userRepository.getById(1), userTest);
        assertNull(userService.getUserById(2));
    }

    public void testCreateUser() {
        User expected = new User(1, "Vasyl");
        given(userRepository.create(expected)).willReturn(expected);
        User actual = userService.createUser(expected);

        assertNotNull(expected);
        assertEquals(expected, actual);
    }

    public void testUpdateUser() {
        User withoutChangesExpected = new User(1, "Vasyl");
        given(userRepository.getById(1)).willReturn(withoutChangesExpected);
        given(userRepository.update(withoutChangesExpected)).willReturn(withoutChangesExpected);
        User withoutChangesActual = userService.getUserById(1);

        assertNotNull(withoutChangesActual);
        assertEquals(withoutChangesExpected, withoutChangesActual);

        String userName = "Petro";
        withoutChangesActual.setName(userName);
        userService.updateUser(withoutChangesActual);
        assertEquals(userName, withoutChangesActual.getName());
    }

    public void testDeleteUserById() {
        userService.deleteUserById(2);
        verify(userRepository).deleteById(2);
    }
}