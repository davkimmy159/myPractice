package kr.co.ps119.controller.stomp;

import java.security.Principal;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class StompChatController {

	private static final Logger logger = LoggerFactory.getLogger(StompChatController.class);
	
	@MessageMapping("/chat")
	@SendTo("/subscribe/chat")
	public StompChatMessage handler1(StompChatMessage msgFromChatClient) {
		logger.info("***********************   chat - subscribe/chat   ***********************");
		logger.info("Received username : " + msgFromChatClient.getUsername());
		logger.info("Received message : " + msgFromChatClient.getMessage());
		
		return msgFromChatClient ;
	}
	
	@MessageMapping("/editor")
	@SendTo("/subscribe/editor")
	public StompEditorContent handler2(StompEditorContent contentFromEditorClient) {
		logger.info("***********************   editor - subscribe/editor   ***********************");
		logger.info("Received message : " + contentFromEditorClient.getContent());
		
		return contentFromEditorClient;
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
