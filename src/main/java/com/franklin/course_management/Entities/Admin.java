package com.franklin.course_management.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;


@Entity
@Table(name = "admin")
public class Admin {
	
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long id;

    @NotEmpty
	@Column(name = "userId")
	public long userId;
	
	public Admin() {
	
	}
	
	public Admin(long userId) {
        this.userId = userId;
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
    
}
