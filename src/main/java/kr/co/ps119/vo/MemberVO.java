package kr.co.ps119.vo;

public class MemberVO {
	
	private final Long id;
	
	private final String email;
	
	private final String username;
	
//	private final String password;
	
	private final boolean enabled;

	public MemberVO(Long id, String email, String username, /*String password,*/ boolean enabled) {
		this.id = id;
		this.email = email;
		this.username = username;
//		this.password = password;
		this.enabled = enabled;
	}

	/*
	public Long getId() {
		return id.longValue();
	}
	*/

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}
	
	/*
	public String getPassword() {
		return password;
	}

	public boolean isEnabled() {
		return enabled;
	}
	*/
}
