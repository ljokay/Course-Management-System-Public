package com.franklin.course_management.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.franklin.course_management.Entities.Assignment;

public interface AssignmentRepository extends CrudRepository<Assignment, Long> {

    Assignment findById(long id);
    
    List<Assignment> findByCourseId(long courseId);
 }