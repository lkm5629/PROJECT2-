<%@ page language="java" contentType="text/html; charset="UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html lang="kr">

<head>
    <meta charset="EUC-KR">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="/mes/static/css/P00_common/common.css">

    <link rel="stylesheet" href="/mes/static/css/P00_layout/header.css">
    <script src="/mes/static/js/00_layout/header.js"></script>

	<link rel="stylesheet" href="/mes/static/css/P00_layout/snb.css">
	<script src="/mes/static/js/00_layout/snb.js"></script>
    <link rel="stylesheet" href="static/css/dashboard.css">
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
    <h1>알콜스왑 MES</h1>
    <h7>제조 실행 시스템</h7>    
    </div>
	<div class="board-box">
	  <div class="box-type1 radius">
	  <h3>공정 진행률</h3>
	  <div>차트 1</div>
	  </div>
	  <div class="box-type1 radius">
	  <h3>불량률</h3>
	  </div>
	  <div class="box-type2 radius">
	  <h3>일변 생산 현황</h3>
	  </div>
	  <div class="box-type2 radius">
	  <h3>불량 유형별 분포</h3>
	  </div>
	  <div class="box-type3 radius">
	  <h3>알림</h3>
	  </div>
	  <div class="box-type3 radius">
	  <h3>공지사항</h3>
	  </div>
	  <div class="box-type3 radius">
	  <h3>건의사항</h3>
	  </div>
	</div>
	</div>
			

        </div>
    </div>


    
    


</body>

</html>