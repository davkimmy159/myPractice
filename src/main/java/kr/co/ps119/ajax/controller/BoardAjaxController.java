package kr.co.ps119.ajax.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ps119.entity.Board;
import kr.co.ps119.entity.Member;
import kr.co.ps119.repository.BoardRepository;
import kr.co.ps119.service.BoardHistoryService;
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
	private BoardRepository boardRepo;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BoardHistoryService boardHisService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private Map<Long, Map<String, MemberVO>> boardStompConnMap;
	
	@GetMapping(value = "httpGetTest")
	public Map<String, Object> httpGetTest(
			Principal principal,
			Long boardId) {
		Map<String, Object> jsonObject = new HashMap<>();
		
		jsonObject.put("key1", "get test 1");
		jsonObject.put("key2", "get test 2");
		
		return jsonObject;
	}
	
	@PostMapping(value = "httpPostTest")
	public Map<String, Object> httpPostTest(
			Principal principal,
			@RequestBody Test test,
			@RequestHeader HttpHeaders headers,
			@RequestParam("id") Long id) {
		System.out.println(test);
		System.out.println(headers);
		System.out.println(id);
		Map<String, Object> jsonObject = new HashMap<>();
		
		jsonObject.put("key1", "post test 1");
		jsonObject.put("key2", "post test 2");
		
		return jsonObject;
	}
	
	private static class Test {
		private String name;
		
		@Override
		public String toString() {
			return name;
		}
		
		public String getName() {
			return this.name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
	}
	
	@GetMapping(value = "updateJoinMemberTable")
	public Map<String, Object> updateJoinMemberTable(
			Principal principal,
			Long boardId) {
		
		Map<String, Object> jsonObject = new HashMap<>();
		
		if(boardId != null) {
			Map<String, MemberVO> memberMap = boardStompConnMap.get(boardId);
			
			if(memberMap != null) {
				jsonObject.put("memberList", memberMap.values());
			} else {
				System.out.println("server error occurred, member list doesn't exist");
				jsonObject.put("message", "server error occurred");
			}
			
		} else {
			System.out.println("board id doesn't exist");
			jsonObject.put("message", "board id doesn't exist");
		}

		return jsonObject;
	}
	
	@GetMapping(value = "updateBoardDB")
	public Map<String, Object> updateBoardDB(
			@RequestParam("username") String username,
			@RequestParam("boardId") Long boardId,
			@RequestParam("editorContent") String editorContent) {

		Long boardIdAfterUpdate = boardService.updateBoardDBContent(username, boardId, editorContent);
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
			String search,
			String sort,
			String order) {
		
		Member member = memberService.findByUsername(principal.getName());
		
		return getAllBoardsHelper(member.getId(), limit, offset, search, sort, order);
	}
	
	@GetMapping(value = "getAllBoards")
	public Map<String, Object> getAllBoards(
			int limit,
			int offset,
			String search,
			String sort,
			String order) {
		
		return getAllBoardsHelper(limit, offset, search, sort, order);
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
			String search,
			String sort,
			String order) {
		
		System.out.println("limit  : " + limit);
		System.out.println("offset : " + offset);
		System.out.println("search : " + search);
		System.out.println("search null? : " + search == null);
		System.out.println("sort   : " + sort);
		System.out.println("order  : " + order);
		
		if(!(null == search || search.isEmpty())) {
			System.out.println("total + search          : " + boardRepo.countByTitleLike(search));
			System.out.println("total + search + member : " + boardRepo.countByMemberIdAndTitleLike(memberId, search));
		}
		
		Sort sorter;
		
		if("asc".equals(order)) {
			sorter = new Sort(Direction.ASC, sort);
		} else {
			sorter = new Sort(Direction.DESC, sort);
		}
		
		// Initial offset becomes
		PageRequest pageRequest = new PageRequest(offset / limit, limit, sorter);
		
		Long total = 0L;
		List<Board> boardListInDB = Collections.emptyList();
		
		if(memberId <= 0L) {
			if(null == search || search.isEmpty()) {
				total = boardService.getTotalCountOfBoards();
				boardListInDB = boardService.findAllBoardsWithPageRequest(pageRequest);
			} else {
				total = boardService.getTotalCountOfBoardsTitleLike(search);
				boardListInDB = boardService.findAllBoardsTitleLikeWithPageRequest(search, pageRequest);
			}
			
		} else {
			if(null == search || search.isEmpty()) {
				total = boardService.getTotalCountOfBoards(memberId);	
				boardListInDB = boardService.findAllBoardsWithPageRequest(memberId, pageRequest);				
			} else {
				total = boardService.getTotalCountOfBoardsTitleLike(memberId, search);
				boardListInDB = boardService.findAllBoardsTitleLikeWithPageRequest(memberId, search, pageRequest);
			}
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
			String search,
			String sort,
			String order) {

		return getAllBoardsHelper(0L, limit, offset, search, sort, order);
	}
}
