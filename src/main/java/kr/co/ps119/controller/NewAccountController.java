package kr.co.ps119.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.ps119.flag.NewAccount;
import kr.co.ps119.service.CreateAccountService;
import kr.co.ps119.vo.MemberForm;

@Controller
@RequestMapping(
		value = "new_account", 
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class NewAccountController {

	@Autowired
	CreateAccountService creaetAccountService;

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
		
		String returnPage = "new_account/registration_input_form";
		
		// JSR-303 check
		if (errors.hasErrors()) {
			return returnPage;
		}

		NewAccount saveStatus = creaetAccountService.createAccount(memberForm);
		String duplicationCheckMsg;
		
		switch(saveStatus) {
			case DUPLICATE_EMAIL :
				duplicationCheckMsg = NewAccount.DUPLICATE_EMAIL.getMessage();
				model.addAttribute("duplicationInput", memberForm.getEmail());
				model.addAttribute("duplicationError", duplicationCheckMsg);
				break;
			
			case DUPLICATE_USERNAME :
				duplicationCheckMsg = NewAccount.DUPLICATE_USERNAME.getMessage();
				model.addAttribute("duplicationInput", memberForm.getUsername());
				model.addAttribute("duplicationError", duplicationCheckMsg);
				break;
			
			// Checks unexpected error while saving entity into DB
			case UNEXPECTED_SERVER_ERROR :
				model.addAttribute("serverSideError", NewAccount.UNEXPECTED_SERVER_ERROR.getMessage());	
				break;
			
			case SUCCESSFUL :
				returnPage = "redirect:/member/member_main";
				break;
			
			// Checks special unexpected error 
			default :
				model.addAttribute("serverSideError", NewAccount.UNEXPECTED_SERVER_ERROR.getMessage());	
				break;
		}		
		
//		redirectAttrs.addFlashAttribute("loginEmailId", memberForm.getEmail());
		
		return returnPage;
	}
	
	private void afterSuccessfulCreation() {
		
	}
}
