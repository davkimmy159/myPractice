package kr.co.ps119.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(
		value = "login",
		method = { RequestMethod.GET, RequestMethod.POST }
)
public class LoginController {
	
	@GetMapping(value = "login")
	public String login() {
		return "login/login";
	}

	/*
	// Controller method, not under authentication
	@GetMapping(value = "login_after_registraiton")
	public String requestHandler(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	 
		// Save Request for security redirect later
		new HttpSessionRequestCache().saveRequest(request, response);
		 
		// Commence authentication using login page
		new LoginUrlAuthenticationEntryPoint("/login/login").commence(request, response, new InsufficientAuthenticationException("Full authentication required"));
//		return null; // Stop request handling here
	 
		// ... do normal processing
		return "/member/member_main";  
	}
	*/
}