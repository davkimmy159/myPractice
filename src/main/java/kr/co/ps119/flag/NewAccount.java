package kr.co.ps119.flag;

public enum NewAccount {
	DUPLICATE_EMAIL("This email is in use already. Please try other email"),
	DUPLICATE_USERNAME("This username is in use already. Please try other username"),
	UNEXPECTED_SERVER_ERROR("Unexpected server error has been occurred! Please try again"),
	SUCCESSFUL("successful!");
	
	private String message;
	
	private NewAccount(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
