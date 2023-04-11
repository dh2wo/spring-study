/**
 addFriendForm, updateFriendForm.html 
 */

function validate(){
	// document.getElementById("ID") : 입력창 객체 그 자체
	// 입력창 안에 들어있는 값을 가지고 오고 싶을때는 객체 뒤에 .value 붙여야됨!!!
	let nameValue = document.getElementById("name").value;
	let ageValue = document.getElementById("age").value;
	let phoneValue = document.getElementById("phone").value;
	let adressValue = document.getElementById("adress").value;
	
	// 비어있는지 확인
	// == : 자료형에 상관없이 값만 같으면 됨('0' == 0 -> true)
	// === : 자료형과 값이 모두 일치해야됨('0' == 0 -> false)
	if (nameValue == ''){
		alert("이름은 필수항목입니다.");
		document.getElementById("name").focus(); // 이름 입력창 선택
		return false;
	}
	if (ageValue == '' || ageValue < 0 || ageValue > 100){
		document.getElementById("age").focus();
		alert("잘못된 나이입니다.");
		return false;
	}
	if (phoneValue == ''){
		document.getElementById("phone").focus();
		alert("전화번호는 필수항목입니다.");
		return false;
	}
	if (adressValue == ''){
		document.getElementById("adress").focus();
		alert("주소는 필수항목입니다..");
		return false;
	}
	// onsubmit이 호출하는 함수가 false를 반환한다면
	// 폼이 서버로 전송되지 않음
}