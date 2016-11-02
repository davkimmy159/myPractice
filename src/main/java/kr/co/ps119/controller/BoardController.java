package kr.co.ps119.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

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
			@RequestParam String currentRelativePath,
			RedirectAttributes model) {
		
		// URL to be returned if there's any problem to create board
		String returnPage = "redirect:" + currentRelativePath;
		
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
		} else if(boardNameLength > 200) {
			model.addFlashAttribute("wrongTitleInput", trimmedBoardName);
			model.addFlashAttribute("errorMessage", "Length of board title must be between 1 ~ 600 (english), 1 ~ 200 (korean). Please try again");
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
			HttpServletRequest request,
			Model model,
			Principal principal) {
		
		String returnPage = "redirect:/index";
		
		Board board = boardService.findOneWithAddHitCount(boardId, principal);

		// If board exists
		if(!(board.isEmptyBoard())) {
			returnPage = "board/board";
			model.addAttribute("boardContent", board.getContent());

		// If board doesn't exist
		} else {
			FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
			flashMap.put("boardNonExistent", false);
			
		}
		
		return returnPage;
	}
}
