package kr.co.ps119.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ps119.entity.Board;
import kr.co.ps119.entity.Member;
import kr.co.ps119.repository.BoardRepository;
import kr.co.ps119.service.BoardService;
import kr.co.ps119.service.MemberService;
import kr.co.ps119.vo.BoardVO;

@RestController
@RequestMapping(
		value = "/ajax/board",
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class BoardAjaxController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardRepository boardRepo;
	
	
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
	
	@GetMapping(value = "test")
	public Map<String, Object> test(
			Principal principal,
			int limit,
			int offset,
			String search,
			String sort,
			String order) {
		
		System.out.println("limit : " + limit);
		System.out.println("offset : " + offset);
		System.out.println("search : " + search);
		System.out.println("sort : " + sort);
		System.out.println("order : " + order);
		
		Member member = memberService.findByUsername(principal.getName());
		
		PageRequest pageRequest = new PageRequest(0, limit, new Sort(Direction.ASC, "id"));
		
//		List<Board> dbList = boardService.findAllBoardsOfMemberByUsername(principal.getName());
		List<Board> dbList = boardRepo. queryFirst10ByMemberId(member.getId(), pageRequest);
		List<BoardVO> viewList;
		
		Map<String, Object> jsonObject = new HashMap<>();
			
		viewList = dbList.stream()
						 .map(board -> new BoardVO(board.getId(), board.getTitle(),board.getContent(), board.getCreateDate(), board.getLastUpdateDate(), board.getUpdateCount(), board.getHitCount(), board.getMember().getUsername()))
						 .collect(Collectors.toList());
		
		jsonObject.put("total", viewList.size());
		jsonObject.put("rows", viewList);
		
		return jsonObject;
	}
	
	/*
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
	*/
}
