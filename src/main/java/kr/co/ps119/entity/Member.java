package kr.co.ps119.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Member implements Serializable {
	
	private static final long serialVersionUID = 3443687366576503018L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;
	
	@Column
	@NotBlank(message = "email is empty")
	@Email(message = "email is invalid")
	private String email;
	
	@Column
	@NotBlank(message = "username is empty")
	@Length(min = 8,
			max = 40,
			message = "must have value between 8 and 40 included")
	private String username;
	
	@Column
	@NotBlank(message = "passwrod is empty")
	@Length(min = 8,
			max = 40,
			message = "must have value between 8 and 40 included")
	private String password;
	
	@Column
	@NotNull
	private boolean enabled;
	
	@OneToMany(mappedBy = "member",
			   fetch = FetchType.LAZY,
			   cascade = CascadeType.ALL,
			   orphanRemoval = true)
	@JsonBackReference
	private List<Board> boards = new ArrayList<>();
	
	public Member() {
	}
	
	public Member(String email, String username, String password) {
		this(email, username, password, true);
	}
	
	public Member(String email, String username, String password, boolean enabled) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Board> getBoards() {
		return boards;
	}

	public void addBoard(Board board) {
		boards.add(board);
		
		if(board.getMember() != this) {
			board.setMember(this);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Member))
			return false;
		Member other = (Member) obj;
		if (getEmail() == null) {
			if (other.email != null)
				return false;
		} else if (!getEmail().equals(other.email))
			return false;
		if (isEnabled() != other.enabled)
			return false;
		if (getPassword() == null) {
			if (other.password != null)
				return false;
		} else if (!getPassword().equals(other.password))
			return false;
		if (getUsername() == null) {
			if (other.username != null)
				return false;
		} else if (!getUsername().equals(other.username))
			return false;
		return true;
	}
}