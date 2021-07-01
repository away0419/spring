package com.it.herb.reboard.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReBoardVO {
	private int no;
	private String name;	
	private String pwd;
	private String title;
	private String email;
	private Timestamp regdate;
	private int readcount;
	private String content;
	
	private int groupNo;               
	private int step;       
	private int sortNo;         
	private String delFlag;         
	private String fileName;                        
	private long fileSize;    
	private int downCount;    
	private String originalFileName;   
	
}
