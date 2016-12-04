package kr.co.ps119.stomp.messageVO;

public class StompMemoUpdateMessage extends AbstractStompMessage {
	
	private int pageNumber;
	
	public StompMemoUpdateMessage() {
		super();
	}

	public StompMemoUpdateMessage(String username, String messageBody) {
		super(username, messageBody);
	}

	@Override
	public void setChatAreaMessage() {
		chatAreaMessage = "** Memo updated by '" + username + "' **";
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
}