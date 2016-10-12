package kr.co.ps119.controller.stomp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class StompMessageController {

	private static final Logger logger = LoggerFactory.getLogger(StompMessageController.class);
	
	@MessageMapping("/chat")
	@SendTo("/subscribe/chat")
	public StompChatMessage handler1(StompChatMessage chatMessage) {
		
		chatMessage.makeChatAreaMessage();
		
		return chatMessage;
	}
	
	@MessageMapping("/editor")
	@SendTo("/subscribe/editor")
	public StompEditorContent handler2(StompEditorContent editorContent) {

		editorContent.makeChatAreaMessage();
		
		return editorContent;
	}
	
	@MessageMapping("/chat/db_update_alarm")
	@SendTo("/subscribe/chat/db_update_alarm")
	public StompDBUpdateMessage handler3(StompDBUpdateMessage DBUpdateMessage) {
		
		DBUpdateMessage.makeChatAreaMessage();
		
		return DBUpdateMessage;
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
