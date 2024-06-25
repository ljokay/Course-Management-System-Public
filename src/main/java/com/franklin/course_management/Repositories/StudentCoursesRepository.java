package com.franklin.course_management.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.franklin.course_management.Entities.StudentCourses;

public interface StudentCoursesRepository extends CrudRepository<StudentCourses, Long> {

    StudentCourses findById(long id);
    
    List<StudentCourses> findByStudentId(long studentId);
 }