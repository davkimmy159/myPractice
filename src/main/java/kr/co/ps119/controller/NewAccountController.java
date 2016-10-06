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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.ps119.service.MemberService;
import kr.co.ps119.vo.MemberForm;

@Controller
@RequestMapping(
		value = "new_account", 
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class NewAccountController {

	@Autowired
	MemberService memberService;

	// Empty instance for validation tags in thymeleaf
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
			Model model,
			RedirectAttributes redirectAttrs) {
		
		boolean successFlag = false;
		
		// JSR-303 check
		if (errors.hasErrors()) {
			return "new_account/registration_input_form";
		}

		successFlag = memberService.createAccount(memberForm);

		// Unexpected entity save error check
		if(!successFlag) {
			model.addAttribute("serverError", "Unexpected server error has occurred!Please try again");
			return "new_account/registration_input_form";
		}
		
		redirectAttrs.addFlashAttribute("loginEmailId", memberForm.getEmail());
		
		return "redirect:/member/member_main";
	}
}
