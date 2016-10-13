package kr.co.ps119.controller;

import kr.co.ps119.service.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping(
		value = "/",
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class BasicTestController {
	
	@Autowired
	MemberService memberService;
	
	@GetMapping(value = "test")
	public String test() {
		return "board/board";
	}
	
	@GetMapping(value = "index")
	public String user_index() {
		return "index";
	}
}
