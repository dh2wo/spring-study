package com.abc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.dao.MemberDAO;
import com.abc.vo.Member;

@Service // 이 어노테이션을 달아야 컨트롤러에서 Autowired 가능
		 // (스프링이 구성요소라고 인식)
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDAO mDao;
	
	@Override
	public int joinMember(Member m) {
		return mDao.insertMember(m);
	}
	
	@Override
	public Member findOneMember(String userId) {
		return mDao.selectOneMember(userId);
	}

	@Override
	public boolean login(Member m) {
		
		Member result = mDao.login(m);
		
		if (result == null || result.getUserId().equals("")) {
			// 결과가 없거나 결과에 아무런 내용이 없을 경우
			return false;
		}
		return true; // result가 null이 아니거나 userId가 무언가 들어있을 경우
	}

	@Override
	public void updateMember(Member m) {
		mDao.updateMember(m);
	}

	@Override
	public int deleteMember(String id, String pw) {
		
		Member m = new Member();
		
		// 객체 생성 후 컨트롤러에서 전해진 값을 설정
		m.setUserId(id);
		m.setUserPw(pw);
		
		return mDao.deleteMember(m);
	}

}
