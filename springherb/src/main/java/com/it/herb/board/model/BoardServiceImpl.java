package com.it.herb.board.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.herb.common.SearchVO;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDAO boardDao;
	
	public int insertBoard(BoardVO vo) {
		return boardDao.insertBoard(vo);
	}

	@Override
	public List<BoardVO> selectAll(SearchVO searchVo) {
		return boardDao.selectAll(searchVo);
	}

	@Override
	public int selectTotalRecord(SearchVO searchVo) {
		return boardDao.selectTotalRecord(searchVo);
	}

	public int updateReadCount(int no){
		return boardDao.updateReadCount(no);
	}
	
	public BoardVO selectByNo(int no) {
		return boardDao.selectByNo(no);
	}

	@Override
	public boolean checkPwd(int no, String pwd) {
		String dbPwd=boardDao.selectPwd(no);
		
		boolean result=false;
		if(pwd.equals(dbPwd)) {
			result=true;
		}
		
		return result;
	}

	@Transactional
	public int updateBoard(BoardVO vo){
		return boardDao.updateBoard(vo);
	}
	
	public int deleteBoard(int no){
		return boardDao.deleteBoard(no);
	}
	
	/*
	public List<BoardVO> selectMainNotice() throws SQLException{
		return boardDao.selectMainNotice();
	}
	
	*/
}
