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

import kr.co.ps119.flag.NewAccountFlag;
import kr.co.ps119.service.AccountService;
import kr.co.ps119.vo.MemberForm;

@Controller
@RequestMapping(
		value = "new_account", 
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class NewAccountController {

	@Autowired
	AccountService creaetAccountService;

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
			RedirectAttributes model) {
		
		String returnPage = "redirect:/new_account/registration_input_form";
		
		// JSR-303 check
		if (errors.hasErrors()) {
			return returnPage;
		}

		// Gets result sign from service bean
		NewAccountFlag saveStatus = creaetAccountService.createAccount(memberForm);
		String duplicationCheckMsg;
		
		// Does next action depending on result sign above
		// This is server-side validation logic
		switch(saveStatus) {
			// Duplication of email
			case DUPLICATE_EMAIL :
				// Gets the alert message from result sign
				duplicationCheckMsg = NewAccountFlag.DUPLICATE_EMAIL.getMessage();
				
				// Sends email input and alert message to view through model
				model.addFlashAttribute("duplicationInput", memberForm.getEmail());
				model.addFlashAttribute("duplicationError", duplicationCheckMsg);
				break;

			// Duplication of username
			case DUPLICATE_USERNAME :
				// Gets the alert message from result sign
				duplicationCheckMsg = NewAccountFlag.DUPLICATE_USERNAME.getMessage();

				// Sends username input and alert message to view through model
				model.addFlashAttribute("duplicationInput", memberForm.getUsername());
				model.addFlashAttribute("duplicationError", duplicationCheckMsg);
				break;
			
			// Unexpected error
			// ex) save error ...
			case UNEXPECTED_SERVER_ERROR :
				// Gets the alert message from result sign and sends to view through model
				model.addFlashAttribute("serverSideError", NewAccountFlag.UNEXPECTED_SERVER_ERROR.getMessage());	
				break;
			
			// Successful creation of account
			case SUCCESSFUL :
				// Just redirects to main page for member
				returnPage = "redirect:/member/member_main";
				break;
			
			// Checks any unexpected special error
			default :
				model.addFlashAttribute("serverSideError", NewAccountFlag.UNEXPECTED_SERVER_ERROR.getMessage());	
				break;
		}		
		
		return returnPage;
	}
	
	private void afterSuccessfulCreation() {
		
	}
}
