package com.franklin.course_management.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.franklin.course_management.Entities.Teacher;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {

    Teacher findById(long id);
    
    Teacher findByUserId(long userId);
    
    @SuppressWarnings("null")
	List<Teacher> findAll();
 }