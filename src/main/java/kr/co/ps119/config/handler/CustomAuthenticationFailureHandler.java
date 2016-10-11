package kr.co.ps119.config.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private String loginId;
	
	private String loginPassword;
	
	private String loginRedirect;
	
	private String securityExceptionMsg;
	
	private String defaultFailureUrl;
	
	public CustomAuthenticationFailureHandler() {
		this("", "", "", "", "");
	}
	
	public CustomAuthenticationFailureHandler(String loginId, String loginPassword, String loginRedirect, String securityExceptionMsg, String defaultFailureUrl) {
		this.loginId = loginId;
		this.loginPassword = loginPassword;
		this.loginRedirect = loginRedirect;
		this.securityExceptionMsg = securityExceptionMsg;
		this.defaultFailureUrl = defaultFailureUrl;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		
		String tmpLoginId = request.getParameter(this.loginId);
		String tmpLoginPassword= request.getParameter(this.loginPassword);
		String tmpLoginRedirect = request.getParameter(this.loginRedirect);
		
		request.setAttribute("loginId", tmpLoginId);
		request.setAttribute("loginPassword", tmpLoginPassword);
		request.setAttribute("loginRedirect", tmpLoginRedirect);
		
		request.setAttribute(this.securityExceptionMsg, exception.getMessage());
		
		request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
		
		// should hold the attributes from your last request
		// FlashMap lastAttributes = RequestContextUtils.getInputFlashMap(request);
		
		System.out.println(tmpLoginId);
		System.out.println(tmpLoginPassword);
		System.out.println(tmpLoginRedirect);
		
		/*
		// will hold the attributes for your next request
		FlashMap forNextRequest = RequestContextUtils.getOutputFlashMap(request);
		
		forNextRequest.put("loginId", "asd");
		forNextRequest.put("loginPassword", "asdasd");
		forNextRequest.put("loginRedirect", "asdasdasd");
		forNextRequest.put("securityExceptionMsg", this.securityExceptionMsg);
		
		response.sendRedirect(defaultFailureUrl);
		*/
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getLoginRedirect() {
		return loginRedirect;
	}

	public void setLoginRedirect(String loginRedirect) {
		this.loginRedirect = loginRedirect;
	}

	public String getsecurityExceptionMsg() {
		return securityExceptionMsg;
	}

	public void setsecurityExceptionMsg(String securityExceptionMsg) {
		this.securityExceptionMsg = securityExceptionMsg;
	}

	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}
}
