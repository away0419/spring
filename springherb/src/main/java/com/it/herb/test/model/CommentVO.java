package com.it.herb.test.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class CommentVO {
	private int commentNo; //카멜케이스로 바꿔야함 이 처리를 mabatis를 이용한 설정해야함
	private String userId;                       
	private Timestamp regDate;         
	private String commentContent;
}
