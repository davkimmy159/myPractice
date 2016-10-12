package kr.co.ps119.controller.stomp;

public class StompChatMessage extends AbstractStompMessage{
	
	
	public StompChatMessage() {
	}

	public StompChatMessage(String username, String messageBody) {
		super(username, messageBody);
	}

	@Override
	public void makeChatAreaMessage() {
		chatAreaMessage = username + " : " + messageBody;
	}
}