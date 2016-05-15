package kr.co.ps119.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
// 서버단의 WebSocketEndPoint 정의
// Websocket에서 서버단의 프로세스를 정의할 수 있다
// xml에 설정안하고 @ServerEndpoint(path) 로 정의할 수 있다고 알고 있다.
public class ChatHandler extends TextWebSocketHandler {

	private List<WebSocketSession> sessionList = new ArrayList<>();
	private Map<String, WebSocketSession> sessionMap = new HashMap<>();
	
	public ChatHandler() {
		super();
	}

	/**
	 * 클라이언트 연결 이후에 실행되는 메소드
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// List 쓸 때
		sessionList.add(session);
		System.out.println("Session connected");
	}

	/**
	 * 클라이언트가 웹소켓서버로 메시지를 전송했을 때 실행되는 메소드
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		String id = session.getId();
		String payloadMessage = message.getPayload();

		for (WebSocketSession eachSession: sessionList) {
			if (!eachSession.equals(session))
				eachSession.sendMessage(new TextMessage(payloadMessage));
		}
	}

	/**
	 * 클라이언트가 연결을 끊었을 때 실행되는 메소드
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// list
		sessionList.remove(session);
		System.out.println("Closed");
		// map
		// sessions.remove(session.getId());
		// logger.info("{} 연결 끊김", session.getId());
	}

}