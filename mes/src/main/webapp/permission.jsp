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
    
    <link rel="stylesheet" href="static/css/permission.css">
</head>

<body>

<%@ include file="/WEB-INF/views/P00_layout/header.jsp" %>
    
    <div class="layout_snb">
        <div class="snbContent">
            <%@ include file="/WEB-INF/views/P00_layout/snb.jsp" %>
        </div>
        <div class="content">
            

<div class="snb-bro">
<div class="board-box">
				<div class="box-type4 radius">
					<div class="mp-title">
						<div class="model-close">
                            <h2>권한</h2>
		                    <span class="close-btn" >&times;</span>
	                    </div>
						<span>권한 부여 및 수정</span>
					</div>
					<form method="get" action="">
					<% for(int i=0;i<5;i++) { %>
						<div class="mp-tool">
						<div class="mp-chain radius">
							<li>사번</li> 
							<li>이름</li> 
							<li>직책</li> 
							<select class="grant" name="grant">
							<option>작업자</option>
							<option>관리자</option>
							<option>슈퍼바이저</option>
							</select> 
						</div>						
						</div>
						<% } %>
						<button class="buttonMain">권한 변경</button>
					</form>
				</div>
			</div>
			</div>

			
        </div>
    </div>




</body>

</html>