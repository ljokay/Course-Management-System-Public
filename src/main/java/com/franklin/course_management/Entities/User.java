package com.franklin.course_management.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
public class User {
	
	public enum Role {
        STUDENT,
        TEACHER,
        ADMIN
    }
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long Id;
	
	@NotEmpty
    @Email
    @Column(name = "email", unique = true)
    private String email;
	
	@NotEmpty
	@Column(name = "username", unique = true)
	private String username;
	
	@NotEmpty
	@Column(name = "password")
	private String password;
	
	@NotEmpty
    @Column(name = "verified")
    private String verified;

    @NotEmpty
    @Column(name = "firstName")
    private String firstName;

    @NotEmpty
    @Column(name = "lastName")
    private String lastName;
    
    @NotEmpty
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
	
	
	
	
	public User() {
		verified = "N";
	}
	
	public User(String email, String username, String password, String verified, String firstName, String lastName, Role role) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.verified = verified;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}
	
	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String isVerified() {
		return verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	

}

