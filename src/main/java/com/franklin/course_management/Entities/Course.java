package com.franklin.course_management.Entities;


import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.*;
import com.franklin.course_management.GradeLevelEnum.*;

@Entity
@Table(name = "course")
public class Course {
	
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long Id;
	
    @Column(name = "teacher_id")
    public long teacherId;
    
    @NotEmpty
	@Column(name = "description")
	public String description;
	
	@NotEmpty
	@Column(name = "name")
	public String name;
	
	@NotEmpty
	@Column(name="credits")
	public int credits;
	
	@NotEmpty
	@Enumerated(EnumType.STRING)
	@Column(name = "course_grade")
    private GradeLevel grade;
	
	
	public Course () {
		
	}
	
	public Course (long teacherId, String description, String name, int credits, GradeLevel grade) {
		this.teacherId = teacherId;
		this.description = description;
		this.name = name;
		this.credits = credits;
		this.grade = grade;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(long teacherId) {
		this.teacherId = teacherId;
	}
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public GradeLevel getGrade() {
		return grade;
	}

	public void setGrade(GradeLevel grade) {
		this.grade = grade;
	}

	
	
}