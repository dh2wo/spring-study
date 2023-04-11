package com.boot.service;

import java.util.List;

import com.boot.vo.Friend;

public interface FriendService {

	// 서비스에서 할 모든 메서드를 정의
	public int insertFriend(Friend f);
	public List<Friend> selectAllFriend();
	public Friend selectOneFriend(String name);
	public int updateFriend(Friend f);
	public int deleteFriend(String name);
	public List<Friend> searchFriend(String name);
}
