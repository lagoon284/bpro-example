package com.keduit.bpro54.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.keduit.bpro54.security.dto.ClubAuthMemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller	// 맵핑되어 화면을 출력해줌!
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/sample")
public class SampleController {
	
	@GetMapping("/all")
	public void exAll() {
		log.info("exAll.....................");
	}
	
	@GetMapping("/member")
	public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
		log.info("exMember.....................");
		log.info("clubAuthMemberDTO : " + clubAuthMemberDTO);
	}
	
	@GetMapping("/admin")
	public void exAdmin() {
		log.info("exAdmin.....................");
	}
	
	@GetMapping("/login")
	public void loginGet(String error, String logout) {
		log.info("..................login get..................");
		log.info("login get: " + logout);
		
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/register")
	public void registerGet() {
		
	}
	

}
