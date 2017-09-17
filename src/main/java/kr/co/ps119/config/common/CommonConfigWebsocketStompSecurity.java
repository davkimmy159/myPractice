package kr.co.ps119.config.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Order(1)
@Configuration
public class CommonConfigWebsocketStompSecurity {
	
	/*
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages.simpDestMatchers("/socket/*").permitAll();
    }
    */
}
