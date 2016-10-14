package kr.co.ps119.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
			RedirectAttributes model) {
		
		// URL to be returned if there's any problem to create board
		String returnPage = "redirect:/member/member_main";
		
		// Trims board name from client input
		String trimmedBoardName = boardName.trim();
		
		// Gets length of board name from trimmed one
		int boardNameLength = trimmedBoardName.length();
		
		// Checks trimmed one and whether it's blank or only white space string and returns if it is
		if(boardNameLength == 0) {
			model.addFlashAttribute("wrongTitleInput", "It's empty or blank");
			model.addFlashAttribute("errorMessage", "Title of board cannot be empty or blank. Please try again");
			return returnPage;
			
		// Returns board name if it's unqualified
		} else if(boardNameLength > 5) {
			model.addFlashAttribute("wrongTitleInput", boardName);
			model.addFlashAttribute("errorMessage", "Length of board title must be between 1 ~ 5 (for test). Please try again");
			return returnPage;
		}
		
		
		Long boardId = boardService.createBoard(boardName, ownerUsername);
		
		model.addAttribute("boardId", boardId);
		
		return "redirect:/board/{boardId}";
	}

	
	
	@GetMapping(value = "{boardId}")
	public String getBoard(@PathVariable Long boardId) {
		
		System.out.println("@PathVariable : " + boardId);
		
		return "board/board2";
	}
}
