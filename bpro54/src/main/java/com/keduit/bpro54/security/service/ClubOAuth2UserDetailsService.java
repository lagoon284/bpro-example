package com.keduit.bpro54.security.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.keduit.bpro54.entity.ClubMember;
import com.keduit.bpro54.entity.ClubMemberRole;
import com.keduit.bpro54.repository.ClubMemberRepository;
import com.keduit.bpro54.security.dto.ClubAuthMemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClubOAuth2UserDetailsService extends DefaultOAuth2UserService {

	private final ClubMemberRepository clubMemberRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		log.info("=================================");
		log.info("userRequest : " + userRequest);
		
		String clientName = userRequest.getClientRegistration().getClientName();
		log.info("clientName : " + clientName);
		log.info("parameter : " + userRequest.getAdditionalParameters());
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		Map<String, Object> paramMap = oAuth2User.getAttributes();
		
		String email = null;
		
		switch (clientName) {
		case "kakao" :
			email = getKakaoEmail(paramMap);
			break;
		}
		
		log.info("=================================");
		log.info(email);
//		oAuth2User.getAttributes().forEach((k, v) -> log.info(k + ":" + v));
		
		ClubMember member = saveSocialMember(email);
		
		ClubAuthMemberDTO clubAuthMemberDTO = new ClubAuthMemberDTO(
														member.getEmail(),
														member.getPassword(),
														true,
														member.getRoleset().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toList()),
														oAuth2User.getAttributes());
		
		clubAuthMemberDTO.setName(member.getName());
				
		
		return clubAuthMemberDTO;
	}

	private ClubMember saveSocialMember(String email) {
		// 기존에 소셜로 가입한적이 있는지 확인
		Optional<ClubMember> result = clubMemberRepository.findByEmail(email, true);
		
		if (result.isPresent()) {
			return result.get();
		}
		
		// 이력이 없다면 등록
		ClubMember clubMember = ClubMember.builder().email(email)
										  .name(email)
										  .password(passwordEncoder.encode("1111"))
										  .fromSocial(true)
										  .build();
		
		clubMember.addMemberRole(ClubMemberRole.USER);
		clubMemberRepository.save(clubMember);
		return clubMember;
	}

	private String getKakaoEmail(Map<String, Object> paramMap) {
		log.info("------------ KAKAO --------------------");
		Object value = paramMap.get("kakao_account");
		
		log.info("kakao_acount : " + value);
		
		LinkedHashMap accountMap = (LinkedHashMap)value;
		String email = (String)accountMap.get("email");
		log.info("email : " + email);
		return email;
	}

}








