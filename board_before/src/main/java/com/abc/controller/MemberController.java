package com.abc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abc.service.MemberService;
import com.abc.vo.Member;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;

@Slf4j
@Controller 
public class MemberController {
	
	@Autowired
	// 인터페이스(MemeberServie)는 자료형으로 사용 가능 
	// (거기에 들어가는 객체는 그 인터페이스를 구현한 실제 클래스) new ArrayList
	private MemberService mService; 
	
	// 초기화면으로 돌아가기 위환 리다이렉트 주소
	private final String REDIRECT_INDEX = "redirect:/";
	
	// 회원 가입하기(화면 띄우기)
	@GetMapping("/join")
	public String join() {
		log.debug("join() 실행됨");
		return "member/joinForm";
	}
	
	// 회원 가입하기(회원정보 저장 실행)
	@PostMapping("/join")
	public String join(Member member) {
		log.debug("join(Memeber) 실행됨");
		log.debug("Member : {}", member);
		
		// Service 호출하기
		mService.joinMember(member);
		
		return REDIRECT_INDEX;
	}
	
	// 한 명의 회원 정보 가져오기
	
	// 로그인(화면 띄우기)
	@GetMapping("/login")
	public String login() {
		log.debug("login()");
		return "member/loginForm";
	}
	
	// 로그인(실제 로그인 실행)
	@PostMapping("/login")
	public String login(Member member, HttpSession session) { // HttpSession 정보를 담기 위해
		log.debug("login(Member)");
		log.debug("Memeber : {}", member);
		
		// Service 호출하기
		boolean result = mService.login(member);
		
		// login이라는 메서드 실행 결과가 참이라면
		if(result) {
			// session에 userId를 담아놓자
			session.setAttribute("login", member.getUserId());
			return REDIRECT_INDEX; // index 페이지로 이동
		} else { // 거짓이라면
			return "member/loginForm"; // 다시 로그인 화면 띄우기
		}
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// 해당하는 키에 들어있던 값을 지워버린다
		session.removeAttribute("login");
		return REDIRECT_INDEX;
	}
	
	// 회원정보 수정화면 띄우기
	@GetMapping("/updateMember")
	public String updateMember(HttpSession session, Model model) {
		
		String userId = (String) session.getAttribute("login");
		
		log.debug("userId : {}" , userId);
		
		Member member = mService.findOneMember(userId);
		
		log.debug("Member : {}", member);
		
		// 클라이언트에 멤버 객체 전달
		model.addAttribute("member", member);
		
		return "member/updateForm";
	}
	
	// 회원정보 수정
	@PostMapping("/updateMember") // 폼이 호출하는 주소
	public String updateMember(Member m) {
		
		log.debug("Member : {}", m);
		
		mService.updateMember(m);
		
		return REDIRECT_INDEX;
	}
	
	// 회원삭제 화면 띄우기
	@GetMapping("/deleteMember")
	public String deleteMember() {
		
		return "member/deleteMember";
	}
	
	// 회원삭제
	@PostMapping("/deleteMember")
	public String deleteMember(HttpSession session, String userPw, Model model) {
		
		String userId = (String) session.getAttribute("login");
		
		int result = mService.deleteMember(userId, userPw);
		
		if (result != 0 ) { // affected row(영향을 받은 행이 1개 이상)
			return "redirect:/logout"; // 세션정보 지우기
		} else { // result == 0
			model.addAttribute("msg", "비밀번호가 틀렸습니다.");
			
			return "member/deleteMember";
		}
	}
	
	// 아이디 중복확인 하는 로직
	@PostMapping("/checkId")
	@ResponseBody // 일부만 갱신
	public String checkId(String id) {
		log.debug("id : {}", id);
		
		Member m = mService.findOneMember(id);
		
		log.debug("m : {}", m);
		
		//DB에서 id에 대한 검색 결과가 없어야지 OK 아니면 NG
		if (m == null) {
			return "OK";
		} else { // 사용 불가능한 id
			return "NG";
		}
	}
	
}
