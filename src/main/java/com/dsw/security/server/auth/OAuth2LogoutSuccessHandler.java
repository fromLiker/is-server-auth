package com.dsw.security.server.auth;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

@Component
public class OAuth2LogoutSuccessHandler implements LogoutSuccessHandler {

	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String redirectUri = request.getParameter("redirect_uri");
		if(!StringUtils.isEmpty(redirectUri)) {
			response.sendRedirect(redirectUri);
		}
	}
	
}
