package com.franklin.course_management.Entities;

import jakarta.validation.constraints.NotEmpty;

import com.franklin.course_management.GradeLevelEnum.GradeLevel;

import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student {
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long Id;
	
	@NotEmpty
	@Column(name = "user_id")
	public long userId;
	
	@NotEmpty
	@Column(name="credits")
	public int credits;
	
	@NotEmpty
	@Enumerated(EnumType.STRING)
	@Column(name = "grade")
    private GradeLevel grade;
	
	
	public Student () {
		
	}
	
	public Student (Long userId, int credits, GradeLevel grade) {
		this.userId = userId;
		this.credits = credits;
		this.grade = grade;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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