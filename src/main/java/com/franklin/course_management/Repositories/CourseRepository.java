package com.franklin.course_management.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.franklin.course_management.Entities.Course;
import com.franklin.course_management.Entities.Teacher;

public interface CourseRepository extends CrudRepository<Course, Long> {

    Course findById(long id);
    
    Course findByName(String name);
    
    List<Course> findByTeacherId(long teacherId);
    
    @SuppressWarnings("null")
	List<Course> findAll();
 }