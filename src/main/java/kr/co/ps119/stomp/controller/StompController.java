package kr.co.ps119.stomp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import kr.co.ps119.stomp.messageVO.StompChatMessage;
import kr.co.ps119.stomp.messageVO.StompDBUpdateMessage;
import kr.co.ps119.stomp.messageVO.StompEditorContent;

@Controller
public class StompController {

	@MessageMapping("/chat/{boardId}")
	@SendTo("/subscribe/chat/{boardId}")
	public StompChatMessage handler1(StompChatMessage chatMessage) {
		
		chatMessage.makeChatAreaMessage();
		
		return chatMessage;
	}
	
	
	@MessageMapping("/chat/db_update_alarm/{boardId}")
	@SendTo("/subscribe/chat/db_update_alarm/{boardId}")
	public StompDBUpdateMessage handler3(StompDBUpdateMessage DBUpdateMessage) {
		
		DBUpdateMessage.makeChatAreaMessage();
		
		return DBUpdateMessage;
	}
	
	@MessageMapping("/editor/{boardId}")
	@SendTo("/subscribe/editor/{boardId}")
	public StompEditorContent handler2(StompEditorContent editorContent) {

		editorContent.makeChatAreaMessage();
		
		return editorContent;
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
