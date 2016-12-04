package kr.co.ps119.stomp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

import kr.co.ps119.entity.Member;
import kr.co.ps119.service.MemberService;
import kr.co.ps119.stomp.controller.StompController;
import kr.co.ps119.stomp.messageVO.StompChatMessage;
import kr.co.ps119.vo.MemberVO;

public class StompHandler extends ChannelInterceptorAdapter {
	
	@Autowired
	private Map<Long, Map<String, MemberVO>> boardStompConnMap;
	
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
		
		/*
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
		
		stompCommand = sha.getCommand();
		
		if (stompCommand == null) {
			return message;
		}
		
		switch (stompCommand) {
		case RECEIPT:
			System.out.println("Receipt");
		
			break;
		
		default:
			break;
		}
		
		return message;
		*/
		
		return super.preSend(message, channel);
	}
	
	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		super.postSend(message, channel, sent);
	}
}
