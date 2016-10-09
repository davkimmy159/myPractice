package kr.co.ps119.controller.stomp;

public class StompChatMessage {
	
	private String username;
	private String message;
	
	public StompChatMessage() {
		this("", "");
	}

	public StompChatMessage(String username, String message) {
		this.username = username;
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
