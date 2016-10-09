package kr.co.ps119.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import kr.co.ps119.websocket.ChatHandler;
import kr.co.ps119.websocket.EditorContentHandler;

@Configuration
@EnableWebSocket
public class ConfigWebsocket implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(chatHandler(), "/socket/chat");//.withSockJS();
		registry.addHandler(editorContentHandler(), "/socket/editor");//.withSockJS();
	}

	@Bean
	public ChatHandler chatHandler() {
		return new ChatHandler();
	}
	
	@Bean
	public EditorContentHandler editorContentHandler() {
		return new EditorContentHandler();
	}
}
	