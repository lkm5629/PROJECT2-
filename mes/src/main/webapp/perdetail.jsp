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
<link rel="stylesheet" href="static/css/perdetail.css">
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
                       <h2 class="h2">권한부여 상세</h2>
		               <span class="close-btn" >&times;</span>
	             </div>
	             
	             <div>
	             <c:if test="${ not empty messege }">
	             <strong>${ messege }</strong>
	             </c:if>
	             </div>
			
			<form method="get" action="login">
					<div class="center">
				           <div class="pd-box">
							사번 : <input type="text"
							class="input-1 radius" name="pd-empid" value="${ d.empid }">
				           </div>
				           <br>
				           <div class="pd-box">
						    부서 : <input type="text" class="input-1 radius"
							name="pd-deptname" value="${ d.deptname }"> 
				           </div>
				           <br>
				           <div class="pd-box">
						    이름 : <input type="text" class="input-1 radius" name="pd_ename"
							value="${ d.ename }">
				           </div>
				           <br>
				           <div class="pd-box">
						    권한 : <select name="permission" class="input-1 radius">
							<option>작업자</option>
							<option>관리자</option>
							<option>슈퍼바이저</option>
							</select>
				           </div>
							<br>
						<button type="submit" class="buttonMain" name="pd_btn">권한등록</button>
						<br>
					</div>
				</form>
				
	           
				
				<a href="mypage.jsp">직원 등록? (관리자 마이페이지)</a>
				<br>
				<br>
				
			</div>
			<script>
	            const close_btn = document.querySelector(".close-btn");
	            close_btn.addEventListener('click', function (evt) {
			    window.location.href = "/mes/permission"
	             })
	        </script>



		</div>
	</div>





</body>

</html>
