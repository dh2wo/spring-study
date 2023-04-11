package com.abc.service;

import com.abc.vo.Member;

public interface MemberService {
	
	// 주석에 있는 이름은 DAO(Mapper의 쿼리 id)의 메서드 이름
	// insertMember
	public int joinMember(Member m);
	
	// selectOneMemeber
	public Member findOneMember(String userId);
	
	// login
	public boolean login(Member m);
	
	// updateMember
	public void updateMember(Member m); // 받아서 딱히 할게 없으면 void
	
	// deleteMember
	public int deleteMember(String id, String pw);
}
