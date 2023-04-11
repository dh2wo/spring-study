package com.abc.dao;

import org.apache.ibatis.annotations.Mapper;
import com.abc.vo.Member;

// 회원정보 관련 DAO(Mapper 파일에 써놓은 쿼리id하고 파라미터, 리턴타입 맞춰서쓰기)
@Mapper
public interface MemberDAO {
	
	public int insertMember(Member m);
	public Member selectOneMember(String userId); // (userId) = Mapper의 #{uesrId}
	public Member login(Member m);
	public int updateMember(Member m);
	public int deleteMember(Member m);
}
