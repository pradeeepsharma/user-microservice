package com.learning.microservices.userservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.microservices.userservice.beans.User;
import com.learning.microservices.userservice.exception.UserNotFoundException;
import com.learning.microservices.userservice.services.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static net.bytebuddy.matcher.ElementMatchers.is;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    User user1 = new User(1l,"Test User 1", "testuser@test.com");
    User user2 = new User(2l,"Test User 2", "testuser@test.com" );
    private User user3 = new User(3l,"Test User 3", "testuser@test.com" );

    @Test
    void getUser() throws Exception {
        Mockito.when(userService.getUserDetails(anyLong())).thenReturn(user1);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue(User.class)))
                .andExpect(jsonPath("$.userName").value("Test User 1"));
    }

    @Test
    void getUserWithError() throws Exception {
        Mockito.when(userService.getUserDetails(anyLong())).thenThrow(new UserNotFoundException(2l));
        mockMvc.perform(MockMvcRequestBuilders.get("/users/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                .andExpect(result -> assertEquals("input user not found :2",result.getResolvedException().getMessage()));
    }

    @Test
    void getUsers() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2, user3));
        mockMvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[1].userName").value("Test User 2"))
                ;
    }

    @Test
    void addUser() throws Exception {
        User reuestUser = new User(null,"Test User 1", "test email");
        Mockito.when(userService.addUser(reuestUser)).thenReturn(user1);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(reuestUser));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.userid", Matchers.is(1)));
    }



}