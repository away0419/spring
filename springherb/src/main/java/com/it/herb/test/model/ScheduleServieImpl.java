package com.it.herb.test.model;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServieImpl implements ScheduleService{
	
	private final  ScheduleDAO dao;
	
	@Override
	public int insertSchedule(ScheduleVO vo) {
		return dao.insertSchedule(vo);
	}

	@Override
	public List<ScheduleVO> listSchedule() {
		return dao.listSchedule();
	}
	
}
