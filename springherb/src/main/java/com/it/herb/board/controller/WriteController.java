package com.it.herb.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.it.herb.board.model.BoardService;
import com.it.herb.board.model.BoardVO;

@Controller
public class WriteController {
	private static final Logger logger 
		= LoggerFactory.getLogger(WriteController.class);
	
	
	private BoardService boardService;
	
	
	
	
	//@Autowired 안써도 생성자가 있으면 자동으로 넣어주는 스프링부트
	public WriteController(BoardService boardService) {
		this.boardService = boardService;
	}

	@RequestMapping(value="/board/write.do", method=RequestMethod.GET)
	public String write() {
		logger.info("글 쓰기 화면 보여주기");
		
		return "board/write";
	}
	
	@RequestMapping(value="/board/write.do", method =RequestMethod.POST)
	public String write_post(@ModelAttribute BoardVO vo, Model model) {
		//1
		logger.info("글등록 처리, 파라미터   vo={}", vo);
		
		//2
		String msg="", url="";
		int cnt=boardService.insertBoard(vo);		
		logger.info("글쓰기 결과, cnt={}", cnt);
		
		if(cnt>0) {
			msg="글쓰기 처리되었습니다.";
			url="/board/list.do";
		}else {
			msg="글쓰기 실패.";
			url="/board/write.do";
		}
		
		//3
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
}




