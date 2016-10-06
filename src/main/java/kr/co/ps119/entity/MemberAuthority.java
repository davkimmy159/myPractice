package kr.co.ps119.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class MemberAuthority implements Serializable {

	private static final long serialVersionUID = -8367924548359963693L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_authority_id")
	private Long id;
	
	// foreign key 1
	@ManyToOne(fetch = FetchType.LAZY,
			   cascade = CascadeType.ALL,
			   optional = false)
	@JoinColumn(name = "member_id",
				nullable = true)
	@JsonManagedReference
	private Member member;
	
	// foreign key 2
	@ManyToOne(fetch = FetchType.LAZY,
			   cascade = CascadeType.ALL,
			   optional = false)
	@JoinColumn(name = "authority_id",
				nullable = true)
	@JsonManagedReference
	private Authority authority;

	public MemberAuthority() {
	}

	public Long getId() {
		return id;
	}

	/*
	public void setId(Long id) {
		this.id = id;
	}
	*/

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
		
		if(!(member.getAuthorities().contains(this))) {
			member.getAuthorities().add(this);
		}
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
		
		if(!(authority.getAuthorities().contains(this))) {
			authority.getAuthorities().add(this);
		}
	}
	
	@Override
	public String toString() {
		return "MemberAuthority [id=" + id + ", This is relational table between member and authority]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authority == null) ? 0 : authority.hashCode());
		result = prime * result + ((member == null) ? 0 : member.hashCode());
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
		if (!(obj instanceof MemberAuthority)) {
			return false;
		}
		MemberAuthority other = (MemberAuthority) obj;
		if (getAuthority() == null) {
			if (other.authority != null) {
				return false;
			}
		} else if (!getAuthority().equals(other.authority)) {
			return false;
		}
		if (getMember() == null) {
			if (other.member != null) {
				return false;
			}
		} else if (!getMember().equals(other.member)) {
			return false;
		}
		return true;
	}
}
