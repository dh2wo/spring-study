package com.abc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.abc.vo.Board;

// 글 정보 관련 DAO(Mapper 파일에 써놓은 쿼리id하고 파라미터, 리턴타입 맞춰서쓰기)
@Mapper
public interface BoardDAO {
	
	public List<Board> selectAllBoard();
	public Board selectOneBoard(int boardNum);
	public int insertBoard(Board b);
	public int updateBoard(Board b);
	public int deleteBoard(int boardNum);
	public int addViewCount(int boardNum);
	public List<Board> selectBoardByKeyword(String keyword);
	public List<Board> selectBoardByContent(String keyword);
	public List<Board> selectBoardByUser(String keyword);
	
	public List<Board> selectAllBoard_1(Map<String, Object> map);
	
	public int updateRecommend(int boardNum);
	
	public int checkRecommendHistory(Map<String, Object> map);
	public int insertRecommendHistory(Map<String, Object> map);
	
}
