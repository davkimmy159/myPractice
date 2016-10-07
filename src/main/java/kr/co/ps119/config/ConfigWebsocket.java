package kr.co.ps119.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import kr.co.ps119.websocket.ChatHandler;
import kr.co.ps119.websocket.EditorContentHandler;

@Configuration
//@EnableWebSocket
@EnableWebSocketMessageBroker
public class ConfigWebsocket extends AbstractWebSocketMessageBrokerConfigurer /* WebSocketConfigurer */ {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/websocket/chat").withSockJS();
		registry.addEndpoint("/websocket/editor").withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic", "queue");
//				.enableStompBrokerRelay("/topic")
		registry.setApplicationDestinationPrefixes("/app");
	}

	/*
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(chatHandler(), "/socket/chat").withSockJS();
		registry.addHandler(editorContentHandler(), "/socket/editor").withSockJS();
	}

	@Bean
	public ChatHandler chatHandler() {
		return new ChatHandler();
	}
	
	@Bean
	public EditorContentHandler editorContentHandler() {
		return new EditorContentHandler();
	}
	*/
}