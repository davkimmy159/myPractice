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

import kr.co.ps119.entity.Board;
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
			model.addFlashAttribute("blankTitleInput", "It's empty or blank");
			model.addFlashAttribute("errorMessage", "Title of board cannot be empty or blank. Please try again");
			return returnPage;
		
		// Returns board name if it's unqualified
		} else if(boardNameLength > 5) {
			model.addFlashAttribute("wrongTitleInput", trimmedBoardName);
			model.addFlashAttribute("errorMessage", "Length of board title must be between 1 ~ 5 (for test). Please try again");
			return returnPage;
		}
		
		Long boardId = boardService.createBoard(boardName, ownerUsername);
		
		if(boardId < 0) {
			model.addFlashAttribute("serverError", "Server error!");
			model.addFlashAttribute("errorMessage", "Server error has been occurred. Please try again");
			return returnPage;
		}
		
		model.addAttribute("boardId", boardId);
		
		returnPage = "redirect:/board/{boardId}";
		
		return returnPage;
	}

	@PostMapping(value = "{boardId}")
	public String getBoard(
			@PathVariable Long boardId,
			RedirectAttributes model) {
		
		String returnPage = "redirect:/index";
		
		Board board = boardService.findOne(boardId);

		// If board doesn't exist
		if(board.isEmptyBoard()) {
			model.addAttribute("boardExists", false);
			
		// If board exists
		} else {
			returnPage = "board/board";
			model.addAttribute("boardContent", board.getContent());
			System.out.println(board.getContent());
		}
		
		return returnPage;
	}
	
	@GetMapping(value = "deleteAll")
	private String deleteAll() {
		boardService.deleteAllBoardsOfMember();
		
		return "redirect:/member/member_main";
	}
}
