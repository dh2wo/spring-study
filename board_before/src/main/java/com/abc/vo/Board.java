package com.abc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	
	private int boardNum;
	private String title;
	private String userId;
	private String content;
	private String inputDate;
	private int viewCount;
	private int recommend;
	// 파일 관련은 나중에 추가할 것임
	private String originalFile;
	private String savedFile;
	
}
