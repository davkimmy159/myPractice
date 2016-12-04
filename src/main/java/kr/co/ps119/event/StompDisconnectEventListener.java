package kr.co.ps119.event;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import kr.co.ps119.entity.Member;
import kr.co.ps119.service.MemberService;
import kr.co.ps119.stomp.messageVO.StompJoinMemberUpdateMessage;
import kr.co.ps119.vo.MemberVO;

@Service
@Transactional
public class StompDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {

	@Autowired
	private Map<Long, Map<String, MemberVO>> boardStompConnMap;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {

		Message<byte[]> message = event.getMessage();
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);

		Long boardId = null;
		String simpSessionIdOut = (String)message.getHeaders().get("simpSessionId");
		Member disconnectMember = memberService.findByUsername(sha.getUser().getName());
		boardId = removeConnection(simpSessionIdOut, disconnectMember);
//		System.out.println("board id : " + boardId);
		
		System.out.println("message       : " + message);
		System.out.println("headers       : " + message.getHeaders());
		System.out.println("simpSessionId : " + message.getHeaders().get("simpSessionId"));
		
		StompJoinMemberUpdateMessage memberUpMsg = new StompJoinMemberUpdateMessage();
		memberUpMsg.setJoin(false);
		memberUpMsg.setUsername(sha.getUser().getName());
		memberUpMsg.setMessageBody("Member out");
		memberUpMsg.setMemberList(new ArrayList<MemberVO>(boardStompConnMap.get(boardId).values()));

		if(boardId != null) {
			template.convertAndSend("/subscribe/board/join_member_update/" + boardId, memberUpMsg);	
		}
	}

	private Long removeConnection(String simpSessionId, Member member) {
		Map<String, MemberVO> memberMap = null;
		boolean flag = false;
		Long boardId = null;
		
		if(member != null) {
			
			for(Long connMapKey : boardStompConnMap.keySet()) {
				memberMap = boardStompConnMap.get(connMapKey);
				if(memberMap.keySet().contains(simpSessionId)) {
					flag = true;
					boardId = connMapKey;
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
		
		return boardId;
	}
}
