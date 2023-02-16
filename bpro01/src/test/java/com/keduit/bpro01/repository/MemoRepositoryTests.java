package com.keduit.bpro01.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import com.keduit.bpro01.entity.Memo;

@SpringBootTest
public class MemoRepositoryTests {
	
	@Autowired
	MemoRepository memoRepository;
	
	@Test
	public void testClass() {
		System.out.println(memoRepository.getClass().getName());
	}

	@Test
	public void testInsertDummies() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Memo memo = Memo.builder().memoText("Sample memo..... " + i).build();
			memoRepository.save(memo);
		});
	}
	
	@Test
	public void testSelect() {
		
		Long mno = 100L;
		Optional<Memo> result = memoRepository.findById(mno);
		
		System.out.println("--------------------");
		if(result.isPresent()) {
			Memo memo = result.get();
			System.out.println(memo);
		}
	}
	
	@Transactional
	@Test
	public void testSelect2() {
		
		Long mno = 100L;
		Memo memo = memoRepository.getOne(mno);
		
		System.out.println("----------------");
		System.out.println(memo);
	}
	
	@Test
	public void testDelete() {
		Long mno = 100L;
		memoRepository.deleteById(mno);
		
	}
	
	@Test
	public void testUpdate() {
		Memo memo = Memo.builder().mno(99L).memoText("Update text.....").build();
		System.out.println(memoRepository.save(memo));
	}
	
	@Test
	public void testPageDefault() {
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<Memo> result = memoRepository.findAll(pageable);
		
		System.out.println(result);
		
		System.out.println("----------------------------------");
		
		System.out.println("Total pages : " + result.getTotalPages());
		System.out.println("Total count : " + result.getTotalElements());
		System.out.println("page number : " + result.getNumber());
		System.out.println("page size : " + result.getSize());
		System.out.println("has next page? : " + result.hasNext());
		System.out.println("first page? : " + result.isFirst());
		
		System.out.println("----------------------------------");
		for (Memo memo: result.getContent()) {
			System.out.println(memo);
		}
		
		
	}
	
	@Test
	public void testSort() {
		Sort sort1 = Sort.by("mno").descending();
		Sort sort2 = Sort.by("memoText").ascending();
		Sort sortAll = sort1.and(sort2);
		
//		Pageable pageable = PageRequest.of(0, 10, sort1);
		Pageable pageable = PageRequest.of(0, 10, sortAll);
		Page<Memo> result = memoRepository.findAll(pageable);
		
		result.get().forEach(memo -> {
			System.out.println(memo);
		});
	}
	
	@Test
	public void testQueryMethods() {
		List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(65L, 90L);
		
		for (Memo memo : list) {
			System.out.println(memo);
		}
	}
	
	@Test
	public void testQueryMethodWithPageable() {
		Pageable pageable = PageRequest.of(0,  10, Sort.by("mno").descending());
		
		Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);
		
		result.get().forEach(memo -> System.out.println(memo));

	}
	
	@Commit
	@Transactional
	@Test
	public void testDeleteQueryMethods() {
		memoRepository.deleteMemoByMnoLessThan(10L);
	}
	
	@Transactional
	@Test
	public void testSelectQuery() {
		List<Memo> list = memoRepository.getListDesc();
		
		for (Memo memo : list) {
			System.out.println(memo);
		}
	}
	
	
	
}
