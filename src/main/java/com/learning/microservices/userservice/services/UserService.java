package com.learning.microservices.userservice.services;

import com.learning.microservices.userservice.beans.User;
import com.learning.microservices.userservice.exception.UserNotFoundException;
import com.learning.microservices.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserDetails(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        userRepository.findAll().forEach(userList::add);
        return userList;
    }

    public User addUser(User userFromRequest) {
        return userRepository.save(userFromRequest);
    }

    protected void setUserRepository(UserRepository repository){
        this.userRepository = repository;
    }
}
