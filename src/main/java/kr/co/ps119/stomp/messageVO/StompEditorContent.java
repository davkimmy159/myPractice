package kr.co.ps119.stomp.messageVO;

public class StompEditorContent extends AbstractStompMessage {
	
	public StompEditorContent() {
		super();
	}

	public StompEditorContent(String username, String messageBody) {
		super(username, messageBody);
	}

	@Override
	public void makeChatAreaMessage() {
		chatAreaMessage = "** Editor updated by '" + username + "' **";
	}
}