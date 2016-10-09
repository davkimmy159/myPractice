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
public class StompTestController {

	private static final Logger logger = LoggerFactory.getLogger(StompTestController.class);
	
	@MessageMapping({"/marco"})
	@SendTo("/queue/echo")
	public Shout handleShout(Shout incoming) {
		System.out.println("Received message : " + incoming.getMessage());
		
		Shout outgoing = new Shout();
		outgoing.setMessage("Polo!");
		return outgoing;
	}
/*	
	@SubscribeMapping({"/queue"})
	public Shout handleSubscription() {
		Shout outgoing = new Shout();
		outgoing.setMessage("Polo!");
		System.out.println("Polo!");
		return outgoing;
	}
*/
	
    @MessageMapping("/echo")
    @SendTo("/subscribe/echo")
    public Shout sendEcho(Shout hello) throws Exception {
        System.out.println("receive message : " + hello.toString());
        Shout echoHello = new Shout();
        echoHello.setMessage(hello.getMessage());
        return echoHello;
    }
}
