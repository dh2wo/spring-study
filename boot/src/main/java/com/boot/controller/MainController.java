package com.boot.controller;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.boot.vo.Friend;

// Controller : 클라이언트에서 어떤 주소가 실행되었을 때 그 주소에 대한 기능을 정의한 곳
@Controller // 서버의 Controller 클래스라는 의미
public class MainController {

	// ()안의 주소에 대해서 실행할 메서드
	@GetMapping("/")
	public String index() {  
		return "index"; // index : templates 폴더 내부에 있는 HTML 파일의 이름
	}
	//	@GetMapping("/")
	//	public String index(String name, int age) {  // 주소 뒤에 ?key=value형식으로 된 문자 열 : Query String
	//		System.out.println("name : " + name); // Query String에 들어가는 Key의 이름 = 실행할 메서드의 파라미터 이름
	//		System.out.println("age : " + age); // http://localhost:8888/?name=abc&age=20
	//		return "index"; // index : templates 폴더 내부에 있는 HTML 파일의 이름

	@GetMapping("/display")
	public String display(Model model) {
		//Model 객체 : 클라이언트에 표시할 데이터들을 키와 값으로 저장하는 것

		String korean = "안녕하세요";
		String english= "Hello";

		// model이라는 쇼핑백 안에 korean이라는 상품이 들어있고,
		// 그 상품의 내용물이 "안녕하세요"라는 문자열이다.
		model.addAttribute("korean", korean); //(key, Value)
		model.addAttribute("english", english);

		// 내 이름
		model.addAttribute("name", "오승재");

		// 태그로 구성된 문자열
		String tag = "<b>We</b> are <i>the champions</i>";
		model.addAttribute("tag", tag);

		// null을 보내면 어떻게 될까?
		String nullData = null;
		model.addAttribute("nullData", nullData);

		// 비어있는 문자
		model.addAttribute("emptyData", "");

		// 숫자
		model.addAttribute("number", 1234);
		model.addAttribute("pi", 3.141);

		// 객체 담기
		Friend f1 = new Friend("손오공", 23, "010-123", true);
		Friend f2 = new Friend("저팔계", 25, "123-234", false);

		model.addAttribute("f1", f1);
		model.addAttribute("f2", f2);

		return "display";
	}

	@GetMapping("/loop")
	public String loop(Model model){

		// 제네릭 생략 : java 8이상에서 가능
		List<String> sList = new ArrayList<>();
		sList.add("apple");
		sList.add("meta");
		sList.add("google");

		model.addAttribute("strList", sList);

		// 1부터 10까지의 숫자를 리스트로 만들어서 클라이언트에 보내기
		List<Integer> iList = new ArrayList<>();

		for(int i = 1; i <= 10; i++) {
			iList.add(i);
		}

		model.addAttribute("iList", iList);

		List<Friend> fList = new ArrayList<>();
		fList.add(new Friend("AAA", 10, "111-111", true));
		fList.add(new Friend("BBB", 20, "222-222", false));
		fList.add(new Friend("CCC", 30, "333-333", true));
		fList.add(new Friend("DDD", 40, "444-444", false));

		model.addAttribute("fList", fList);

		// Java Collection Framework => List, Map, Set
		Map<String, Friend> fMap = new HashMap<>();
		fMap.put("eric", new Friend("Eric", 10, "999-999", true)); //(key, Value)
		fMap.put("stan", new Friend("Stan", 9, "1313-123", true));
		fMap.put("kenny", new Friend("Kenny", 8 ,"123-123", true));

		model.addAttribute("fMap", fMap);

		return "loop";
	}

	@GetMapping("/friendList")
	public String friendList(Model model) {
		List<Friend> fList = new ArrayList<>();

		fList.add(new Friend("AAA", 10, "111-111", true));
		fList.add(new Friend("BBB", 20, "222-222", false));
		fList.add(new Friend("CCC", 30, "333-333", true));

		model.addAttribute("fList", fList);

		return "friendList";
	}

	//
	@GetMapping("/sendData")
	public String sendData(Model model) {
		List<Friend> fList = new ArrayList<>();

		fList.add(new Friend("AAA", 10, "111-111", true));
		fList.add(new Friend("BBB", 20, "222-222", false));
		fList.add(new Friend("CCC", 30, "333-333", true));

		model.addAttribute("fList", fList);

		return "dataView";
	}

	@GetMapping("/sendOne")
	public String sendOne(String name, int age) {
		System.out.printf("name : %s, age : %d\n", name, age);
		return "dataView";
	}

	@GetMapping("/sendFormData")
	public String showForm() {
		return "formView";
	}

	@GetMapping("/sendForm")
	public String getFormData(String name, int age) {
		System.out.printf("name : %s, age : %d\n", name, age);
		return "formView";
	}

	// 메서드 오버로딩(메서드 다중정의) : 하나의 클래스 안에서 같은 이름의 메서드를 여러개 만들 수 있음
	// 주의 : 매개변수 타입 or 개수 or 순서가 달라야함 / 리턴타입 상관없음
	@PostMapping("/sendForm")
	public String getFormData(String name, String age) {
		System.out.println(name + " " + age);
		return "formView";
	}

	// 클라이언트에 전송방식과 서버의 전송방식은 동일해야 한다 (GET or POST)
	// 그렇지 않으면 405 에러 발생
	@GetMapping("/sendObject")
	public String getObject(String name, int age, String phone, boolean isActive) {
		System.out.println(name);
		System.out.println(age);
		System.out.println(phone);
		System.out.println(isActive);

		return "formView";
	}

	@GetMapping("/sendObject2")
	public String getObject(Friend f) { 
		// 사용자 정의 객체로 파라미터를 사용할 수 있는 이유
		// 스프링이 HTML의 name 속성의 값을 보고 setter를 실행하기 때문
		System.out.println(f);

		return "formView";
	}
	
	@PostMapping("/loginTest")
	public String loginTest(String userId, String pw) {
		System.out.println(userId + " " + pw);
		return "formView";
	}
	
	
}

