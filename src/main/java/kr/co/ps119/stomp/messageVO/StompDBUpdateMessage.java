package kr.co.ps119.stomp.messageVO;

public class StompDBUpdateMessage extends AbstractStompMessage {
	
	public StompDBUpdateMessage() {
		super();
	}
	
	public StompDBUpdateMessage(String username, String messageBody) {
		super(username, messageBody);
	}

	@Override
	public void makeChatAreaMessage() {
		chatAreaMessage = "*** DB content updated by '" + username + "' ***";
	}
}