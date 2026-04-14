<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.util.*" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>설비 정보 상세보기</title>

<link rel="stylesheet" href="/mes/static/css/P00_common/common.css">

<link rel="stylesheet" href="/mes/static/css/P00_layout/header.css">
<script src="/mes/static/js/00_layout/header.js"></script>

<link rel="stylesheet" href="/mes/static/css/P00_layout/snb.css">
<script src="/mes/static/js/00_layout/snb.js"></script>
    
<link rel="stylesheet" href="/mes/static/css/P07_work/detail.css">
<link rel="stylesheet" href="/mes/static/css/P09_equip/equip.css">
<script src="/mes/static/js/09_equip/detail.js"></script>

</head>
<body>

    <%@ include file="/WEB-INF/views/P00_layout/header.jsp" %>
    
    <div class="layout_snb">
        <div class="snbContent">
            <%@ include file="/WEB-INF/views/P00_layout/snb.jsp" %>
        </div>
        <div class="content">
            <!-- 상단 타이틀 영역 -->
		    <div class="page-header">
		        <div class="title-group">
		            <h2>설비관리</h2>
		            <p class="sub-text">설비 : ${eqInfo.eqId}</p>
		        </div>
		
		        <div class="button-group">
			        <a href="/mes/worklist">
			            <button type="button" class="buttonWhite">목록으로</button>
			        </a>
			        <a href="/mes/eqlogadd?eqId=${eqInfo.eqId}">
			            <button type="button" class="buttonMain">설비 점검이력 추가</button>
			        </a>
		        </div>
		    </div>
		
		
		    <!-- 작업정보 -->
		    <div class="card">
		        <div class="card-header">
		            <div>
		                <strong>설비정보</strong>
		                <span class="sub">${eqInfo.eqName} (${eqInfo.eqId})</span>
		            </div>
	            	<c:if test="${ eqInfo.status == '가동' }">
	            		<span class="status run">가동</span>
	            	</c:if>
	            	<c:if test="${ eqInfo.status == '점검 중' }">
	            		<span class="status insp">점검 중</span>
	            	</c:if>
	            	<c:if test="${ eqInfo.status == '고장'}">
	            		<span class="status error">고장</span>
	            	</c:if>
	            	<c:if test="${ eqInfo.status == '사용중단' }">
	            		<span class="status hold">사용중단</span>
	            	</c:if>
	            	<c:if test="${ eqInfo == null }">
	            		<span class="status">-</span>
	            	</c:if>
		        </div>
		
		        <div class="info-grid">
		            <div class="info-box">
		                <span class="label">설비 가동 시작일</span>
		                <span class="value">${eqInfo != null ? eqInfo.startDate : ' - '}</span>
		            </div>
		
		            <div class="info-box">
		                <span class="label">설비 총 가동시간</span>
		                <span class="value"><fmt:formatNumber value="${eqInfo.runMin / 60}" pattern="0" />시간 ${ eqInfo.runMin%60 }분</span>
		            </div>
		        </div>
		        
		        <hr>
		
		        <!-- 진행률 -->
		        <div class="progress-area">
		            <div class="progress-header">
		                <span class="progress-title">현재 가동률</span>
		                <span class="percent"><strong><fmt:formatNumber value="${eqInfo != null ? (eqInfo.runMin/eqInfo.totalMin)*100 : ' 0 '}" maxFractionDigits="1"/>%</strong></span>
		            </div>
		
		            <div class="progress-bar">
		                <div class="progress-fill" style="width: ${eqInfo != null ? (eqInfo.totalMin/eqInfo.runMin)*100 : '0'}%;"></div>
		            </div>
		        </div>
		    </div>
		    
		    
		    <div class="card">
		    	<div class="card-header">
		    		<strong>설비 점검이력</strong>
		    	</div>
		    	
		    	<table class="table">
		    		<thead>
		    		</thead>
		    	</table>
		    </div>
		
			
        </div>
    </div>
    
</body>
</html>