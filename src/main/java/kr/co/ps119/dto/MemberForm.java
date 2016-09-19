package kr.co.ps119.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;


public class MemberForm {
	
	@NotBlank(message = "email is empty")
	@Email(message = "email is invalid")
	private String email;
	
	@NotBlank(message = "username is empty")
	@Length(min = 8,
			max = 40,
			message = "must have value between 8 and 40 included")
	private String username;
	
	@NotBlank(message = "password is empty")
	@Length(min = 8,
			max = 40,
			message = "must have value between 8 and 40 included")
	private String password;
	
	public MemberForm() {
	}
	
	public MemberForm(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if(this.getClass() != obj.getClass())
			return false;
		MemberForm other = (MemberForm) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
	
		return true;
	}
}