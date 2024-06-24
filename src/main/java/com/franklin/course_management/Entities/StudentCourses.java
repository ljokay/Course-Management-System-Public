package com.franklin.course_management.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;


@Entity
@Table(name = "student_courses")
public class StudentCourses {
	
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long id;

    @NotEmpty
	@Column(name = "student_id")
	public long studentId;
    
    @NotEmpty
	@Column(name="course_id")
	public int courseId;
	
	public StudentCourses() {
	
	}
	
	public StudentCourses(long studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
    
}
