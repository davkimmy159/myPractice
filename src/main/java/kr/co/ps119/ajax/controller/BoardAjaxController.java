package kr.co.ps119.ajax.controller;

import java.security.Principal;
import java.util.ArrayList;
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
import kr.co.ps119.service.BoardService;
import kr.co.ps119.service.MemberService;
import kr.co.ps119.vo.BoardVO;
import kr.co.ps119.vo.MemberVO;

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
	
	/*
	@Autowired
	private Map<Long, List<Member>> memberListMap;
	
	@GetMapping(value = "updateJoinMemberTable")
	public Map<String, Object> updateJoinMemberTable(
			Principal principal,
			Long boardId) {
		
		Member member = memberService.findByUsername(principal.getName());
//		MemberVO memberVO = new MemberVO(member.getId(), member.getEmail(), member.getUsername(), member.isEnabled());
		
		List<Member> memberList = memberListMap.get(boardId);
		
		if(memberList == null) {
			memberList = new ArrayList<>();
			memberList.add(member);
			memberListMap.put(boardId, memberList);
		} else if(!(memberList.contains(member))){
			memberList.add(member);	
		}

		System.out.println("memberList : " + memberList);
		
		Map<String, Object> jsonObject = new HashMap<>();
		
		jsonObject.put("memberList", memberList);
		
		return jsonObject;
	}
	*/
	
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
	
	@GetMapping(value = "getAllBoardsOfMember")
	public Map<String, Object> getAllBoardsOfMember(
			Principal principal,
			int limit,
			int offset,
//			String search,
			String sort,
			String order) {
		
		Member member = memberService.findByUsername(principal.getName());
		
		return getAllBoardsHelper(member.getId(), limit, offset, sort, order);
	}
	
	@GetMapping(value = "getAllBoards")
	public Map<String, Object> getAllBoards(
			int limit,
			int offset,
//			String search,
			String sort,
			String order) {
		
		return getAllBoardsHelper(limit, offset, sort, order);
	}
	
	@GetMapping(value = "deleteOneBoard") 
	public Map<String, Object> deleteOneBoardFromBtn(
			Principal principal,
			Long boardId) {

		Map<String, Object> jsonObject = new HashMap<>();

		Member member;
		Board board;

		Long memberIdFromBoard;
		Long memberIdFromMember; 
		
		if(boardId == null) {
			jsonObject.put("message", "board id is empty");
		} else {
			member = memberService.findByUsername(principal.getName());
			board = boardService.findOne(boardId);

			memberIdFromBoard = board.getMember().getId();
			memberIdFromMember = member.getId();
			
			if (memberIdFromBoard.equals(memberIdFromMember)) {
				boardService.deleteById(boardId);
				jsonObject.put("message", "Board NO. " + boardId + " deleted");
			} else {
				jsonObject.put("message", "That board is not yours");
			}
		}
		
		return jsonObject;
	}
	
	@GetMapping(value = "deleteSelectedBoard") 
	public Map<String, Object> deleteSelectedBoard(
			Principal principal,
			@RequestParam("boardIds[]") List<Long> boardIds) {

		Map<String, Object> jsonObject = new HashMap<>();
		
		Member member;
		
		if(boardIds.isEmpty()) {
			jsonObject.put("message", "board id list is empty");
		} else {
			member = memberService.findByUsername(principal.getName());
			boardService.deleteByIdInAndMemberId(boardIds, member.getId());
			jsonObject.put("message", "Board NO. " + boardIds + " deleted");
		}
		
		return jsonObject;
	}
	
	private Map<String, Object> getAllBoardsHelper(
			Long memberId,
			int limit,
			int offset,
//			String search,
			String sort,
			String order) {
		
		System.out.println("sort : " + sort);
		
		Sort sorter;
		
		if("asc".equals(order)) {
			sorter = new Sort(Direction.ASC, sort);
		} else {
			sorter = new Sort(Direction.DESC, sort);
		}
		
		// Initial offset becomes
		PageRequest pageRequest = new PageRequest(offset / limit, limit, sorter);
		
		Long total;
		
		if(memberId <= 0L) {
			total = boardService.getTotalCountOfBoards();
		} else {
			total = boardService.getTotalCountOfBoards(memberId);	
		}
		
		List<Board> boardListInDB;
		
		if(memberId <= 0L) {
			boardListInDB = boardService.findAllBoardsWithPageRequest(pageRequest);
		} else {
			boardListInDB = boardService.findAllBoardsWithPageRequest(memberId, pageRequest);
		}
		
		List<BoardVO> viewList;
		
		Map<String, Object> jsonObject = new HashMap<>();
		
		viewList = boardListInDB.stream()
						 .map(board -> new BoardVO(board.getId(), board.getTitle(),board.getContent(), board.getCreateDate(), board.getLastUpdateDate(), board.getUpdateCount(), board.getHitCount(), board.getMember().getUsername()))
						 .collect(Collectors.toList());
		
		jsonObject.put("total", total);
		jsonObject.put("rows", viewList);
		
		return jsonObject;
	}
	
	private Map<String, Object> getAllBoardsHelper(
			int limit,
			int offset,
//			String search,
			String sort,
			String order) {

		return getAllBoardsHelper(0L, limit, offset, sort, order);
	}
}
