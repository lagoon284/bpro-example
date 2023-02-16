package com.keduit.bpro51.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.keduit.bpro51.entity.Board;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

	public BoardSearchImpl() {
		super(Board.class);
	}
	
	@Override
	public Page<Board> search1(Pageable pageable) {
		
		
		return null;
	}

}
