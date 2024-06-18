package com.franklin.course_management.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;


@Entity
@Table(name = "teacher_courses")
public class TeacherCourses {
	
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long id;

    @NotEmpty
	@Column(name = "teacher_id")
	public long teacherId;
    
    @NotEmpty
	@Column(name="course_id")
	public int courseId;
	
	public TeacherCourses() {
	
	}
	
	public TeacherCourses(long teacherId, int courseId) {
        this.teacherId = teacherId;
        this.courseId = courseId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(long teacherId) {
		this.teacherId = teacherId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	

    
    
}
