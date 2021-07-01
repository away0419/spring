package com.it.herb.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.it.herb.board.model.BoardService;
import com.it.herb.board.model.BoardVO;
import com.it.herb.common.ConstUtil;
import com.it.herb.common.PaginationInfo;
import com.it.herb.common.SearchVO;

@Controller
public class ListController {
	private static final Logger logger
		=LoggerFactory.getLogger(ListController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/board/list.do")
	public String list(@ModelAttribute SearchVO searchVo, Model model) {
		//1
		logger.info("글 목록 페이지, 파라미터 searchVo={}", searchVo);
		
		//페이징 처리
		//[1] PaginationInfo 객체 생성
		PaginationInfo pagingInfo = new PaginationInfo();
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
		pagingInfo.setBlockSize(ConstUtil.BLOCK_SIZE);
		pagingInfo.setRecordCountPerPage(ConstUtil.RECORD_COUNT);
		
		//[2] SearchVo에 paging관련 변수값 셋팅
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		searchVo.setRecordCountPerPage(ConstUtil.RECORD_COUNT);
		logger.info("페이지번호 관련 셋팅 후 searchVo={}", searchVo);
		
		//2
		List<BoardVO> list=boardService.selectAll(searchVo);
		logger.info("글 전체 조회 결과, list.size={}", list.size());
		
		int totalRecord=boardService.selectTotalRecord(searchVo);
		logger.info("totalRecord="+totalRecord);
		pagingInfo.setTotalRecord(totalRecord);
		
		//3
		model.addAttribute("list", list);
		model.addAttribute("pagingInfo", pagingInfo);
		
		return "board/list";
	}
	
	
}
