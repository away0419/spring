package com.it.herb.test.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScheduleDAO {
	int insertSchedule(ScheduleVO vo);
	List<ScheduleVO> listSchedule();
}
