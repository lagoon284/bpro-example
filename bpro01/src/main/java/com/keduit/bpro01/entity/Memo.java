package com.keduit.bpro01.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="tbl_memo")
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Memo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long mno;
	
	@Column(length=200, nullable=false)
	private String memoText;
	
	@Column(nullable=false, columnDefinition="date default sysdate", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate = new Date();

}
