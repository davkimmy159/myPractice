package kr.co.ps119.config.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Order(1)
@Configuration
public class CommonConfigActiveMQ {
	/*
	public static final String HELLO_QUEUE = "hello.queue";
	 
    @Bean
    public Queue helloJMSQueue() {
        return new ActiveMQQueue(HELLO_QUEUE);
    }
    */
}