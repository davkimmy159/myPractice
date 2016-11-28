package kr.co.ps119.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Member implements UserDetails, Serializable {

	private static final long serialVersionUID = 3443687366576503018L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;
	
	@Column
	@NotBlank(message = "email is empty")
	@Email(message = "email is invalid")
	@Size(min = 5,
		  max = 100,
		  message = "email length must be between 5 ~ 100")
	private String email;
	
	@Column
	@NotBlank(message = "username is empty")
	@Length(min = 8,
			max = 60,
			message = "username must have value between 8 and 60 included")
	private String username;
	
	@Column
	@NotBlank(message = "password is empty")
	@Length(min = 8,
			max = 180,
			message = "password must have value between 8 and 180 included")
	private String password;
	
	@Column
	@NotNull
	private boolean enabled;
	
	@OneToMany(mappedBy = "member",
			   fetch = FetchType.LAZY,
			   cascade = CascadeType.ALL,
			   orphanRemoval = true)
	@JsonBackReference
	private List<MemberAuthority> memberAuthorities = new ArrayList<>();

	@OneToMany(mappedBy = "member",
			   fetch = FetchType.LAZY,
			   cascade = CascadeType.ALL,
			   orphanRemoval = true)
	@JsonBackReference
	private List<Board> boards = new ArrayList<>();
	
	@OneToMany(mappedBy = "member",
			   fetch = FetchType.LAZY,
			   cascade = CascadeType.ALL,
			   orphanRemoval = true)
	@JsonBackReference
	private List<Memo> memos = new ArrayList<>();
		
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
	
	
	// ------------------------------------------------------------------
	
	
	// Sets default values
	@PrePersist
	void setDefaultValues() {
		enabled = true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		memberAuthorities.stream()
						 .forEach((auth) -> authorities.add(new SimpleGrantedAuthority(auth.getAuthority().getAuthority())));
        return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	// ------------------------------------------------------------------
	
	
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

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
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
	
	public List<Memo> getMemos() {
		return memos;
	}

	public void addMemo(Memo memo) {
		memos.add(memo);
		
		if(memo.getMember() != this) {
			memo.setMember(this);
		}
	}
	
	public List<MemberAuthority> getMemberAuthorities() {
		return memberAuthorities;
	}
	
	public void addMemberAuthority(MemberAuthority memberAuthority) {
		memberAuthorities.add(memberAuthority);
		
		if(memberAuthority.getMember() != this) {
			memberAuthority.setMember(this);
		}
	}
	
	@Override
	public String toString() {
		return "Member [ id=" + id + ", email=" + email + ", username=" + username + " ]";
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Member)) {
			return false;
		}
		Member other = (Member) obj;
		if (getEmail() == null) {
			if (other.email != null)
				return false;
		} else if (!getEmail().equals(other.email))
			return false;
		if (isEnabled() != other.enabled) {
			return false;
		}
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