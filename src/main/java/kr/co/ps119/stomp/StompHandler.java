package kr.co.ps119.stomp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
	private Map<Long, Map<String, Member>> boardStompConnMap;
	
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
			
			Long boardId = Long.valueOf(sha.getNativeHeader("boardId").get(0));
			String simpSessionIdIn = (String)message.getHeaders().get("simpSessionId");
			Member connectMember = memberService.findByUsername(sha.getUser().getName());
			addConnection(boardId, simpSessionIdIn, connectMember);
			
			System.out.println("message       : " + message);
			System.out.println("headers       : " + message.getHeaders());
			System.out.println("simpSessionId : " + message.getHeaders().get("simpSessionId"));
			
			break;
		case DISCONNECT:
			System.out.println("Disconnected");
			
			String simpSessionIdOut = (String)message.getHeaders().get("simpSessionId");
			Member disconnectMember = memberService.findByUsername(sha.getUser().getName());
			removeConnection(simpSessionIdOut, disconnectMember);
			
			System.out.println("message       : " + message);
			System.out.println("headers       : " + message.getHeaders());
			System.out.println("simpSessionId : " + message.getHeaders().get("simpSessionId"));
			
			break;
		case RECEIPT:
			System.out.println("Receipt");
			break;
		default:
			break;
		}
	}
	
	public void addConnection(Long boardId, String simpSessionId, Member member) {
		Map<String, Member> memberMap = null;
		
		if(boardId != null && member != null) {
			memberMap = boardStompConnMap.get(boardId);
			
			if(memberMap == null) {
				memberMap = new HashMap<>();
				memberMap.put(simpSessionId, member);
				boardStompConnMap.put(boardId, memberMap);
				System.out.println("---------- CONNECT ----------");
			} else if(!(memberMap.keySet().contains(simpSessionId))) {
				memberMap.put(simpSessionId, member);
				System.out.println("---------- CONNECT ----------");
			} else {
				System.out.println("Connection has been made already");
			}
		} else if(boardId == null) {
			System.out.println("boardId param is null.");
		} else {
			System.out.println("member param is null.");
		}
		
		System.out.println("memberList : " + memberMap);
	}
	
	public void removeConnection(String simpSessionId, Member member) {
		Map<String, Member> memberMap = null;
		boolean flag = false;
		
		if(member != null) {
			
			for(Long connMapKey : boardStompConnMap.keySet()) {
				memberMap = boardStompConnMap.get(connMapKey);
				if(memberMap.keySet().contains(simpSessionId)) {
					flag = true;
					memberMap.remove(simpSessionId);
					System.out.println("---------- DISCONNECT ----------");
				}
			}
			
			if(!flag) {
				System.out.println("That connection doesn't exist. ");
			}

		} else {
			System.out.println("member param is null.");
		}
		
		System.out.println("memberList : " + memberMap);
	}
}
