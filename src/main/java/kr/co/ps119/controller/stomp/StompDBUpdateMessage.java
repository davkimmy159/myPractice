package kr.co.ps119.controller.stomp;

public class StompDBUpdateMessage extends AbstractStompMessage {
	
	public StompDBUpdateMessage() {
	}
	
	public StompDBUpdateMessage(String username, String messageBody) {
		super(username, messageBody);
	}

	@Override
	public void makeChatAreaMessage() {
		chatAreaMessage = "*** DB content updated by '" + username + "' ***";
	}
}