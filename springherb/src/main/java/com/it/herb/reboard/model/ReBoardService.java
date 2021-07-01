package com.it.herb.reboard.model;

import java.util.List;

import com.it.herb.common.SearchVO;

public interface ReBoardService {
	public int insertReBoard(ReBoardVO vo);
	public List<ReBoardVO> selectAll(SearchVO searchVo);
	int selectTotalRecord(SearchVO searchVo);
	public int updateReadCount(int no);
	public ReBoardVO selectByNo(int no);
	public boolean checkPwd(int no, String pwd);
	public int updateReBoard(ReBoardVO vo);
	public int deleteReBoard(int no);
	
	/*
	public List<ReBoardVO> selectMainNotice();
	*/
}
