package com.keduit.bpro01.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.keduit.bpro01.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {
	
	List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);
	
	Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);
	
	void deleteMemoByMnoLessThan(Long num);
	
	@Query("select m from Memo m order by m.mno desc")
	List<Memo> getListDesc();
}
