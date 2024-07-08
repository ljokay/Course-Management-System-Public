package com.franklin.course_management.Entities;


import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.*;

@Entity
@Table(name = "student_assignments")
public class StudentAssignments {
	
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long Id;
	
    @Column(name = "student_id")
    public long studentId;
    
    @NotEmpty
	@Column(name = "assignment_id")
	public long assignmentId;
	
	@NotEmpty
	@Column(name = "is_submitted")
	public String isSubmitted;
	
	
	public StudentAssignments () {
		
	}
	
	public StudentAssignments (long Id, long studentId, long assignmentId, String isSubmitted) {
		this.Id = Id;
		this.studentId = studentId;
		this.assignmentId = assignmentId;
		this.isSubmitted = isSubmitted;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public long studentId() {
		return studentId;
	}

	public void setCourseId(long studentId) {
		this.studentId = studentId;
	}

	public long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(long assignmentId) {
		this.assignmentId = assignmentId;
	}

	public String getIsSubmitted() {
		return isSubmitted;
	}

	public void setIsSubmitted(String isSubmitted) {
		this.isSubmitted = isSubmitted;
	}
}