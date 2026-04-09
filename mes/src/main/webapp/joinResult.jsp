<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page import="P01_auth.LoginDTO"%>
<%@page import="java.util.*"%>


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
    
    <link rel="stylesheet" href="static/css/joinResult.css">
</head>

<body>
    <img class="logo" src="static/images/logo.png" alt="MES 로고"> 
    <h1>알콜스왑 MES</h1>
    <h7>제조 실행 시스템</h7>
	<div class="model-body">
	<div class="model-high">
        <h2>회원가입 결과확인</h2>
		<span class="close-btn" >&times;</span>
	</div>
		<form method="get" action="login">
			<div class="center">
				
				<table>
				<tr>
				<td>이름</td>
				<td> &nbsp; &nbsp; ${ list.ename } &nbsp;</td>
				</tr>
				<tr>
				<td>사원번호</td>
				<td> &nbsp; &nbsp; ${ list.empid } &nbsp;</td>
				</tr>
				<tr>
				<td>비밀번호</td>
				<td> &nbsp; &nbsp; ${ list.password } &nbsp;</td>
				</tr>
				<tr>
				<td>부서명</td>
				<td> &nbsp; &nbsp; ${ list.deptname } &nbsp; </td>
				</tr>
				<tr>
				<td>부서번호</td>
				<td> &nbsp; &nbsp; ${ list.deptno } &nbsp; </td>
				</tr>
				<tr>
				<td>입사일</td>
				<td> &nbsp; &nbsp; ${ list.hiredate } &nbsp;</td>
				</tr>
				
				</table>
				
		
				<div></div>
				
			</div>
		</form>
		<c:if test=" ${not empty error} ">
		<div style="color: red; ">${error}</div>
		</c:if>
		<div>http://localhost:8080/mes/joinResult.jsp</div>
		<div>http://localhost:8080/mes/join.jsp</div>
	</div>


</body>

</html>