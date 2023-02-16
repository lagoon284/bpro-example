package com.keduit.bpro51.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.keduit.bpro51.entity.Board;

public interface BoardSearch {

	Page<Board> search1(Pageable pageable);
}
