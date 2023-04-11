package com.abc.service;

import java.util.List;

import com.abc.vo.Board;

public interface BoardService {
	
	// selectAllBoard
	public List<Board> getBoardList();
	
	//selectOneBoard
	public Board read(int boardNum);
	
	// insertBoard
	public int write(Board b);
	
	// updateBoard
	public int update(Board b);
	
	// deleteBoard
	public int delete(int boardNum);
	
	// selectBoardByKeyword
	public List<Board> selectBoardByKeyword(String keyword, String category);
	
	// selectAllBoard_1
	public List<Board> selectAllBoard_1(String category, String keyword);
	
	// updateRecommend
	public int updateRecommend(int boardNum);
	
	// 
	public boolean recommend(String userId, int boardNum);

	
}
