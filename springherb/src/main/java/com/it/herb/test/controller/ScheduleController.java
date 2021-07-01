package com.it.herb.test.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.it.herb.test.model.ScheduleService;
import com.it.herb.test.model.ScheduleVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ScheduleController {
	private final ScheduleService service;
	private static final Logger logger
		=LoggerFactory.getLogger(TestController.class);

	@PostMapping("/insertSchedule")
	@ResponseBody
	public void insertSchedule(@RequestBody ScheduleVO scheduleVO) {
		logger.info("cal 등록, 파라미터 vo = {}", scheduleVO);
		service.insertSchedule(scheduleVO);
		
	}
	
	@GetMapping("/")
	public String Schedule(){
		
		return "/index";
	}
	
	@GetMapping("/listSchedule")
	@ResponseBody
	public List<ScheduleVO> listSchedule(){
		List<ScheduleVO> list = service.listSchedule();
		logger.info("cal 리스트, 파라미터 vo = {}", list.get(1));
		
		return list;
	}
}
