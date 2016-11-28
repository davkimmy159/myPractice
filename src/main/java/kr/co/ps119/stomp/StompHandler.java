package kr.co.ps119.stomp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

import kr.co.ps119.entity.Member;
import kr.co.ps119.service.MemberService;

public class StompHandler extends ChannelInterceptorAdapter {
	
	@Autowired
	private Map<Long, List<Member>> memberListMap;
	
	@Autowired
	private MemberService memberService;
	
	private StompCommand stompCommand;
	
	@Override
	public boolean preReceive(MessageChannel channel) {
		return super.preReceive(channel);
	}
	
	@Override
	public Message<?> postReceive(Message<?> message, MessageChannel channel) {
		return super.postReceive(message, channel);
	}
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		return super.preSend(message, channel);
	}

	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

		StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
		
		stompCommand = sha.getCommand();
		
		if (stompCommand == null) {
			return;
		}
		
		switch (stompCommand) {
		case CONNECT:
			System.out.println("Connected");
			
			Member connectMember = memberService.findByUsername(sha.getUser().getName());
//			addConnection(Long.valueOf(sha.getNativeHeader("boardId").get(0)), connectMember);	
			System.out.println("header : " + sha.getHeader(""));
			
			break;
		case DISCONNECT:
			System.out.println("Disconnected");
			
			Member disconnectMember = memberService.findByUsername(sha.getUser().getName());
//			removeConnection(Long.valueOf(sha.getNativeHeader("boardId").get(0)), disconnectMember);	
//			System.out.println("boardId : " + Long.valueOf(sha.getNativeHeader("boardId").get(0)));
			System.out.println("receipt : " + sha.getReceipt());
			
			break;
		case RECEIPT:
			System.out.println("Receipt");
			break;
		default:
			break;
		}
	}
	
	public void addConnection(Long boardId, Member member) {
		List<Member> memberList = null;
		
		if(boardId != null && member != null) {
			memberList = memberListMap.get(boardId);
			
			if(memberList == null) {
				memberList = new ArrayList<>();
				memberList.add(member);
				memberListMap.put(boardId, memberList);
			} else if(!(memberList.contains(member))) {
				memberList.add(member);
			} else {
				System.out.println("Connection has been made already");
			}
		} else if(boardId == null) {
			System.out.println("That board doesn't exist.");
		} else {
			System.out.println("That member doesn't exist.");
		}
		
		System.out.println("memberList : " + memberList);
	}
	
	public void removeConnection(Long boardId, Member member) {
		List<Member> memberList = null;
		if(boardId != null && member != null) {
			memberList = memberListMap.get(boardId);
			
			if(memberList == null) {
				System.out.println("Connection is never made before.");
			} else if(memberList.contains(member)) {
				memberList.remove(member);
			} else {
				System.out.println("Connection is empty already.");
			}
		} else if(boardId == null) {
			System.out.println("That board doesn't exist.");
		} else {
			System.out.println("That member doesn't exist.");
		}
		
		System.out.println("memberList : " + memberList);
	}
}
