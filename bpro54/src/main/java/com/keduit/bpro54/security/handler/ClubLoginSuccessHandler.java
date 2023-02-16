package com.keduit.bpro54.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.keduit.bpro54.security.dto.ClubAuthMemberDTO;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ClubLoginSuccessHandler implements AuthenticationSuccessHandler {
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private PasswordEncoder passwordEncoder;

	public ClubLoginSuccessHandler(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		log.info("-------------------------------------------");
		log.info("onAuthenticationSuccess");
		
		ClubAuthMemberDTO authMember = (ClubAuthMemberDTO)authentication.getPrincipal();
		
		boolean fromSocial = authMember.isFromSocial();
		
		log.info("사용자 정보를 수정 여부?" + fromSocial);
		
		boolean passwordResult = passwordEncoder.matches("1111", authMember.getPassword());
		
		if(fromSocial && passwordResult) {
			redirectStrategy.sendRedirect(request, response, "/sample/modify?from=social");
		}
		
		
	}

}
