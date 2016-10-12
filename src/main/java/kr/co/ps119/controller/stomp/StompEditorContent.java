package kr.co.ps119.controller.stomp;

public class StompEditorContent extends AbstractStompMessage {
	
	public StompEditorContent() {
	}

	public StompEditorContent(String username, String messageBody) {
		super(username, messageBody);
	}

	@Override
	public void makeChatAreaMessage() {
		chatAreaMessage = "** Editor updated by '" + username + "' **";
	}
}