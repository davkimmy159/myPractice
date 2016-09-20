package kr.co.ps119.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
}