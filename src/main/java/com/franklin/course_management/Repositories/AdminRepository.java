package com.franklin.course_management.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.franklin.course_management.Entities.Admin;

public interface AdminRepository extends CrudRepository<Admin, Long> {

    Admin findById(long id);
 }