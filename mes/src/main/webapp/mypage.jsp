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
<link rel="stylesheet" href="static/css/mypage.css">
</head>

<body>


<%@ include file="/WEB-INF/views/P00_layout/header.jsp" %>
    
    <div class="layout_snb">
        <div class="snbContent">
            <%@ include file="/WEB-INF/views/P00_layout/snb.jsp" %>
        </div>
        <div class="content">
            
		<div class="snb-bro">
			<div class="title-box">
				<h1>마이페이지</h1>
				<h7>제조 실행 시스템</h7>
			</div>
			<div class="board-box">
				<div class="box-type4 radius">
					<div class="mp-title">
						<h3>사용자 정보</h3>
						<span>기본 계정 및 계정 설정</span>
					</div>
					<form method="get" action="">
						<div class="mp-tool">
						<div class="mp-chain">
							<span>이름</span> 
							<input type="text" name="mp-name" class="input-3 radius"> 
						</div>
						<div class="mp-chain">
							<span>연락처</span> 
							<input type="text" name="mp-p_no" class="input-3 radius">
						</div>
						<div class="mp-chain">
							 <span>부서</span>
							<input type="text" name="mp-dept" class="input-3 radius">
						</div>
						<div class="mp-chain">
							<span>비밀번호</span> 
							<input type="text" name="mp-pw" class="input-3 radius">
						</div>
						<div class="mp-chain">
							<span>비밀번호 확인</span> 
							<input type="text" name="mp-pw2" class="input-3 radius">
						</div>
						<div class="mp-chain">
							 <span>입사일</span> 
							 <input type="date" name="mp-h_date" class="input-3 radius">
						</div>
						<div class="mp-chain">
							 <span> </span>	
							 <button type="submit" name="mp-btn" class="buttonMain">정보	수정</button>
						</div>
						</div>
					</form>
				</div>
				<div class="box-type4 radius">
					<div class="title-box">
						<h3>내 작업</h3>
						<span>내 작업 지시서 보여주기</span>
					</div>
				</div>
			</div>
		</div>
            
            
        </div>
    </div>
    
	
		


</body>

</html>