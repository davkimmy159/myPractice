package kr.co.ps119.controller.stomp;

public class StompEditorContent {
	
	private String username;
	private String content;
	
	public StompEditorContent() {
		this("", "");
	}

	public StompEditorContent(String content, String username) {
		this.username = username;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}