/**
addFriendForm.html 
 */
// 회원가입 입력창 검증하는 함수
// 유효하면 -> 서버로 보냄, 아니면 -> 여기서 머무름
function checkvalue(){
	let userId = document.getElementById("userId").value; // .value : 입력창 안의 값 
	let pw1 = document.getElementById("pw1").value;
	let pw2 = document.getElementById("pw2").value;
	let userName = document.getElementById("userName").value;
	let email = document.getElementById("email").value;
	
	// ID가 비어있거나 3~10자가 아니면 무효
	if(userId == '' || userId.length < 3 || userId.length > 10){
		alert("아이디는 3글자 이상 10글자 이하여야합니다.");
		document.getElementById("userId").focus(); // 이름 입력창 선택
		return false;
	}
	// 비밀번호가 비어있거나 4~8글자가 아니면 무효
	if(pw1 == '' || pw1.length < 4 || pw1.length > 8){
		alert("비밀번호는 4글자 이상 8글자 이하여야합니다.");
		document.getElementById("pw1").focus();
		return false;
	}
	// 비밀번호 입력창과 비밀번호 확인창의 값이 다르면 무효
	if(pw2 !== pw1) {
		alert("비밀번호가 일치하지 않습니다.");
		document.getElementById("pw2").focus();
		return false;
	}
	// 이름이 없거나 한글자거나 7글자 이상이면 무효
	if(userName == '' || userName.length < 2 || userName.length > 7){
		alert("이름은 2글자 이상 6글자 이하여야합니다.");
		document.getElementById("userName").focus();
		return false;
	}
	// 이름에 숫자 들어있으면 무효
	for (let i = 0; i < userName; i++) {
	if (!isNaN(userName.charAt(i))) {
    	alert("이름에 숫자가 들어갈 수 없습니다.");
    	return false;
	  	}
	} 
/*	let nameSplit = userName.split('');
	for (let i in nameSplit) {
		if (!isNaN(nameSplit[i])) {
	    	alert("이름에 숫자가 들어갈 수 없습니다.");
	    	return false;
	  	}
	} */
	
	// 이메일이 비어있으면 무효
	if(email == ''){
		alert("이메일을 입력해 주세요.");
		document.getElementById("email").focus();
		return false;
	}
	
	// 아이디 중복확인을 했는지?
	let checkIdMsg = document.getElementById("msg").innerText;
	
	// "중복확인 메시지에 '가능'이라는 키워드가 없다면"
	if (!checkIdMsg.includes("가능")){
		alert("유효하지 않은 ID입니다.");
		return false;
	} // 중복이 아닌 ID에 대해서는 '가능' 키워드 있음!
	
	return true;
}
	
	