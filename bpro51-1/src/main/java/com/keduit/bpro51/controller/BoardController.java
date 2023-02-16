package com.keduit.bpro51.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.keduit.bpro51.dto.BoardDTO;
import com.keduit.bpro51.dto.PageRequestDTO;
import com.keduit.bpro51.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService service;
	
	@GetMapping("/")
	public String index() {
		return "redirect:/board/list";
	}
	
	@GetMapping("/list")
	public void list(PageRequestDTO pageRequestDTO, Model model) {
		
		log.info("list..........", pageRequestDTO);
		model.addAttribute("result", service.getList(pageRequestDTO));
	}

	@GetMapping("/register")
	public void register() {
		log.info("register get..........");
	}
	
	@PostMapping("/register")
	public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes) {
		log.info("register dto....... " + dto);
		
		Long bno = service.register(dto);
		redirectAttributes.addFlashAttribute("msg", bno);
		
		return "redirect:/board/list";
	}
	
	@GetMapping({"/read", "/modify"})
	public void read(long bno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
		log.info("bno : " + bno);
		
		BoardDTO dto = service.read(bno);
		model.addAttribute("dto", dto);
	}
	
	@PostMapping("/remove")
	public String remove(long bno, RedirectAttributes redirectAttributes) {
		log.info("-----------remove bno : " + bno);
		
		service.remove(bno);
		redirectAttributes.addFlashAttribute("msg",	bno);
		
		return "redirect:/board/list";
		
	}
	
	@PostMapping("/modify")
	public String modify(BoardDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes) {
		
		log.info("post modify................ ");
		log.info("... dto : ", dto);
		
		service.modify(dto);
		
		redirectAttributes.addAttribute("page", requestDTO.getPage());
		redirectAttributes.addAttribute("type", requestDTO.getType());
		redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
		redirectAttributes.addAttribute("bno", dto.getBno());
		
		
		return "redirect:/board/read";
	}
	
}
