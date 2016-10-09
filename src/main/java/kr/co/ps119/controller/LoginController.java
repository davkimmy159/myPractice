package kr.co.ps119.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.ps119.entity.Member;
import kr.co.ps119.service.MemberService;

@Controller
@RequestMapping(
		value = "login",
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping(value = "login")
	public String login() {
		return "login/login";
	}
	
	@PostMapping(value = "validate")
	public String loginValidate(
			@RequestParam String loginEmailId,
			@RequestParam String loginPassword,
			RedirectAttributes redirectAttrs) {
	
		String targetEmailId = loginEmailId;
		String targetPassword = loginPassword;
		boolean exist = false;
		
		exist = loginService.getLoginMemberInfo(targetEmailId);
		
		redirectAttrs.addFlashAttribute("loginEmailId", loginEmailId);
		
		return "redirect:/member/member_main";
	}	
}