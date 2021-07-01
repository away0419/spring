package com.it.herb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	private static final Logger logger
		=LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping("/pd/write")
	public String write() {
		logger.info("write 화면!");
		
		return "pd/write";
	}
	
	
}
