package kr.co.ps119.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ps119.entity.Board;
import kr.co.ps119.service.BoardService;

@RestController
@RequestMapping(
		value = "/ajax/board",
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class BoardAjaxController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping(value = "updateBoardDB")
	public Map<String, Object> updateBoardDB(
			@RequestParam("boardId") Long boardId,
			@RequestParam("editorContent") String editorContent) {

		System.out.println("Ajax communication");
		
		Long boardIdAfterUpdate = boardService.updateBoardDBContent(boardId, editorContent);
		String resultMessage;
		
		if(boardIdAfterUpdate > 0) {
			resultMessage = "Save successful.";
			
		} else {
			resultMessage = "Server error, Please try again.";
		}

		Map<String, Object> jsonObject = new HashMap<>();
		
		jsonObject.put("resultMessage", resultMessage);
		
		return jsonObject;
	}
	
	@GetMapping(value = "getBoardListOfMemberFromDBByUsername")
	public Map<String, Object> getBoardListOfMemberFromDBByUsername(String username) {
		List<Board> boardList = boardService.findAllBoardsOfMemberByUsername(username);
		
		Map<String, Object> jsonObject = new HashMap<>();
		
		jsonObject.put("total", boardList.size());
		jsonObject.put("boardList", boardList);
		
		return jsonObject;
	}
	
	@GetMapping(value = "deleteOneBoardFromBtn") 
	public Map<String, Object> deleteOneBoardFromBtn(Long boardId) {
		boardService.deleteOneBoardById(boardId);
		
		Map<String, Object> jsonObject = new HashMap<>();
		
		jsonObject.put("message", "Board is deleted");
		
		return jsonObject;
	}
	
	@GetMapping(value = "deleteSelectedBoard") 
	public Map<String, Object> deleteSelectedBoard(
			@RequestParam("boardIds[]") String boardIds[]) {
		System.out.println(boardIds);
		System.out.println(boardIds.length);
		

		for(String boardId : boardIds) {
//			boardService.deleteOneBoardById(Long.valueOf(boardId));
			System.out.println(boardId);
		}
		
		Map<String, Object> jsonObject = new HashMap<>();
		
		jsonObject.put("message", "Selected board are deleted");
		
		return jsonObject;
	}
}
