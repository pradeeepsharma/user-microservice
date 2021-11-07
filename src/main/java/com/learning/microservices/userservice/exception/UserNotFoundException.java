package com.learning.microservices.userservice.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super();
    }
    public UserNotFoundException(Long userId){
        super("input user not found :"+userId);
    }
}
