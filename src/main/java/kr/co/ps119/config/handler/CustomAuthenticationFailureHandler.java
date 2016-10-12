package kr.co.ps119.config.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private String loginIdParamName;
	
	private String loginPasswordParamName;
	
	private String loginRedirectParamName;
	
	private String securityExceptionMsgParamName;
	
	private String defaultFailureUrlParamName;
	
	private String defaultFailureUrl;
	
	public CustomAuthenticationFailureHandler() {
		this("", "", "", "", "", "");
	}
	
	public CustomAuthenticationFailureHandler(String loginIdParamName,
											  String loginPasswordParamName,
											  String loginRedirectParamName,
											  String securityExceptionMsgParamName,
											  String defaultFailureUrlParamName,
											  String defaultFailureUrl) {
		this.loginIdParamName = loginIdParamName;
		this.loginPasswordParamName = loginPasswordParamName;
		this.loginRedirectParamName = loginRedirectParamName;
		this.securityExceptionMsgParamName = securityExceptionMsgParamName;
		this.defaultFailureUrlParamName = defaultFailureUrlParamName;
		this.defaultFailureUrl = defaultFailureUrl;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		request.setAttribute(this.loginIdParamName, request.getParameter(this.loginIdParamName));
		request.setAttribute(this.loginPasswordParamName, request.getParameter(this.loginPasswordParamName));
		request.setAttribute(this.loginRedirectParamName, request.getParameter(this.loginRedirectParamName));
		request.setAttribute(this.securityExceptionMsgParamName, exception.getMessage());
		request.setAttribute(this.defaultFailureUrlParamName, this.defaultFailureUrl);
		
		request.getRequestDispatcher(this.defaultFailureUrl).forward(request, response);
	}

	public String getLoginIdParamName() {
		return loginIdParamName;
	}

	public void setLoginIdParamName(String loginIdParamName) {
		this.loginIdParamName = loginIdParamName;
	}

	public String getLoginPasswordParamName() {
		return loginPasswordParamName;
	}

	public void setLoginPasswordParamName(String loginPasswordParamName) {
		this.loginPasswordParamName = loginPasswordParamName;
	}

	public String getLoginRedirectParamName() {
		return loginRedirectParamName;
	}

	public void setLoginRedirectParamName(String loginRedirectParamName) {
		this.loginRedirectParamName = loginRedirectParamName;
	}

	public String getSecurityExceptionMsgParamName() {
		return securityExceptionMsgParamName;
	}

	public void setSecurityExceptionMsgParamName(String securityExceptionMsgParamName) {
		this.securityExceptionMsgParamName = securityExceptionMsgParamName;
	}

	public String getDefaultFailureUrlParamName() {
		return defaultFailureUrlParamName;
	}

	public void setDefaultFailureUrlParamName(String defaultFailureUrlParamName) {
		this.defaultFailureUrlParamName = defaultFailureUrlParamName;
	}

	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}
}
