<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html lang="kr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="static/css/P00_common/common.css">
    <link rel="stylesheet" href="static/css/join.css">
</head>

<body>
    <img class="logo" src="static/images/logo.png" alt="MES 로고"> 
    <h1>알콜스왑 MES</h1>
    <h7>제조 실행 시스템</h7>
	<div class="model-body">
	<div class="model-high">
        <h2>회원가입</h2>
		<span class="close-btn" >&times;</span>
	</div>
		<form method="get" action="/">
			<div class="center">
				<input type="text" class="input-1 radius" name="join_name" placeholder="이름"><br>
				<input type="text" class="input-1 radius" name="join_email" placeholder="이메일"><br>
				<div class="chain">
				<input type="text" class="input-2 radius" name="join_dept" placeholder="부서"><br>
				<input type="text" class="input-2 radius" name="join_job" placeholder="직책"><br>
				<input type="text" class="input-2 radius" name="join_license" placeholder="자격증"><br>
				</div>
				<br>
				
				<input type="text" class="input-1 radius" name="join_p_no" placeholder="연락처"><br>
				<input type="password" class="input-1 radius" name="join_pw" placeholder="비밀번호를 입력해주세요."><br>
				<input type="password" class="input-1 radius" name="join_pw2" placeholder="비밀번호 확인"><br>
				<button type="submit" class="buttonMain"  name="login_btn">회원가입</button><br>
			</div>
		</form>
		<a href="login.jsp">이미 회원이신가요? (로그인)</a>
	</div>


</body>

</html>
