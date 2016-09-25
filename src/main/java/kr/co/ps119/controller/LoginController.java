package kr.co.ps119.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
			@RequestParam String loginPassword) {
		
		String targetEmailId = loginEmailId;
		String targetPassword = loginPassword;
		
		System.out.println(targetEmailId + ", " + targetPassword);
		
		return "forward:/user_main";
	}
	
}