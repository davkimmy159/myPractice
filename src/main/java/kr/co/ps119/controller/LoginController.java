package kr.co.ps119.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.ps119.dto.MemberForm;
import kr.co.ps119.service.MemberService;

@Controller
@RequestMapping(
		value = "login",
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class LoginController {
	
	@Autowired
	MemberService memberService;
	
	// Empty instance
	@ModelAttribute("memberForm")
	public MemberForm emptyMemberForm() {
		return new MemberForm();
	}
	
	@GetMapping
	public String login() {
		return "login";
	}
	
	@GetMapping(value = "account_creation")
	public String accountCreation() {
		return "account_creation";
	}
	
	@PostMapping(value = "account/create")
	public String createAccount(
			@Valid @ModelAttribute MemberForm memberForm,
			Errors errors,
			Model model) {
		
		if(errors.hasErrors()) {
			System.out.println("error!!!!!!!!!!!!!!!!");
			return "account_creation";
		}
		
		memberService.createAccount(memberForm);
		
		System.out.println("success!!!!!!!!!!!!!!!!");
		return "redirect:/user_main"; 
	}
}