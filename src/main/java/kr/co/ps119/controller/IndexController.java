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
	private MemberService memberService;
	
	@GetMapping(value = "/")
	public String index1() {
		
		return "redirect:/index";
	}
	
	@GetMapping(value = "index")
	public String index2(
			HttpServletRequest request,
			Model model) {
		
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap != null) {
			model.addAttribute("boardNonExistent", flashMap.get("boardNonExistent"));
		} else {
			model.addAttribute("boardNonExistent", false);
		}
		
		return "index";
	}
}
