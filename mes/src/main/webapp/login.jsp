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
		<form method="get" action="login">
			<div class="center">
				<input type="text" class="input-1 radius" name="login_id" placeholder="사원번호를 입력해주세요."><br>
				<input type="password" class="input-1 radius" name="login_pw" placeholder="비밀번호를 입력해주세요."><br>
				<button type="submit" class="buttonMain"  name="login_btn">로그인</button><br>
			</div>
		</form>
		<c:if test=" ${not empty error} ">
		<div style="color: red; ">${error}</div>
		</c:if>
		<div>http://localhost:8080/mes/login.jsp</div>
	</div>


</body>

</html>