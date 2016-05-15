package kr.co.ps119.data.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class MemberUserForm {

	@NotBlank(message = "insert your name")
	private String name;

	@NotNull(message = "must insert your name")
	@Range(min = 1, max = 100, message = "insert your correct age")
	private Integer age;

	public MemberUserForm() {
	}

	public MemberUserForm(String name, Integer age) {
		this.name = name;
		this.age = age;
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
