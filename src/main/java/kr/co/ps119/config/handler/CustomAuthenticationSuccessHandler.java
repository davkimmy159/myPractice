package kr.co.ps119.config.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	private String targetUrlParameter;
	
	private String defaultUrl;
	
	private boolean useReferer;
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	public CustomAuthenticationSuccessHandler() {
		this("", "/", false);
	}
	
	public CustomAuthenticationSuccessHandler(String targetUrlParameter, String defaultUrl, boolean useReferer) {
		this.targetUrlParameter = targetUrlParameter;
		this.defaultUrl = defaultUrl;
		this.useReferer = useReferer;
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if(session == null) {
			return ;
		}
		
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
		clearAuthenticationAttributes(request);
		
		int intRedirectStrategy = decideRedirectStrategy(request, response);
		
		switch(intRedirectStrategy) {
		case 1:
			useTargetUrl(request, response);
		case 2:
			useSessionUrl(request, response);
		case 3:
			useRefererUrl(request, response);
		case 4:
			useDefaultUrl(request, response);
		}
	}

	private int decideRedirectStrategy(HttpServletRequest request, HttpServletResponse response) {
		
		int result = 0;
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if(!"".equals(targetUrlParameter)) {
			String targetUrl = request.getParameter(targetUrlParameter);
			if(StringUtils.hasText(targetUrl)) {
				result = 1;
			} else {
				if(savedRequest != null) {
					result = 2;
				} else {
					String refererUrl = request.getHeader("REFERER");
					if(useReferer && StringUtils.hasText(refererUrl)) {
						result = 3;
					} else {
						result = 0;
					}
				}
			}
			
			return result;
		}
		
		if(savedRequest != null) {
			result = 2;
			
			return result;
		}
		
		String refererUrl = request.getHeader("REFERER");
		if(useReferer && StringUtils.hasText(refererUrl)) {
			result = 3;
		} else {
			result = 0;
		}
		
		return result;
	}
	
	private void useTargetUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if(savedRequest != null) {
			requestCache.removeRequest(request,  response);
		}
		
		String targetUrl = request.getParameter(targetUrlParameter);
		redirectStrategy.sendRedirect(request,  response, targetUrl);
	}
	
	private void useSessionUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		String targetUrl = savedRequest.getRedirectUrl();
		redirectStrategy.sendRedirect(request,  response, targetUrl);
	}
	
	private void useRefererUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String targetUrl = request.getHeader("REFERER");
		redirectStrategy.sendRedirect(request,  response, targetUrl);
	}
	
	private void useDefaultUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		redirectStrategy.sendRedirect(request,  response, defaultUrl);
	}
	
	public String getTargetUrlParameter() {
		return targetUrlParameter;
	}
	
	public RequestCache getRequestCache() {
		return requestCache;
	}

	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}

	public String getDefaultUrl() {
		return defaultUrl;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

	public boolean isUseReferer() {
		return useReferer;
	}

	public void setUseReferer(boolean useReferer) {
		this.useReferer = useReferer;
	}

	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	public void setTargetUrlParameter(String targetUrlParameter) {
		this.targetUrlParameter = targetUrlParameter;
	}
}
