package com.keduit.bpro51.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="tbl_board")
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Board extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	
	@Column(length=500, nullable=false)
	private String title;
	
	@Column(length=2000, nullable=false)
	private String content;
	
	@Column(length=200, nullable=false)
	@ManyToOne
	private String writer;
	
	public void change(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
