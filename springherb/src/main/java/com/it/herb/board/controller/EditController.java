package com.it.herb.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.it.herb.board.model.BoardService;
import com.it.herb.board.model.BoardVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class EditController {
	private static final Logger logger
		=LoggerFactory.getLogger(EditController.class);
	
	private final BoardService boardService;
	
	@RequestMapping(value="/edit.do", method=RequestMethod.GET)
	public String edit(@RequestParam(defaultValue = "0") int no, Model model) {
		//1		
		logger.info("수정화면 보기, 파라미터 no={}", no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된 url!");
			model.addAttribute("url", "/board/list.do");
			return "common/message";
		}
		
		//2
		BoardVO vo=boardService.selectByNo(no);
		logger.info("수정화면-조회,결과 vo={}", vo);
		
		//3
		model.addAttribute("vo", vo);
		
		return "board/edit";
	}
	
	@RequestMapping(value="/edit.do", method=RequestMethod.POST)
	public String edit_post(@ModelAttribute BoardVO vo, Model model) {
		//1
		logger.info("수정 처리, 파라미터 vo={}", vo);
		
		//2
		String msg="글 수정 실패", url="/board/edit.do?no="+vo.getNo();
		if(boardService.checkPwd(vo.getNo(), vo.getPwd())) {
			int cnt=boardService.updateBoard(vo);
			if(cnt>0) {
				msg="글 수정되었습니다.";
				url="/board/detail.do?no="+vo.getNo();
			}
		}else {
			msg="비밀번호가 일치하지 않습니다.";
		}
		
		//3
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
}
