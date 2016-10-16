package kr.co.ps119.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ps119.service.BoardService;

@RestController
@RequestMapping(
		value = "ajax/board",
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
		
		Long boardIdAfterUpdate = boardService.updateBoard(boardId, editorContent);
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
}
