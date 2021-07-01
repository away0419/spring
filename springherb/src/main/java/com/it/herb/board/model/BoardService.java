package com.it.herb.board.model;

import java.util.List;

import com.it.herb.common.SearchVO;

public interface BoardService {
	public int insertBoard(BoardVO vo);
	public List<BoardVO> selectAll(SearchVO searchVo);
	int selectTotalRecord(SearchVO searchVo);
	public int updateReadCount(int no);
	public BoardVO selectByNo(int no);
	public boolean checkPwd(int no, String pwd);
	public int updateBoard(BoardVO vo);
	public int deleteBoard(int no);
	
	/*
	public List<BoardVO> selectMainNotice();
	*/
}
