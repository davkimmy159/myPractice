package kr.co.ps119.stomp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import kr.co.ps119.entity.Member;
import kr.co.ps119.service.MemberService;
import kr.co.ps119.stomp.messageVO.MemberRowInfo;
import kr.co.ps119.stomp.messageVO.StompChatMessage;
import kr.co.ps119.stomp.messageVO.StompDBUpdateMessage;
import kr.co.ps119.stomp.messageVO.StompEditorContent;
import kr.co.ps119.stomp.messageVO.StompMemoUpdateMessage;

@Controller
public class StompController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private Map<Long, List<Member>> memberListMap;
	
	@MessageMapping("/board/chat/{boardId}")
	@SendTo("/subscribe/board/chat/{boardId}")
	public StompChatMessage handler1(StompChatMessage chatMessage) {
		
		chatMessage.makeChatAreaMessage();
		
		return chatMessage;
	}
	
	@MessageMapping("/board/chat/db_update_alarm/{boardId}")
	@SendTo("/subscribe/board/chat/db_update_alarm/{boardId}")
	public StompDBUpdateMessage handler3(StompDBUpdateMessage DBUpdateMessage) {
		
		DBUpdateMessage.makeChatAreaMessage();
		
		return DBUpdateMessage;
	}
	
	@MessageMapping("/board/editor/{boardId}")
	@SendTo("/subscribe/board/editor/{boardId}")
	public StompEditorContent handler2(StompEditorContent editorContent) {

		editorContent.makeChatAreaMessage();
		
		return editorContent;
	}
	
	@MessageMapping("/board/memo_update_alarm/{boardId}")
	@SendTo("/subscribe/board/memo_update_alarm/{boardId}")
	public StompMemoUpdateMessage handler4(StompMemoUpdateMessage memoUpdateMessage) {
		
		memoUpdateMessage.makeChatAreaMessage();
		
		System.out.println("page number : " + memoUpdateMessage.getPageNumber());
		
		return memoUpdateMessage;
	}
	
	@MessageMapping("/board/memberInfoRow/{boardId}")
	@SendTo("/subscribe/board/memberInfoRow/{boardId}")
	public MemberRowInfo handler5(
			MemberRowInfo memberRowInfo,
			@DestinationVariable Long boardId) {
		
		System.out.println("board id : " + boardId);
		System.out.println("member row info : " + memberRowInfo.getMemberInfoRowData());
		System.out.println("member username : " + memberRowInfo.getUsername());
		
		return memberRowInfo;
	}
	
	/*
	@SubscribeMapping({"/queue/subscribe1"})
	public StompBasicMessage handleSubscription() {
		StompBasicMessage outgoing = new StompBasicMessage();
		outgoing.setUsername("username!");
		outgoing.setMessage("message");
		
		return outgoing;
	}
	*/
}
