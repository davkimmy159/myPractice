package kr.co.ps119.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	Long id;
	
	@Column
	String email;
	
	@Column
	String username;
	
	@Column
	String password;

	public Member() {
	}
	
	public Member(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public Member(Long id, String email, String password) {
		this.id = id;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	/*
	public void setId(Long id) {
		this.id = id;
	}
	*/
	
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
}