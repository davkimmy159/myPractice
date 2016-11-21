package kr.co.ps119.ajax.controller;

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

import kr.co.ps119.entity.Board;
import kr.co.ps119.entity.Memo;
import kr.co.ps119.service.MemoService;

@RestController
@RequestMapping(
		value = "/ajax/memo",
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class MemoAjaxController {

	@Autowired
	private MemoService memoService;
	
	@GetMapping(value = "getAllMemosOfBoard")
	private Map<String, Object> getAllMemosOfBoard(
			Long boardId,
			int limit,
			int offset,
//			String search,
			String sort,
			String order) {
		
		System.out.println("boardId : " + boardId);
		System.out.println("limit : " + limit);
		System.out.println("offset : " + offset);
//		System.out.println("sort : " + sort);
		System.out.println("order : " + order);
		
		Sort sorter;
		
		if("asc".equals(order)) {
			sorter = new Sort(Direction.ASC, sort);
		} else {
			sorter = new Sort(Direction.DESC, sort);
		}
		
		// Initial offset becomes
		PageRequest pageRequest = new PageRequest(offset / limit, limit, sorter);
		
		Long total;
		
		total = memoService.getTotalCountOfMemos(boardId);	
		
		List<Memo> memoListInDB;
		
		memoListInDB = memoService.findAllMemosWithPageRequest(boardId, pageRequest);
		
		Map<String, Object> jsonObject = new HashMap<>();
		
		jsonObject.put("total", total);
		jsonObject.put("rows", memoListInDB);
		
		return jsonObject;
	}
}
