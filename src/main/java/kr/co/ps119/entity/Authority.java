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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Authority implements Serializable {

	private static final long serialVersionUID = -790349361869707005L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "authority_id")
	private Integer id;

	@Column
	private String authority;

	@OneToMany(mappedBy = "authority",
			   fetch = FetchType.LAZY,
			   cascade = CascadeType.ALL,
			   orphanRemoval = true)
	@JsonBackReference
	private List<MemberAuthority> memberAuthorities = new ArrayList<>();
	
	public Authority() {
	}

	public Authority(String authority) {
		this.authority = authority;
	}
	
	public Integer getId() {
		return id;
	}

	/*
	public void setId(Long id) {
		this.id = id;
	}
	*/

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public List<MemberAuthority> getMemberAuthorities() {
		return memberAuthorities;
	}
	
	public void addMemberAuthority(MemberAuthority memberAuthority) {
		memberAuthorities.add(memberAuthority);
		
		if(memberAuthority.getAuthority() != this) {
			memberAuthority.setAuthority(this);
		}
	}

	@Override
	public String toString() {
		return "Authority [id=" + id + ", authority=" + authority + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authority == null) ? 0 : authority.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Authority)) {
			return false;
		}
		Authority other = (Authority) obj;
		if (getAuthority() == null) {
			if (other.authority != null) {
				return false;
			}
		} else if (!getAuthority().equals(other.authority)) {
			return false;
		}
		return true;
	}
}
