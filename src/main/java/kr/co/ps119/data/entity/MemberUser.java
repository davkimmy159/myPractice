package kr.co.ps119.data.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "member_user")
public class MemberUser implements Serializable {

	private static final long serialVersionUID = -104618268484385033L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @NotNull
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@NotBlank
	@Column(name = "email", nullable = false)
	private String email;

	@NotBlank
	@Column(name = "username", nullable = false)
	private String username;

	public MemberUser() {
	}

	public MemberUser(String email, String username) {
		this.email = email;
		this.username = username;
	}

	public MemberUser(Long id, String email, String username) {
		this.id = id;
		this.email = email;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}
