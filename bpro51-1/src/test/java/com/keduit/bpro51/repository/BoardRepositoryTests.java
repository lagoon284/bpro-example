package com.keduit.bpro51.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.keduit.bpro51.entity.Board;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

	@Autowired
	BoardRepository boardRepository;
	
	@Test
	public void testClass() {
		System.out.println(boardRepository.getClass().getName());
	}
	
	@Test
	public void testInsertDummies() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Board board = Board.builder().content("sample content...... " + i).title("sample title..... " + i).writer("writer..... " + i).build();
			boardRepository.save(board);
		});
	}
	
	@Test
	public void testSelect() {
		
		Long bno = 100L;
		
		Optional<Board> result = boardRepository.findById(bno);
		
		System.out.println("---------------------");
		
		if(result.isPresent()) {
			Board board = result.get();
			System.out.println(board);
		}
	}
	
	@Test
	public void update() {
		Board board = Board.builder().bno(1L).title("update content...... ").content("update content...... ").writer("updater").build();
		System.out.println(boardRepository.save(board));
		
		// Long bno = 100L;
		// Optional<Board> result = boardRepository.findById(bno);
		// Board board = result.orElseThrow();
		// board.change("title...", "content...");
		// boardRepository.save(board);
	}
	
	@Test
	public void delete() {
		Long bno = 1L;
		
		boardRepository.deleteById(bno);
	}
	
	@Test
	public void testPaging() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
		Page<Board> result = boardRepository.findAll(pageable);
		
		log.info("total count : " + result.getTotalElements());
		log.info("total page : " + result.getTotalPages());
		log.info("page number : " + result.getNumber());
		log.info("page size : " + result.getSize());
		
		List<Board> board = result.getContent();
		board.forEach(boardli -> log.info(boardli));
	}
	
}
