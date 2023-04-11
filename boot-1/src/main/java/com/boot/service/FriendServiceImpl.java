package com.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.dao.FriendDAO;
import com.boot.vo.Friend;

// Service 구현 클래스에서 @Service을 꼭! 붙여야 한다
// 그래야 스프링부트에서 서비스 클래스라고 인식한다
@Service
public class FriendServiceImpl implements FriendService{
	
	// 스프링한테 DAO 객체를 주입받고 싶을때
	@Autowired
	private FriendDAO fDao;

	@Override
	public int insertFriend(Friend f) {
		return fDao.insertFriend(f);
	}

	@Override
	public List<Friend> selectAllFriend() {
		return fDao.selectAllFriend();
	}

	@Override
	public Friend selectOneFriend(String name) {
		return fDao.selectOneFriend(name);
	}

	@Override
	public int updateFriend(Friend f) {
		return fDao.updateFriend(f);
	}

	@Override
	public int deleteFriend(String name) {
		return fDao.deleteFriend(name);
	}

	@Override
	public List<Friend> searchFriend(String name) {
		return fDao.searchFriend(name);
	}
	
	

}
