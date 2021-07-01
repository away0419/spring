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

@Controller
@RequestMapping(value="/board/delete.do")
public class DeleteController {
	private static final Logger logger 
		= LoggerFactory.getLogger(DeleteController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String delete(@RequestParam(defaultValue = "0") int no, Model model) {
		//1
		logger.info("삭제 화면, 파라미터 no={}", no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된  url");
			model.addAttribute("url", "/board/list.do");
			
			return "common/message";
		}
		
		//2		
		//3
		return "board/delete";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String delete_post(@RequestParam int no, @RequestParam String pwd,
			Model model) {
		//1
		logger.info("삭제 처리, 파라미터 no={}, pwd={}", no, pwd);
		
		//2
		String msg="글 삭제 실패", url="/board/delete.do?no="+no;
		if(boardService.checkPwd(no, pwd)) {
			int cnt=boardService.deleteBoard(no);
			if(cnt>0) {
				msg="글 삭제되었습니다.";
				url="/board/list.do";
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
