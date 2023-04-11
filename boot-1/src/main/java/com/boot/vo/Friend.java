package com.boot.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 롬복(라이브러리) 사용하기 => Package - Spring - Add
@Data 				// @Getter, @Setter, @ToString, @Equals, @hashCode 한번에 생성
@NoArgsConstructor  // 매개변수가 없는 생성자(=기본생성자)
@AllArgsConstructor // 필드를 모두 초기화하는 생성자
@Getter				// 모든 필드의 Getter
@Setter				// 모든 필드의 Setter
@ToString			// toString 메서드 오버라이딩
public class Friend {
	// 속성(=>멤버변수, 필드)
	private String name; 	  // 이름
	private int age;          // 나이
	private String phone; 	  // 전화번호
	private boolean isActive; // 접속중?
	private String adress; 	  // 주소
	private String category;  // 분류
	
}
