package com.learning.microservices.userservice.repositories;

import com.learning.microservices.userservice.beans.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
