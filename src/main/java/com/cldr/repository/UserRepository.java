package com.cldr.repository;

import org.springframework.data.repository.CrudRepository;
import com.cldr.user.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
