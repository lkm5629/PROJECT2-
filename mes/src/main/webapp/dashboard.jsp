<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html lang="kr">

<head>
    <meta charset="EUC-KR">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="static/css/P00_common/common.css">
    <link rel="stylesheet" href="static/css/dashboard.css">
</head>

<body>
    <header class="radius">
    헤더 자리.
    </header>
    <div class="dash-body">
    <div class="snb radius">snb</div>
    <div class="snb-bro">
    <div class="title-box">
    <h1>알콜스왑 MES</h1>
    <h7>제조 실행 시스템</h7>    
    </div>
	<div class="board-box">
	  <div class="box-type1 radius">
	  <h3>공정 진행률</h3>
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


</body>

</html>