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
import org.springframework.web.bind.annotation.RestController;

import kr.co.ps119.entity.BoardHistory;
import kr.co.ps119.service.BoardHistoryService;
import kr.co.ps119.service.BoardService;
import kr.co.ps119.service.MemberService;

@RestController
@RequestMapping(
		value = "/ajax/board/history",
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class BoardHistoryAjaxController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardHistoryService boardHisService;
	
	@GetMapping(value = "getHistoriesOfBoard")
	public Map<String, Object> getHistoriesOfBoard(
			Principal principal,
			Long boardId,
			int offset) {
		
		Sort sorter = new Sort(Direction.DESC, "createDate");
		
		Long total = boardHisService.getTotalCountOfHistories(boardId);
		PageRequest pageRequest = new PageRequest(offset - 1, 10, sorter);
		
		List<BoardHistory> historyList;
		
		historyList = boardHisService.findAllHistoriesWithPageRequest(boardId, pageRequest);

		historyList.forEach(history -> {
			history.setMemberUsername(history.getMember().getUsername());
			history.setMemberNull();
		});
		
		Map<String, Object> jsonObject = new HashMap<>();
		
		jsonObject.put("total", total);
		jsonObject.put("historyList", historyList);
		
		return jsonObject;
	}
}
