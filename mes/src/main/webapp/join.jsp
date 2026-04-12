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
<link rel="stylesheet" href="static/css/join.css">
</head>

<body>


	<%@ include file="/WEB-INF/views/P00_layout/header.jsp"%>

	<div class="layout_snb">
		<div class="snbContent">
			<%@ include file="/WEB-INF/views/P00_layout/snb.jsp"%>
		</div>
		<div class="content">



			
			<div class="model-body">
				<div class="model-high">
					<h2>회원가입</h2>
				</div>
				<form method="get" action="login">
					<div class="center">
						<input type="text" class="input-1 radius" name="join_name"
							placeholder="이름"><br> <input type="text"
							class="input-1 radius" name="join_phone" placeholder="연락처"><br>

						<div class="chain">
							<input type="text" class="input-2 radius" name="join_dept"
								placeholder="부서번호"><br> <input type="text"
								class="input-2 radius" name="join_mgr" placeholder="상사 사원번호"><br>
							<input type="text" class="input-2 radius" name="join_license"
								placeholder="자격증"><br>
						</div>
						<br> <input type="password" class="input-1 radius"
							name="join_pw" placeholder="비밀번호를 입력해주세요."><br> <input
							type="password" class="input-1 radius" name="join_pw2"
							placeholder="비밀번호 확인"><br>
						<button type="submit" class="buttonMain" name="join_btn">회원가입</button>
						<br>
					</div>
				</form>
				<a href="mypage.jsp">직원 등록? (관리자 마이페이지)</a>
			</div>



		</div>
	</div>





</body>

</html>
