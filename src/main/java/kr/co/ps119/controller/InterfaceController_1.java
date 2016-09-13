package kr.co.ps119.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.ps119.entity.MemberUser;

public interface InterfaceController_1 {
	public Map<String, Object> ajaxTest1(@RequestParam(value = "nickname") String nickname, HttpSession session);
	
	public Map<String, Object> ajaxTest2(@RequestParam(value = "id") Long id);
	
	public String test();

	public String index();
	
	public String login();

	public String accountCreation();
}
