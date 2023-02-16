package com.keduit.bpro51.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.keduit.bpro51.dto.BoardDTO;
import com.keduit.bpro51.dto.PageRequestDTO;
import com.keduit.bpro51.dto.PageResultDTO;
import com.keduit.bpro51.entity.Board;

@SpringBootTest
public class BoardServiceTests {
	
	@Autowired
	private BoardService service;
	
	@Test
	public void testRegister() {
		BoardDTO boardDTO = BoardDTO.builder().title("sample title.....").content("sample content.....").writer("user01").build();
		
		System.out.println(service.register(boardDTO));
	}
	
	@Test
	public void testList() {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
		
		PageResultDTO<BoardDTO, Board> resultDTO = service.getList(pageRequestDTO);
		
		System.out.println("prev : " + resultDTO.isPrev());
		System.out.println("next : " + resultDTO.isNext());
		System.out.println("total : " + resultDTO.getTotalPage());
		System.out.println("-------------------------------------");
		
		for(BoardDTO boardDTO : resultDTO.getDtoList()) {
			System.out.println(boardDTO);
		}
		System.out.println("================================");
		resultDTO.getPageList().forEach(i -> System.out.println(i));
	}
	
	
	@Test
	public void testSearch() {
		
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
													  .page(1)
													  .size(10)
													  .type("tc")
													  .keyword("asd")
													  .build();
		
		PageResultDTO<BoardDTO, Board> resultDTO = service.getList(pageRequestDTO);
		
		System.out.println("prev : " + resultDTO.isPrev());
		System.out.println("next : " + resultDTO.isNext());
		System.out.println("total : " + resultDTO.getTotalPage());
		
		System.out.println("--------------------------");
		for(BoardDTO dto : resultDTO.getDtoList()) {
			System.out.println("DTO : " + dto);
		}
		
		System.out.println("==========================");
		resultDTO.getPageList().forEach(i -> System.out.println(i));
	}
	
	
}
