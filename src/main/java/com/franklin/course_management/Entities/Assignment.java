package com.franklin.course_management.Entities;


import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "assignment")
public class Assignment {
	
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long Id;
	
    @Column(name = "course_id")
    public long courseId;
    
    @NotEmpty
    @Column(name = "student_id")
    public long studentId;
    
    @NotEmpty
	@Column(name = "description")
	public String description;
	
	@NotEmpty
	@Column(name = "name")
	public String name;
	
	@NotEmpty
	@Column(name = "due_date")
	public Date dueDate;
	
	@NotEmpty
	@Column(name="points_earned")
	public Integer pointsEarned;
	
	@NotEmpty
	@Column(name="total_points")
	public int totalPoints;
	
	@NotEmpty
	@Column(name = "is_submitted")
	public String isSubmitted;
	
	
	
	public Assignment () {
		
	}
	
	public Assignment (long courseId, long studentId, String description, String name, Date dueDate, Integer pointsEarned, int totalPoints, String isSubmitted) {
		this.courseId = courseId;
		this.studentId = studentId;
		this.description = description;
		this.name = name;
		this.dueDate = dueDate;
		this.pointsEarned = pointsEarned;
		this.totalPoints = totalPoints;
		this.isSubmitted = isSubmitted;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
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

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Integer getPointsEarned() {
		return pointsEarned;
	}

	public void setPointsEarned(Integer pointsEarned) {
		this.pointsEarned = pointsEarned;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public String getIsSubmitted() {
		return isSubmitted;
	}

	public void setIsSubmitted(String isSubmitted) {
		this.isSubmitted = isSubmitted;
	}

	
	
}