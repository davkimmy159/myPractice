package kr.co.ps119.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import kr.co.ps119.stomp.StompHandler;

@Configuration
@EnableWebSocketMessageBroker
public class ConfigWebsocketStomp extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/websocket/board").withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/subscribe");
//				.enableStompBrokerRelay("/topic")
		registry.setApplicationDestinationPrefixes("/dest");
	}
	
	@Bean
	public StompHandler stompHandler() {
		return new StompHandler();
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.setInterceptors(stompHandler());
	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		registration.taskExecutor().corePoolSize(8);
		registration.setInterceptors(stompHandler());
	}
}