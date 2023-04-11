package com.abc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.dao.BoardDAO;
import com.abc.vo.Board;

@Service // 이 어노테이션을 달아야 컨트롤러에서 Autowired 가능
// (스프링이 구성요소라고 인식)
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardDAO bDao;

	@Override
	public List<Board> getBoardList() {
		return bDao.selectAllBoard();
	}

	@Override
	public Board read(int boardNum) {
		bDao.addViewCount(boardNum); // 조회수 늘리기
		return bDao.selectOneBoard(boardNum);
	}

	@Override
	public int write(Board b) {
		return bDao.insertBoard(b);
	}

	@Override
	public int update(Board b) {
		return bDao.updateBoard(b);
	}

	@Override
	public int delete(int boardNum) {
		return bDao.deleteBoard(boardNum);
	}

	@Override
	public List<Board> selectBoardByKeyword(String keyword, String category) {
		List<Board> result = null;

		//category에 따라서 DAO의 각각 다른 메서드 호출
		if (category.equals("title")){
			result = bDao.selectBoardByKeyword(keyword);

		} else if (category.equals("content")) {
			result = bDao.selectBoardByContent(keyword);

		} else if (category.equals("user")) {
			result = bDao.selectBoardByUser(keyword);
		}

		return result;
	}

	@Override
	public List<Board> selectAllBoard_1(String category, String keyword) {

		Map<String, Object> map = new HashMap<String, Object>();

		// 맵에 넣는 key값은 마이바티스가 파라미터로 사욜할 이름
		// value는 실제 값
		map.put("category", category);
		map.put("keyword", keyword);

		// map : 마이바티스가 파라미터로 사용할 값들의 모음
		return bDao.selectAllBoard_1(map);
	}

	@Override
	public int updateRecommend(int boardNum) {

		bDao.updateRecommend(boardNum);

		Board b = bDao.selectOneBoard(boardNum);
		// 내가 돌려줄 숫자는... 해당하는 글의 추천수이다.

		return b.getRecommend();
	}

	@Override
	public boolean recommend(String userId, int boardNum) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("userId", userId);
		map.put("boardNum", boardNum);
		
		// 글 번호와 사용자 ID 넘겨서 추천한 적이 있는지 확인
		int checkResult = bDao.checkRecommendHistory(map);

		// 추천한적 없음
		if (checkResult == 0) {
			bDao.insertRecommendHistory(map);
			return true;
		} 
			return false;
	}
}