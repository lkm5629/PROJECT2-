<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html lang="kr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="static/css/login.css">
</head>

<body>
    <img class="logo" src="static/images/logo.png" alt="MES 로고"> 
    <h1>알콜스왑 MES</h1>
    <h7>제조 실행 시스템</h7>
	<div class="model-body">
	<div class="model-high">
        <h2>로그인</h2>
		<span class="close-btn" >&times;</span>
	</div>
		<form>
			<div class="center">
				<input type="text" class="input-1 radius" name="login_id" placeholder="아이디를 입력해주세요."><br>
				<input type="password" class="input-1 radius" name="login_pw" placeholder="비밀번호를 입력해주세요."><br>
				<input type="submit" class="btn-1 radius" placeholder="로그인" name="login_btn"><br>
			</div>
		</form>
		<a href="join.jsp">아직 회원이 아니신가요? (회원가입)</a>
	</div>


</body>

</html>