package kr.co.ps119.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		value = "new_account", 
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class NewAccountController {

	@Autowired
	MemberService memberService;

	// Empty instance for validation tags in thymeleafs
	@ModelAttribute("memberForm")
	public MemberForm emptyMemberForm() {
		return new MemberForm();
	}

	@GetMapping(value = "registration_input_form")
	public String newAccount() {
		return "new_account/registration_input_form";
	}

	@PostMapping(value = "validate_input_form")
	public String createAccount(
			@Valid @ModelAttribute MemberForm memberForm,
			Errors errors,
			Model model) {

		if (errors.hasErrors()) {
			System.out.println("error!!!!!!!!!!!!!!!!");
			return "new_account/registration_input_form";
		}

		memberService.createAccount(memberForm);

		model.addAttribute("loginEmailId", memberForm.getEmail());
		
		System.out.println("success!!!!!!!!!!!!!!!!");
		return "forward:/user/user_main";
	}
}
