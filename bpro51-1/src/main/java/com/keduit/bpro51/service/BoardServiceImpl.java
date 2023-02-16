package com.keduit.bpro51.service;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.keduit.bpro51.dto.BoardDTO;
import com.keduit.bpro51.dto.PageRequestDTO;
import com.keduit.bpro51.dto.PageResultDTO;
import com.keduit.bpro51.entity.Board;
import com.keduit.bpro51.entity.QBoard;
import com.keduit.bpro51.repository.BoardRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor	// 이거했을때
public class BoardServiceImpl implements BoardService {

	private final BoardRepository repository;	// final 넣어야함.
	
	@Override
	public Long register(BoardDTO dto) {
		log.info("DTO------------------");
		log.info(dto);
		
		Board entity = dtoToEntity(dto);
		log.info(entity);
		
		repository.save(entity);
		
		return entity.getBno();
	}

	@Override
	public PageResultDTO<BoardDTO, Board> getList(PageRequestDTO requestDTO) {

		Pageable pageable = requestDTO.getPageable(Sort.by("bno").descending());
		
		BooleanBuilder booleanBuilder = getSearch(requestDTO);
		
		Page<Board> result = repository.findAll(booleanBuilder, pageable);
		
		Function<Board, BoardDTO> fn = (entity -> entityToDto(entity));
		
		return new PageResultDTO<>(result, fn);
	}

	@Override
	public BoardDTO read(Long bno) {

		Optional<Board> result = repository.findById(bno);
		
		return result.isPresent() ? entityToDto(result.get()) : null;
	}

	@Override
	public void remove(Long bno) {
		repository.deleteById(bno);
		
	}

	@Override
	public void modify(BoardDTO dto) {
		Optional<Board> result = repository.findById(dto.getBno());
		
		if(result.isPresent()) {
			Board entity = result.get();
			
			entity.change(dto.getTitle(), dto.getContent());
			repository.save(entity);
		}
		
		
		
	}
	
	private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
		String type = requestDTO.getType();
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		QBoard qBoard = QBoard.board;
		String keyword = requestDTO.getKeyword();
		BooleanExpression expression = qBoard.bno.gt(0L);
		booleanBuilder.and(expression);
		
		if(type == null || type.trim().length() == 0) {
			return booleanBuilder;
		}
		
		BooleanBuilder conditionBuilder = new BooleanBuilder();
		
		if (type.contains("t")) {
			conditionBuilder.or(qBoard.title.contains(keyword));
		}
		
		if (type.contains("c")) {
			conditionBuilder.or(qBoard.content.contains(keyword));
		}
		
		if (type.contains("w")) {
			conditionBuilder.or(qBoard.writer.contains(keyword));
		}
		
		booleanBuilder.and(conditionBuilder);
		
		return booleanBuilder;
	}
	
	

}
