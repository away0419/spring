package com.it.herb.board.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.it.herb.common.SearchVO;

@Mapper
public interface BoardDAO {
	public int insertBoard(BoardVO vo);
	public List<BoardVO> selectAll(SearchVO searchVo);
	int selectTotalRecord(SearchVO searchVo);
	public int updateReadCount(int no);
	public BoardVO selectByNo(int no);
	public String selectPwd(int no);
	public int updateBoard(BoardVO vo);
	public int deleteBoard(int no);
	
	/*
	public List<BoardVO> selectMainNotice();
	*/
}
