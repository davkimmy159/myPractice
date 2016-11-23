package kr.co.ps119.stomp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import kr.co.ps119.stomp.messageVO.StompChatMessage;
import kr.co.ps119.stomp.messageVO.StompDBUpdateMessage;
import kr.co.ps119.stomp.messageVO.StompEditorContent;
import kr.co.ps119.stomp.messageVO.StompMemoUpdateMessage;

@Controller
public class StompController {

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
