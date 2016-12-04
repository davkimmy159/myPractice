package kr.co.ps119.stomp.messageVO;

import java.util.ArrayList;
import java.util.List;

import kr.co.ps119.vo.MemberVO;

public class StompJoinMemberUpdateMessage extends AbstractStompMessage {

	boolean join;
	private List<MemberVO> memberList;
	
	public StompJoinMemberUpdateMessage() {
		super("", "");
	}
	
	public StompJoinMemberUpdateMessage(String username, String messageBody) {
		super(username, messageBody);
		join = false;
	}
	
	@Override
	public void makeChatAreaMessage() {
	}

	public boolean isJoin() {
		return join;
	}

	public void setJoin(boolean join) {
		this.join = join;
	}

	public List<MemberVO> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<MemberVO> memberList) {
		this.memberList = memberList;
	}
	
	public void addMember(MemberVO member) {
		memberList.add(member);
	}
}
