package kr.co.ps119.stomp.messageVO;

public class StompChatMessage extends AbstractStompMessage{
	
	public StompChatMessage() {
		super();
	}

	public StompChatMessage(String username, String messageBody) {
		super(username, messageBody);
	}

	@Override
	public void makeChatAreaMessage() {
		chatAreaMessage = username + " : " + messageBody;
	}
}