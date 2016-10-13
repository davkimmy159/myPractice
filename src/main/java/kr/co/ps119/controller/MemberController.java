package kr.co.ps119.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(
		value = "member",
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class MemberController {

	@PostMapping(value = "member_main")
	public String userMain() {
		return "member/member_main_body";
	}
	
	@GetMapping(value = "createRoom")
	public String createRoom() {
		return "test";
	}
}
