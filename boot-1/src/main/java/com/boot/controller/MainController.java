package com.boot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.boot.service.FriendService;
import com.boot.vo.Friend;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;

@Slf4j // (Simple Logging Facade for Java) Logback
@Controller
public class MainController {
	// 변수나 메서드이름 클래스 내부에서 한번에 수정하는 법 -> alt + shift +r

	// 의존성주입 (Dependency Injection)
	@Autowired // 개발자가 new로 객체를 만들지 않아도 스프링 컨테이너가 자동으로 객체 생성해줌
	private FriendService fService; // interface -> 생성자 생성X
	
	private final String REDIRECT_LIST = "redirect:/showFriendList"; // redirect: 뒤의 주소를 호출
																	 // HTML파일을 호출하는게 아니라
	
	@GetMapping("/")
	public String index() {
		return "index"; // templates/index.html
	}

	@GetMapping("/addFriend") // 화면 표시하기
	public String addFriend() {
		// html 파일이 templates의 내부 폴더에 존재할 때
		// templates 폴더 이후의 경로 작성
		return "form/addFriendForm"; // templates/form/index.html
	}

	@PostMapping("/addFriend") // 실제(submit을 눌렀을때) 실행
	public String addFriend(Friend f) {

		// 중괄호에 들어갈 값을 지정하지 않으면 그냥 friend : {}만 출력되니 조심
		log.debug("friend : {}", f);

		fService.insertFriend(f); // form에서 받아온 친구 정보 추가

		return REDIRECT_LIST;  
	}

	@GetMapping("/showFriendList")	
	public String showFriendList(Model model) {

		List<Friend> fList = fService.selectAllFriend();

		log.debug("fList size : {}", fList.size()); // List를 다 가져오기 보다는 size를 추천

		model.addAttribute("fList", fList);

		return "info/friendList";
	}

	@GetMapping("/showInfo")
	public String selectOneFriend(Model model, String name) {

		// 파라미터 순서에 따라서 중괄호 처음부터 파라미터의 값이 입력됨
		log.debug("입력된 이름 : {}", name);

		Friend f = fService.selectOneFriend(name);

		log.debug("returned friend : {}", f); // f.toString()

		// model은 실행될때 화면에 같이 표현(HTML)
		model.addAttribute("f", f); // "f" -> friendInfo.html

		return "info/friendInfo";
	}

	//쿼리스트링으로 정보(이름을 보냄)를 보내서 Get방식/    name=${} 
	@GetMapping("/updateFriend")
	public String showupdateFriend(Model model, String name) {

		log.debug("name : {}", name);

		Friend f = fService.selectOneFriend(name);

		log.debug("friend : {}", f);

		model.addAttribute("friend", f);

		return "form/updateFriendForm";
	}

	@PostMapping("/updateFriend")
	public String updateFriend(Friend f) {

		log.debug("Friend : {}", f);

		// service 호출
		fService.updateFriend(f);

		// return "FriendList"; -> 여기에서는 update를 했지 select를 하지 않음
		return REDIRECT_LIST;
	}

	@GetMapping("/deleteFriend") 
	public String deleteFriend(String name) {
		
		log.debug("Friend : {}", name);
		
		// service 호출 
		fService.deleteFriend(name);
		
		return REDIRECT_LIST;	
	}

	@GetMapping("/searchFriend") 
	public String searchFriend(Model model, String searchName) { // 템플릿에 보내줄 model
		log.debug("searchName : {}", searchName);
		
		// 서비스 호출
		List<Friend> fList = fService.searchFriend(searchName);
		
		log.debug("fList : {}", fList.size());
		
		model.addAttribute("fList", fList);
		
		return "info/friendList";
	}
}
