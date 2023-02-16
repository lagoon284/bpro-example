package com.keduit.bpro54.security;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.keduit.bpro54.entity.ClubMember;
import com.keduit.bpro54.entity.ClubMemberRole;
import com.keduit.bpro54.repository.ClubMemberRepository;

@SpringBootTest
public class ClubMemberTests {
	
	@Autowired
	private ClubMemberRepository clubMemberRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void insertDummies() {
		
		IntStream.rangeClosed(1, 100).forEach(i -> {
			
			ClubMember clubMember = ClubMember.builder()
											.email("user" + i + "@abc.com")
											.name("사용자" + i)
											.fromSocial(false)
											.password(passwordEncoder.encode("1111"))
											.build();
			
			clubMember.addMemberRole(ClubMemberRole.USER);
			
			if(i > 80) {
				clubMember.addMemberRole(ClubMemberRole.MANAGER);
			}
			
			if(i > 90) {
				clubMember.addMemberRole(ClubMemberRole.ADMIN);
			}
			
			clubMemberRepository.save(clubMember);
		});
	}
	
	@Test
	public void testRead() {
		Optional<ClubMember> result = clubMemberRepository.findByEmail("user5@abc.com", false);
		
		ClubMember clubMember = result.get();
		
		System.out.println(clubMember);
	}
	

}
