package com.keduit.bpro51.service;

import com.keduit.bpro51.dto.BoardDTO;
import com.keduit.bpro51.dto.PageRequestDTO;
import com.keduit.bpro51.dto.PageResultDTO;
import com.keduit.bpro51.entity.Board;

public interface BoardService {

	Long register(BoardDTO dto);
	
	PageResultDTO<BoardDTO, Board> getList(PageRequestDTO requestDTO);
	
	BoardDTO read(Long bno);
	
	void remove(Long bno);
	
	void modify(BoardDTO dto);
	
	default Board dtoToEntity(BoardDTO dto) {
		Board entity = Board.builder()
							.bno(dto.getBno())
							.title(dto.getTitle())
							.content(dto.getContent())
							.writer(dto.getWriter())
							.build();
		return entity; 
	}
	
	default BoardDTO entityToDto(Board entity) {
		
		BoardDTO dto = BoardDTO.builder()
							   .bno(entity.getBno())
							   .title(entity.getTitle())
							   .content(entity.getContent())
							   .writer(entity.getWriter())
							   .regDate(entity.getRegDate())
							   .updateDate(entity.getUpdateDate())
							   .build();
		
		return dto;
	}
	
}
