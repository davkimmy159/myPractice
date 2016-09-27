package kr.co.ps119.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(
		value = "board",
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class BoardController {

	@GetMapping(value = "board")
	public String main() {
		return "board/board_body";
	}
}
