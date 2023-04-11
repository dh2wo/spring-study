package com.abc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 주소 들어갈것 이기 때문에
public class MainController{
	
	// 메인 화면 띄우기
	@GetMapping({"","/"}) // localhost:8888"" enter or localhost:8888"/" enter
	public String index() {
		return "index";
	}

}
