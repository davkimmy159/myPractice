package kr.co.ps119.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(
		value = "login",
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class LoginController {
	
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
		
		redirectAttrs.addFlashAttribute("loginEmailId", loginEmailId);
		
		System.out.println("id : " + targetEmailId + ", password : " + targetPassword);
		
		return "redirect:/user/user_main";
	}
	
}