package kr.co.ps119.controller;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.ps119.entity.Board;
import kr.co.ps119.service.BoardService;
import kr.co.ps119.service.MemberService;

@Controller
@RequestMapping(
		value = "member",
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping(value = "member_main")
	public String userMain(
			Principal principal,
			Model model) {
		
		String username = principal.getName();
		
		// 1
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		System.out.println(authentication.getName());
		
		// 2
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		System.out.println(user.getUsername());
		
//		List<Board> boardList = memberService.findAllBoardsOfMember(principal.getName());
		
//		model.addFlashAttribute("boardList", boardList);
		
		// Should be replaced to immutable VO later
		List<Board> userBoardList = boardService.findAllBoardsOfMemberByUsername(username);
		List<Board> allBoardList = boardService.findAllBoards();

		model.addAttribute("userBoardList", userBoardList);
		model.addAttribute("allBoardList", allBoardList);
		
		return "member/member_main";
	}
}
