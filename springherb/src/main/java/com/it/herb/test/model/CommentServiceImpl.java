package com.it.herb.test.model;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
	private final CommentDAO dao;
	
	@Override
	public int insertCmt(CommentVO vo) {
		return dao.insertCmt(vo);
	}

	@Override
	public List<CommentVO> listCmt() {
		return dao.listCmt();
	}
	

}
