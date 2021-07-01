package com.it.herb.test.model;

import java.util.List;

public interface CommentService {
	int insertCmt(CommentVO vo);
	List<CommentVO> listCmt();
}
