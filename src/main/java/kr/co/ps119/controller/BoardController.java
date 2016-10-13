package kr.co.ps119.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.ps119.service.BoardService;

@Controller
@RequestMapping(
		value = "board",
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@PostMapping(value = "create_board")
	public String createBoard(
			@RequestParam String boardName,
			@RequestParam String ownerUsername,
			Model model) {
		
		Long boardId = boardService.createBoard(boardName, ownerUsername);
		
		model.addAttribute("boardId", boardId);
		
		return "redirect:/board/{boardId}";
	}

	@GetMapping(value = "{boardId}")
	public String getBoard(@PathVariable Long boardId) {
		
		System.out.println("@PathVariable : " + boardId);
		
		return "redirect:/test";
	}
}
