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
	public int pointsEarned;
	
	@NotEmpty
	@Column(name="total_points")
	public int totalPoints;
	
	
	public Assignment () {
		
	}
	
	public Assignment (long courseId, String description, String name, Date dueDate, int pointsEarned, int totalPoints) {
		this.courseId = courseId;
		this.description = description;
		this.name = name;
		this.dueDate = dueDate;
		this.pointsEarned = pointsEarned;
		this.totalPoints = totalPoints;
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

	public int getPointsEarned() {
		return pointsEarned;
	}

	public void setPointsEarned(int pointsEarned) {
		this.pointsEarned = pointsEarned;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	

	
	
}