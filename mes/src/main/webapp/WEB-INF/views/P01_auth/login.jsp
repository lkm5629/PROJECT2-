<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html lang="kr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="/mes/static/css/P00_common/common.css">

	<link rel="stylesheet" href="/mes/static/css/P00_layout/header.css">
	<script src="/mes/static/js/00_layout/header.js"></script>

	<link rel="stylesheet" href="/mes/static/css/P00_layout/snb.css">
	<script src="/mes/static/js/00_layout/snb.js"></script>
    
    <link rel="stylesheet" href="/mes/static/css/P01_auth/login.css">
</head>

<body>
    <img class="logo" src="static/images/logo.png" alt="MES 로고"> 
    <h1>알콜스왑 MES</h1>
    <h7>제조 실행 시스템</h7>
	<div class="model-body">
	<div class="model-high">
        <h2>로그인</h2>
		
	</div>
		<form method="post" action="/mes/login" id="login-form">
			<div class="center">
			      <div class="messege">
			      <c:if test="${ not empty error }">
			             <span class="messege" style="color: red;"> ${ error } </span>	
			             <br>	
			     </c:if>
			     
			      </div>
				<input type="text" class="input-1 radius" name="login_id" placeholder="사원번호를 입력해주세요."
				 required 
				 title="영문 시작, 1 ~ 10자 이내로 입력해주세요" 
				 pattern="[a-z][a-z0-9_]{0,10}">
				 <div class="id-m">
				 
			     </div>
				<input type="password" class="input-1 radius" name="login_pw" placeholder="비밀번호를 입력해주세요."
				 required
				 title="8 ~ 16자의 영문, 숫자, 특수문자를 모두 포함해야 합니다." 
				 pattern="(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&_])[A-Za-z\d@$!%*#?_&]{8,16}">
				 <div class="pw-m"></div>
				<button type="submit" class="buttonMain"  name="login_btn">로그인</button><br>
			</div>
		</form>
		
		<a href="/mes/changepw">비밀번호를 잊으셨나요? (비밀번호 변경)</a>
		<div></div>
	</div>
	<script>
	
	const loginForm = document.querySelector("#login-form");
	const id = document.querySelector("[name='login_id']");
	const pw = document.querySelector("[name='login_pw']");
	const idM = document.querySelector(".id-m");
	const pwM = document.querySelector(".pw-m");
	const messege = document.querySelector(".messege");
	
	
	// ID: 입력할 때마다 체크 (콘솔용)
	id.addEventListener('input', function() {
	    if(!id.checkValidity()){
	        console.log('ID 조건 미달');
	        idM.style.color = 'red';
	        idM.innerHTML = `
                형식에 맞춰주세요.	        
	        `;
	    } else {
	        console.log('ID 통과');
	        idM.innerHTML = `
                	        
	        `;
	    }
	});


	// pw: 입력창을 벗어날 때(blur) 경고창 띄우기
	pw.addEventListener('blur', function() {
	    if(!id.checkValidity() || !pw.checkValidity()){
	        //id.reportValidity(); // 여기서 말풍선이 뜹니다.
	        messege.style.color = 'red';
	        messege.innerHTML = `
                아이디 혹은 비밀번호가 형식과 일치하지 않습니다.	        
	        `;
	        setTimeout(function() {
	        	messege.innerHTML = `

	        	`
	        }, 1500);
	    } else {
	    	 messege.innerHTML = `
	                	        
		        `;
	    	
	    }
	});

	
	
	loginForm.addEventListener('submit', function(evt) {
		const isIdCheckValidity = id.checkValidity();
		const isPwCheckValidity = pw.checkValidity();
		
		if( !isIdCheckValidity || !isPwCheckValidity ) {
			
			// 값이 서버로 전송되는 거 막기
			evt.preventDefault();
			
			// 2. 사용자 피드백 (알림 또는 스타일 변경)
			 messege.innerHTML = `
	                아이디 혹은 비밀번호가 형식과 일치하지 않습니다.	        
		        `;
		        setTimeout(function() {
		        	messege.innerHTML = `

		        	`
		        }, 2000);
		        
		        //친절하게 입력창으로 포커스 이동
		        id.focus();
		        
			
			
			
		}
	})
	
	
	window.onload = function() {
   //메세지창 특정
    const serverMessage = document.querySelector(".messege");

    // 만약 서버에서 보낸 에러 메시지가 있다면
    if (serverMessage && serverMessage.innerText.trim() !== "") {
        // 2초 후 없애기
        setTimeout(function() {
            // 메시지 안보이게
            serverMessage.style.display = 'none';
        }, 2000); 
      }
   };
	</script>


</body>

</html>