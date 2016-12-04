package kr.co.ps119.event;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import kr.co.ps119.service.MemberService;
import kr.co.ps119.vo.MemberVO;

@Service
@Transactional

public class Test implements ApplicationListener<SessionSubscribeEvent> {

	@Autowired
	private Map<Long, Map<String, MemberVO>> boardStompConnMap;
	
	@Autowired
	private MemberService memberService;

	@Autowired
	private SimpMessagingTemplate template;
	
	@Override
	public void onApplicationEvent(SessionSubscribeEvent event) {
		
		/*
		System.out.println("------------------------- test -------------------------");
		System.out.println(event);
		System.out.println("========================= test =========================");
		
		Message<byte[]> message = event.getMessage();
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

		String sessionId = accessor.getSessionId();
		String stompSubscriptionId = accessor.getSubscriptionId();
		String destination = accessor.getDestination();
		
		String username = accessor.getUser().getName();
		String payload = "stomp event test";
		
		System.out.println("Session id            : " + sessionId);
		System.out.println("Stomp subscription id : " + stompSubscriptionId);
		System.out.println("Destination           : " + destination);

		template.convertAndSend("/subscribe/test/249", payload);
		*/
	}
}