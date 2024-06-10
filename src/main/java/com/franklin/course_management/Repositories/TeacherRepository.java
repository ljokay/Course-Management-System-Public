package com.franklin.course_management.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.franklin.course_management.Entities.Teacher;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {

    Teacher findById(long id);
 }