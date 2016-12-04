package kr.co.ps119.ajax.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import kr.co.ps119.entity.Memo;
import kr.co.ps119.service.BoardService;
import kr.co.ps119.service.MemberService;
import kr.co.ps119.service.MemoService;

@RestController
@RequestMapping(
		value = "/ajax/memo",
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class MemoAjaxController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private MemoService memoService;

	@GetMapping(value = "updateMemo")
	public Map<String, Object> updateMemo(
			Principal principal,
			@RequestParam Long memoId,
			@RequestParam String memoTitle,
			@RequestParam String memoContent) {

		Map<String, Object> jsonObject = new HashMap<>();

		String trimmedMemoTitle;
		String trimmedMemoContent;
		
		Memo memo;
		
		if(memoTitle == null || "".equals(trimmedMemoTitle = memoTitle.trim())) {
			jsonObject.put("result", "Memo title is invalid");
		} else if (memoContent == null || "".equals(trimmedMemoContent = memoContent.trim())) {
			jsonObject.put("result", "Memo content is invalid");
		} else {
			
			memo = memoService.findOne(memoId);
			
			memo.setTitle(trimmedMemoTitle);
			memo.setContent(trimmedMemoContent);
		
			memo = memoService.updateOneMemo(memo);
			
			if(memo == null) {
				jsonObject.put("result", "Server error occurred during saving");
			} else {
				jsonObject.put("result", "Memo is updated successfully");
			}
		}
		
		return jsonObject;
	}
	
	@GetMapping(value = "createMemo")
	public Map<String, Object> createMemo(
			Principal principal,
			@RequestParam Long boardId,
			@RequestParam String memoTitle,
			@RequestParam String memoContent) {

		Map<String, Object> jsonObject = new HashMap<>();

		String trimmedMemoTitle;
		String trimmedMemoContent;
		
		Member member = memberService.findByUsername(principal.getName());
		Board board = boardService.findOne(boardId);
		Memo memo;
		
		if(memoTitle == null || "".equals(trimmedMemoTitle = memoTitle.trim())) {
			jsonObject.put("result", "Memo title is invalid");
		} else if (memoContent == null || "".equals(trimmedMemoContent = memoContent.trim())) {
			jsonObject.put("result", "Memo content is invalid");
		} else {
			memo = new Memo();
			memo.setMember(member);
			memo.setBoard(board);
			memo.setTitle(trimmedMemoTitle);
			memo.setContent(trimmedMemoContent);
		
			memo = memoService.saveOneMemo(memo);
			if(memo == null) {
				jsonObject.put("result", "Server error occurred during saving");
			} else {
				jsonObject.put("result", "Memo is saved successfully");
			}
		}
		
		return jsonObject;
	}

	@GetMapping(value = "getAllMemosOfBoard")
	private Map<String, Object> getAllMemosOfBoard(
			Long boardId,
			int offset) {
		
		Sort sorter = new Sort(Direction.DESC, "id");
		
		Long total = memoService.getTotalMemoCountOfBoard(boardId);
		PageRequest pageRequest = new PageRequest(offset - 1, 10, sorter);
		
		List<Memo> memoList;
		
		memoList = memoService.findAllMemosWithPageRequest(boardId, pageRequest);

		memoList.forEach(memo -> {
			memo.setMemberUsername(memo.getMember().getUsername());
			memo.setMemberNull();
		});
		
		Map<String, Object> jsonObject = new HashMap<>();
		
		jsonObject.put("total", total);
		jsonObject.put("memoList", memoList);
		
		return jsonObject;
	}
	
	@GetMapping(value = "deleteOneMemo") 
	public Map<String, Object> deleteOneMemoFromBtn(
			Principal principal,
			Long memoId) {

		Map<String, Object> jsonObject = new HashMap<>();

		Member member;
		Memo memo;

		Long memberIdFromMemo;
		Long memberIdFromMember; 
		
		if(memoId == null) {
			jsonObject.put("message", "board id is empty");
		} else {
			member = memberService.findByUsername(principal.getName());
			memo = memoService.findOne(memoId);

			memberIdFromMemo = memo.getMember().getId();
			memberIdFromMember = member.getId();
			
			if (memberIdFromMemo.equals(memberIdFromMember)) {
				memoService.deleteById(memoId);
				jsonObject.put("message", "Memo NO. " + memoId + " deleted");
			} else {
				jsonObject.put("message", "That memo is not yours");
			}
		}
		
		return jsonObject;
	}
}
