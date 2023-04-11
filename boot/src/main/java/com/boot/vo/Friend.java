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
	
	// 생성자
//	public Friend() {} // 기본생성자
//	
//	public Friend(String name, int age, String phone, boolean isActive) {
//		this.name = name;
//		this.age = age;
//		this.phone = phone;
//		this.isActive = isActive;
//	}

	// 동작(=>메서드)
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public int getAge() {
//		return age;
//	}
//
//	public void setAge(int age) {
//		this.age = age;
//	}
//
//	public String getPhone() {
//		return phone;
//	}
//
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}
//
//	public boolean isActive() {
//		return isActive;
//	}
//
//	public void setActive(boolean isActive) {
//		this.isActive = isActive;
//	}
	
//	@Override
//	public String toString() {
//		return String.format("name : %s age : %d phone : %s isActive : %s",
//				this.name, this.age, this.phone, this.isActive);
//	}
	
}
