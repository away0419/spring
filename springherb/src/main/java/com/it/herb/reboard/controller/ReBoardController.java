package com.it.herb.reboard.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.it.herb.common.ConstUtil;
import com.it.herb.common.FileUploadUtil;
import com.it.herb.common.PaginationInfo;
import com.it.herb.common.SearchVO;
import com.it.herb.reboard.model.ReBoardService;
import com.it.herb.reboard.model.ReBoardVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reBoard")
@RequiredArgsConstructor
public class ReBoardController {
	private static final Logger logger
	=LoggerFactory.getLogger(ReBoardController.class);
	
	private final ReBoardService reBoardService;
	private final FileUploadUtil fileUploadUtil;
	
	@RequestMapping(value="/write.do", method=RequestMethod.GET)
	public String write() {
		logger.info("글 쓰기 화면 보여주기");
		
		return "reBoard/write";
	}
	
	@RequestMapping(value="/write.do", method =RequestMethod.POST)
	public String write_post(@ModelAttribute ReBoardVO vo,
			HttpServletRequest request ,Model model) {
		//1
		logger.info("글등록 처리, 파라미터   vo={}", vo);
		
		//2
		//파일 업로드 처리
		String fileName="", originalFileName="";
		long fileSize=0;
		
		try {
			List<Map<String, Object>> list = fileUploadUtil.fileUpload(request);
			for(int i=0; i<list.size(); i++) {
				Map<String, Object> map =list.get(i);
				fileName=(String) map.get("fileName");
				originalFileName=(String) map.get("originalFileName");
				fileSize=(Long) map.get("fileSize");
			}
		
			logger.info("파일 업로드 성공, fileName={}, fileSize={}",
					fileName, fileSize);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		vo.setFileName(fileName);
		vo.setFileSize(fileSize);
		vo.setOriginalFileName(originalFileName);
		
		String msg="", url="";
		int cnt=reBoardService.insertReBoard(vo);		
		logger.info("글쓰기 결과, cnt={}", cnt);
		
		if(cnt>0) {
			msg="글쓰기 처리되었습니다.";
			url="/reBoard/list.do";
		}else {
			msg="글쓰기 실패.";
			url="/reBoard/write.do";
		}
		
		//3
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping("/list.do")
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
		List<ReBoardVO> list=reBoardService.selectAll(searchVo);
		logger.info("글 전체 조회 결과, list.size={}", list.size());
		
		int totalRecord=reBoardService.selectTotalRecord(searchVo);
		logger.info("totalRecord="+totalRecord);
		pagingInfo.setTotalRecord(totalRecord);
		
		//3
		model.addAttribute("list", list);
		model.addAttribute("pagingInfo", pagingInfo);
		
		return "reBoard/list";
	}
	
	@RequestMapping("/countUpdate.do")
	public String countUpdate(@RequestParam(defaultValue = "0") int no,
			Model model) {
		//1
		logger.info("조회수 증가 페이지, 파라미터 no={}", no);
		if(no==0) {
			model.addAttribute("msg", "잘못된 url입니다.");
			model.addAttribute("url", "/reBoard/list.do");
			
			return "common/message";
		}
		
		//2
		int cnt=reBoardService.updateReadCount(no);
		logger.info("조회수 증가 결과, cnt={}", cnt);
		
		//3
		return "redirect:/reBoard/detail.do?no="+no;
	}
	
	@RequestMapping("/detail.do")
	public String detail(@RequestParam(defaultValue = "0") int no, Model model) {
		//1
		logger.info("상세보기, 파라미터 no={}", no);
		if(no==0) {
			model.addAttribute("msg", "잘못된 url!");
			model.addAttribute("url", "/reBoard/list.do");
			
			return "common/message";
		}
		
		//2
		ReBoardVO vo=reBoardService.selectByNo(no);
		logger.info("상세보기 결과, vo={}", vo);
		
		//3
		model.addAttribute("vo", vo);
		
		return "reBoard/detail";
	}
		
	@RequestMapping(value="/edit.do", method=RequestMethod.GET)
	public String edit(@RequestParam(defaultValue = "0") int no, Model model) {
		//1		
		logger.info("수정화면 보기, 파라미터 no={}", no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된 url!");
			model.addAttribute("url", "/reBoard/list.do");
			return "common/message";
		}
		
		//2
		ReBoardVO vo=reBoardService.selectByNo(no);
		logger.info("수정화면-조회,결과 vo={}", vo);
		
		//3
		model.addAttribute("vo", vo);
		
		return "reBoard/edit";
	}
	
	@RequestMapping(value="/edit.do", method=RequestMethod.POST)
	public String edit_post(@ModelAttribute ReBoardVO vo, Model model) {
		//1
		logger.info("수정 처리, 파라미터 vo={}", vo);
		
		//2
		String msg="글 수정 실패", url="/reBoard/edit.do?no="+vo.getNo();
		if(reBoardService.checkPwd(vo.getNo(), vo.getPwd())) {
			int cnt=reBoardService.updateReBoard(vo);
			if(cnt>0) {
				msg="글 수정되었습니다.";
				url="/reBoard/detail.do?no="+vo.getNo();
			}
		}else {
			msg="비밀번호가 일치하지 않습니다.";
		}
		
		//3
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping(value="/delete.do", method = RequestMethod.GET)
	public String delete(@RequestParam(defaultValue = "0") int no, Model model) {
		//1
		logger.info("삭제 화면, 파라미터 no={}", no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된  url");
			model.addAttribute("url", "/reBoard/list.do");
			
			return "common/message";
		}
		
		//2		
		//3
		return "reBoard/delete";
	}
	
	@RequestMapping(value="/delete.do",method=RequestMethod.POST)
	public String delete_post(@RequestParam int no, @RequestParam String pwd,
			Model model) {
		//1
		logger.info("삭제 처리, 파라미터 no={}, pwd={}", no, pwd);
		
		//2
		String msg="글 삭제 실패", url="/reBoard/delete.do?no="+no;
		if(reBoardService.checkPwd(no, pwd)) {
			int cnt=reBoardService.deleteReBoard(no);
			if(cnt>0) {
				msg="글 삭제되었습니다.";
				url="/reBoard/list.do";
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
