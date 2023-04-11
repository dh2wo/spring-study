package com.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.boot.vo.Friend;

// @Mapper : 마이바티스 등 DB 프레임워크와 연동되는 인터페이스
@Mapper
public interface FriendDAO {
	
	// 마이바티스 매퍼에 쓴 id 값과 똑같이 이름 만들어야함
	// 마이바티스 매버에 쓴 resultType, parameterType 일치해야함
	public int insertFriend(Friend f);
	public List<Friend> selectAllFriend();
	public Friend selectOneFriend(String name);
	public int updateFriend(Friend f);
	public int deleteFriend(String name);
	public List<Friend> searchFriend(String name); //찾은 결과가 여러개 일 수도 있어서 List
	
}
