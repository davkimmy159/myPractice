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

	@GetMapping(value = "member_main")
	public String userMain() {
		return "member/member_main";
	}
	
	@PostMapping(value = "create_room")
	public String createRoom() {
		System.out.println("createRoom!");
		
		return "redirect:/test2";
	}
	
	@GetMapping(value = "room")
	public String test() {
		
		return "test";
	}
}
