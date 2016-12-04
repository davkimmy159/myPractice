package kr.co.ps119.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import kr.co.ps119.entity.Member;
import kr.co.ps119.service.MemberService;
import kr.co.ps119.stomp.messageVO.StompJoinMemberUpdateMessage;
import kr.co.ps119.vo.MemberVO;

@Service
@Transactional
public class StompConnectedEventListener implements ApplicationListener<SessionConnectedEvent> {

	@Autowired
	private Map<Long, Map<String, MemberVO>> boardStompConnMap;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@Override
	public void onApplicationEvent(SessionConnectedEvent event) {
		
		Message<byte[]> message = event.getMessage();
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
		
		MessageHeaders messageHeaders = ((GenericMessage<?>)sha.getHeader("simpConnectMessage")).getHeaders();
		Map<?, ?> nativeHeaders = (Map<?, ?>)messageHeaders.get("nativeHeaders");
		List<?> memberList = (List<?>)nativeHeaders.get("boardId");
		Long boardId = Long.valueOf((String)memberList.get(0));

		String simpSessionIdIn = (String)message.getHeaders().get("simpSessionId");
		Member connectMember = memberService.findByUsername(sha.getUser().getName());
		addConnection(boardId, simpSessionIdIn, connectMember);
		
		System.out.println("message       : " + message);
		System.out.println("headers       : " + message.getHeaders());
		System.out.println("simpSessionId : " + message.getHeaders().get("simpSessionId"));
		
		StompJoinMemberUpdateMessage memberUpMsg = new StompJoinMemberUpdateMessage();
		memberUpMsg.setJoin(true);
		memberUpMsg.setUsername(sha.getUser().getName());
		memberUpMsg.setMessageBody("Member join");
		memberUpMsg.setMemberList(new ArrayList<MemberVO>(boardStompConnMap.get(boardId).values()));

		template.convertAndSend("/subscribe/board/join_member_update/" + boardId, memberUpMsg);
	}
	
	private void addConnection(Long boardId, String simpSessionId, Member member) {
		Map<String, MemberVO> memberMap = null;
		MemberVO memberVO;
		
		if(boardId != null && member != null) {
			memberMap = boardStompConnMap.get(boardId);
			
			memberVO = changeToVO(member);
			
			if(memberMap == null) {
				memberMap = new HashMap<>();
				memberMap.put(simpSessionId, memberVO);
				boardStompConnMap.put(boardId, memberMap);
				System.out.println("---------- CONNECTED ----------");
			} else if(!(memberMap.keySet().contains(simpSessionId))) {
				memberMap.put(simpSessionId, memberVO);
				System.out.println("---------- CONNECTED ----------");
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
	
	private MemberVO changeToVO(Member member) {
		return new MemberVO(member.getId(), member.getEmail(), member.getUsername(), member.isEnabled());
	}
}
