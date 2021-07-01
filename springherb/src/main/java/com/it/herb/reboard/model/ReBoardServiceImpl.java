package com.it.herb.reboard.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.herb.common.SearchVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReBoardServiceImpl implements ReBoardService{
	private final ReBoardDAO reBoardDao;
	
	public int insertReBoard(ReBoardVO vo) {
		return reBoardDao.insertReBoard(vo);
	}

	@Override
	public List<ReBoardVO> selectAll(SearchVO searchVo) {
		return reBoardDao.selectAll(searchVo);
	}

	@Override
	public int selectTotalRecord(SearchVO searchVo) {
		return reBoardDao.selectTotalRecord(searchVo);
	}

	public int updateReadCount(int no){
		return reBoardDao.updateReadCount(no);
	}
	
	public ReBoardVO selectByNo(int no) {
		return reBoardDao.selectByNo(no);
	}

	@Override
	public boolean checkPwd(int no, String pwd) {
		String dbPwd=reBoardDao.selectPwd(no);
		
		boolean result=false;
		if(pwd.equals(dbPwd)) {
			result=true;
		}
		
		return result;
	}

	@Transactional
	public int updateReBoard(ReBoardVO vo){
		return reBoardDao.updateReBoard(vo);
	}
	
	public int deleteReBoard(int no){
		return reBoardDao.deleteReBoard(no);
	}
	
	/*
	public List<ReBoardVO> selectMainNotice() throws SQLException{
		return reBoardDao.selectMainNotice();
	}
	
	*/
}
