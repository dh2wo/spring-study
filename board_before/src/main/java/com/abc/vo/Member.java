package com.abc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // @Getter, @Setter, @toString 들어있음!
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	
	private String userId;
	private String userPw;
	private String userName;
	private String email;
	
}
