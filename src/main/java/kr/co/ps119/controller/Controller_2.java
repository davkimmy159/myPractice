package kr.co.ps119.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.ps119.entity.Board;
import kr.co.ps119.entity.Comment;
import kr.co.ps119.entity.Member;
import kr.co.ps119.service.Service_1;
import kr.co.ps119.service.MemberService;

@Controller
@RequestMapping("/test")
public class Controller_2 {

	@Autowired
	Service_1 service1;
	
	@Autowired
	MemberService service2;
	
	@GetMapping(value = "test1")
	@ResponseBody
	public Map<String, Object> test1() {
		
		List<Member> members = service2.test1(); 
		
		Map<String, Object> jsonObject = new HashMap<>();
		
		jsonObject.put("members", members);
		
		return jsonObject;
	}
	
	/*
	@GetMapping(value = "test2")
	@ResponseBody
	public Map<String, Object> test2() {
		
		List<MemberUser> list = service1.findAll();

		Map<String, Object> jsonObject = new HashMap<>();

		jsonObject.put("list", list);
		jsonObject.put("size", list.size());

		return jsonObject;
	}
	*/
	
	/*
	@GetMapping(value = "ajaxTest1")
	@ResponseBody
	public Map<String, Object> ajaxTest1(@RequestParam(value = "nickname") String nickname, HttpSession session) {
		MemberUser memberUser = new MemberUser(nickname + "@mail.com", nickname);

		Long id = service1.save(memberUser).getId();

		List<MemberUser> list = service1.findAll();

		Map<String, Object> jsonObject = new HashMap<>();

		jsonObject.put("list", list);
		jsonObject.put("size", list.size());
		jsonObject.put("id", id);

		System.out.println(session);
		return jsonObject;
	}
	*/

	/*
	@GetMapping(value = "ajaxTest2")
	@ResponseBody
	public Map<String, Object> ajaxTest2(@RequestParam(value = "id") Long id) {
		service1.delete(id);

		Map<String, Object> jsonObject = new HashMap<>();
		
		List<MemberUser> list = service1.findAll();

		jsonObject.put("list", list);
		jsonObject.put("size", list.size());

		return jsonObject;
	}
	*/
}
