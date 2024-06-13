package com.franklin.course_management.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.franklin.course_management.Entities.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {

    Student findById(long id);
    
    Student findByUserId(long userId);
 }