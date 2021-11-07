package com.learning.microservices.userservice.services;

import com.learning.microservices.userservice.beans.User;
import com.learning.microservices.userservice.exception.UserNotFoundException;
import com.learning.microservices.userservice.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Service class tests")
class UserServiceTest {


    @MockBean
    private UserRepository repository;
    @InjectMocks
    private UserService service;

    @BeforeEach
    public void setup() {
        service.setUserRepository(repository);
    }

    @Nested
    @DisplayName("User details related tests")
    class Details {

        @Test
        void canGetUserDetails() {

            Optional<User> mockUser = Optional.of(new User(1l, "Mock User", "user@mock.com"));
            when(repository.findById(anyLong())).thenReturn(mockUser);
            User userDetails = service.getUserDetails(1l);
            assertAll(
                    () -> assertNotNull(userDetails),
                    () -> assertEquals(1l, userDetails.getUserid(), "Expects a long value"),
                    () -> assertEquals("Mock User", userDetails.getUserName(), "Mock user is expected"),
                    () -> assertEquals("user@mock.com", userDetails.getEmailId(), () -> "a value different than user@mock.com is not valid")
            );
        }

        @Test
        void canNotGetUserDetails(){
            when(repository.findById(anyLong())).thenThrow(new UserNotFoundException(2l));
            assertThrows(UserNotFoundException.class, ()->service.getUserDetails(2l));
        }
    }


    @Nested
    @DisplayName("Tests related to user list operations")
    class Users {
        User user1 = new User(1l,"Test User 1", "testuser@test.com");
        User user2 = new User(2l,"Test User 2", "testuser@test.com" );
        User user3 = new User(3l,"Test User 3", "testuser@test.com" );
        @Test
        void canGetAllUsers() {
            when(repository.findAll()).thenReturn(Arrays.asList(user1, user2, user3));
            List<User> allUsers = service.getAllUsers();
            assertAll(
                    ()->assertEquals(3, allUsers.size()),
                    () -> assertEquals(1l, allUsers.get(0).getUserid(), "Expects a long value"),
                    () -> assertEquals("Test User 1", allUsers.get(0).getUserName(), "Mock user is expected"),
                    () -> assertEquals("testuser@test.com", allUsers.get(0).getEmailId(), () -> "a value different than user@mock.com is not valid")
            );
        }
    }

    @Test
    void addUser() {
    }
}