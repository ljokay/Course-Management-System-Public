package com.franklin.course_management.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;


@Entity
@Table(name = "teacher")
public class Teacher {
	
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long id;

    @NotEmpty
	@Column(name = "userId")
	public long userId;
    
    @NotEmpty
	@Column(name="credits")
	public int credits;
	
	public Teacher() {
	
	}
	
	public Teacher(long id, long userId, int credits) {
		this.id = id;
        this.userId = userId;
        this.credits = credits;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    public long getUserId() {
        return this.userId;
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
    
    
    
}
