package com.keduit.bpro54.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClubMember {
	
	@Id
	private String email;
	
	private String password;
	private String name;
	private boolean fromSocial;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@Builder.Default
	private Set<ClubMemberRole> roleset = new HashSet<>();
	
	public void changePassword(String password) {
		this.password = password;
	}
	
	public void changeName(String name) {
		this.name = name;
	}

	public void addMemberRole(ClubMemberRole user) {
		
		roleset.add(user);
		
	}

}
