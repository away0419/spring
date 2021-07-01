package com.it.herb.reboard.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.it.herb.common.SearchVO;

@Mapper
public interface ReBoardDAO {
	public int insertReBoard(ReBoardVO vo);
	public List<ReBoardVO> selectAll(SearchVO searchVo);
	int selectTotalRecord(SearchVO searchVo);
	public int updateReadCount(int no);
	public ReBoardVO selectByNo(int no);
	public String selectPwd(int no);
	public int updateReBoard(ReBoardVO vo);
	public int deleteReBoard(int no);
	
	/*
	public List<ReBoardVO> selectMainNotice();
	*/
}
