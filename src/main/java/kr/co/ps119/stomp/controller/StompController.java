package kr.co.ps119.stomp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ps119.entity.Board;
import kr.co.ps119.entity.BoardHistory;
import kr.co.ps119.entity.Member;
import kr.co.ps119.service.BoardHistoryService;
import kr.co.ps119.service.BoardService;
import kr.co.ps119.service.MemberService;
import kr.co.ps119.stomp.messageVO.StompChatMessage;
import kr.co.ps119.stomp.messageVO.StompDBUpdateMessage;
import kr.co.ps119.stomp.messageVO.StompEditorContent;
import kr.co.ps119.stomp.messageVO.StompMemoUpdateMessage;

@Transactional
@Controller
public class StompController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardHistoryService boardHisService;
	
	@Autowired
	SimpMessagingTemplate messagingTemplate;
	
	@MessageMapping("/board/chat/{boardId}")
	@SendTo("/subscribe/board/chat/{boardId}")
	public StompChatMessage handler1(StompChatMessage chatMessage) {
		
		chatMessage.setChatAreaMessage();
		
		return chatMessage;
	}
	
	@MessageMapping("/board/chat/db_update_alarm/{boardId}")
	@SendTo("/subscribe/board/chat/db_update_alarm/{boardId}")
	public StompDBUpdateMessage handler3(
			StompDBUpdateMessage DBUpdateMessage,
			@DestinationVariable Long boardId) {
		
		DBUpdateMessage.setChatAreaMessage();

		return DBUpdateMessage;
	}
	
	@MessageMapping("/board/editor/{boardId}")
	@SendTo("/subscribe/board/editor/{boardId}")
	public StompEditorContent editorContentUpdate(
			StompEditorContent editorContent,
			@DestinationVariable Long boardId,
			@Header(name = "nativeHeaders") Map<String, Object> headers) {

		editorContent.setChatAreaMessage();

		List<String> list = (List<String>)headers.get("DB_UPDATE");
		boolean dbUpdateFlag = Boolean.valueOf(list.get(0));
		
		if(!dbUpdateFlag) {
			createOneBoardHistory(editorContent.getUsername(), boardId, "Editor content update");
		}
		
		return editorContent;
	}
	
	@MessageMapping("/board/memo_update_alarm/{boardId}")
	@SendTo("/subscribe/board/memo_update_alarm/{boardId}")
	public StompMemoUpdateMessage handler4(StompMemoUpdateMessage memoUpdateMessage) {
		
		memoUpdateMessage.setChatAreaMessage();
		
		System.out.println("page number : " + memoUpdateMessage.getPageNumber());
		
		return memoUpdateMessage;
	}
	
	private void createOneBoardHistory(String username, Long boardId, String historyContent) {
		Member member = memberService.findByUsername(username);
		Board board = boardService.findOne(boardId);
		
		BoardHistory boardHistory = new BoardHistory();
		boardHistory.setMember(member);
		boardHistory.setBoard(board);
		boardHistory.setContent(historyContent);
		boardHisService.saveOne(boardHistory);
	}
}
