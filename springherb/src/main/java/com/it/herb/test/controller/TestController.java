package com.it.herb.test.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.it.herb.test.model.CommentService;
import com.it.herb.test.model.CommentVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController {
	private static final Logger logger
		=LoggerFactory.getLogger(TestController.class);
	
	private final CommentService service;
	
	@GetMapping("/test/write")
	public String write() {
		logger.info("comment2 화면");
		
		return "test/write";
	}
	
	@PostMapping("/test/write")
	public String write_post(@ModelAttribute CommentVO vo) {
		logger.info("comment2 등록, 파라미터 vo = {}", vo);
		
		int cnt = service.insertCmt(vo);
		logger.info("등록결과, cnt={}",cnt);
		
		return "redirect:/test/list";
	}
	
	@GetMapping("/test/list")
	public String list(Model model) {
		
		List<CommentVO> list = service.listCmt();
		
		model.addAttribute("list",list);
		
		return "test/list";
	}
}
