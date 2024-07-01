package com.franklin.course_management.Entities;


import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.*;

import java.util.Date;

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
	@Column(name = "start_date")
	public Date startDate;
	
	@NotEmpty
	@Column(name = "end_date")
	public Date end_date;
	
	@NotEmpty
	@Column(name="credits")
	public int credits;
	
	@NotEmpty
	@Column(name="seats")
	public int seats;
	
	@NotEmpty
	@Enumerated(EnumType.STRING)
	@Column(name = "course_grade")
    private GradeLevel grade;
	
	
	public Course () {
		
	}
	
	public Course (long teacherId, String description, String name, int seats, int credits, GradeLevel grade, Date startDate, Date end_date) {
		this.teacherId = teacherId;
		this.description = description;
		this.name = name;
		this.seats = seats;
		this.credits = credits;
		this.grade = grade;
		this.startDate = startDate;
		this.end_date = end_date;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}
	
	

	
	
}