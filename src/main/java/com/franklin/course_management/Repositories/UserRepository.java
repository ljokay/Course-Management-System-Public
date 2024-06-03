package com.franklin.course_management.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.franklin.course_management.Entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findById(long id);
 }