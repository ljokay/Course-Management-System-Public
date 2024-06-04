package com.franklin.course_management.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.franklin.course_management.Entities.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {

    Course findById(long id);
 }