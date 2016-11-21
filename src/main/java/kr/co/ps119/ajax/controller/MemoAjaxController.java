package kr.co.ps119.ajax.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ps119.entity.Member;
import kr.co.ps119.entity.Memo;
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
	private MemoService memoService;
	
	@GetMapping(value = "getAllMemosOfBoard")
	private Map<String, Object> getAllMemosOfBoard(
			Long boardId,
			int offset) {
		
		Long total = memoService.getTotalCountOfMemos(boardId);
		PageRequest pageRequest = new PageRequest(offset - 1, 10);
		
		List<Memo> memoList;
		
		memoList = memoService.findAllMemosWithPageRequest(boardId, pageRequest);

		memoList.forEach(memo -> memo.getMember().getUsername());
		
		Map<String, Object> jsonObject = new HashMap<>();
		
		jsonObject.put("total", total);
		jsonObject.put("memoList", memoList);
		
		return jsonObject;
	}
}
