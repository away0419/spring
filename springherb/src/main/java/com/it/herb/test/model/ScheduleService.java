package com.it.herb.test.model;

import java.util.List;

public interface ScheduleService {
	
	int insertSchedule(ScheduleVO vo);
	List<ScheduleVO> listSchedule();
}
