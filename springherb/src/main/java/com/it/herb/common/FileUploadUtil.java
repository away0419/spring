package com.it.herb.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Component
public class FileUploadUtil {
	private static final Logger logger
		=LoggerFactory.getLogger(FileUploadUtil.class);
	
	
	public List<Map<String,Object>> fileUpload(HttpServletRequest request) throws IllegalStateException, IOException {
		MultipartHttpServletRequest multiRequest = 
				(MultipartHttpServletRequest) request;
		
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String, MultipartFile> filesMap = multiRequest.getFileMap();
		
		Iterator<String> iter = filesMap.keySet().iterator();
		while(iter.hasNext()) {
			String key = iter.next();
			MultipartFile tempFile=filesMap.get(key);
			//=> 업로드된 파일을 임시파일 형태로 제공
			
			if(!tempFile.isEmpty()) {
				long fileSize = tempFile.getSize();
				String originFileName = tempFile.getOriginalFilename();
				
				//변경된 파일 이름
				String fileName=getUniqueFileName(originFileName);
				
				
				//업로드 경로
				String uploadPath= getUploadPath(request);
				
				//업로드 처리하기 - 업로드 경로에 저장
				File file =new File(uploadPath, fileName);
				tempFile.transferTo(file);
				
				Map<String,Object> map = new HashMap<>();
				map.put("fileName", fileName);
				map.put("fileSize", fileSize);
				map.put("originalFilename",originFileName);
				
				list.add(map);
			}
		}
		return list;
	
	}
	
	public String getUploadPath(HttpServletRequest request) {
		String path="";
		if(ConstUtil.FILE_UPLOAD_PATH.equals("test")) {
			path=ConstUtil.FILE_UPLOAD_PATH_TEST;
		}else {
			path=ConstUtil.FILE_UPLOAD_PATH;
			//실제 물리적인 경로 구하기
			path = request.getSession().getServletContext().getRealPath(path);
		}
		
		logger.info("업로드 경로 : {}",path);
		
		return path;
	}
	
	public String getUniqueFileName(String fileName) {
		//업로드한 파일명이 중복될 경우 파일 이름 변경하기 - (현재시간(밀리초까지)추가)
		//a.txt => ab_20210630155820123.txt
		
		//순수 파일명만 구하기
		int idx=fileName.lastIndexOf(".");
		String fName= fileName.substring(0,idx); //ab
		
		//확장자 구하기
		String ext = fileName.substring(idx);
		
		String result =fName+"_"+getTimeStamp()+ext;
		logger.info("변경된 파일명:{}",result);

		return result;
	}
	
	public String getTimeStamp() {
		Date d = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyMMddHHmmssSSS");
		String str= sdf.format(d);
		
		return str;
	}
}
