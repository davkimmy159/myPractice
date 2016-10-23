package kr.co.ps119.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContextUtils;

import kr.co.ps119.service.MemberService;

@Controller
@RequestMapping(
		value = "/",
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class IndexController {
	
	@Autowired
	MemberService memberService;
	
	@GetMapping(value = "test")
	public String test() {
		return "board/boardTest";
	}
	
	@GetMapping(value = "index")
	public String user_index(
			HttpServletRequest request,
			Model model) {
		
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap != null) {
			model.addAttribute("boardNonExistent", (boolean)flashMap.get("boardNonExistent"));
		} else {
			model.addAttribute("boardNonExistent", false);
		}
		
		return "index";
	}
}
