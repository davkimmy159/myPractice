package kr.co.ps119.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(
		value = "user",
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class UserController {

	@PostMapping(value = "user_main")
	public String userMain() {

		return "user/user_main_body";
	}
}