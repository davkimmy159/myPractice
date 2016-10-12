package kr.co.ps119.controller.stomp;

public abstract class AbstractStompMessage implements StompMessageInterface {

	protected String username;
	protected String messageBody;
	protected String chatAreaMessage;
	
	public AbstractStompMessage() {
	}
	
	public AbstractStompMessage(String username, String messageBody) {
		this.username = username;
		this.messageBody = messageBody;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getMessageBody() {
		return messageBody;
	}
	
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getChatAreaMessage() {
		return chatAreaMessage;
	}

	public void setChatAreaMessage(String chatAreaMessage) {
		this.chatAreaMessage = chatAreaMessage;
	}
}