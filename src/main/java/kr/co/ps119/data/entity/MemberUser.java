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
	@Column(name = "name", nullable = false)
	private String name;

	@NotNull
	@Range
	@Column(name = "age", nullable = false)
	private Integer age;

	public MemberUser() {
	}

	public MemberUser(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	public MemberUser(Long id, String name, Integer age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}
