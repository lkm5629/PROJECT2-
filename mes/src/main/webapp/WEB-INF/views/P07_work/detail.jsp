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
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>작업 상세 페이지</title>

<link rel="stylesheet" href="/mes/static/css/P00_common/common.css">

<link rel="stylesheet" href="/mes/static/css/P00_layout/header.css">
<script src="/mes/static/js/00_layout/header.js"></script>

<link rel="stylesheet" href="/mes/static/css/P00_layout/snb.css">
<script src="/mes/static/js/00_layout/snb.js"></script>
    
<link rel="stylesheet" href="/mes/static/css/P07_work/detail.css">
<script src="/mes/static/js/07_work/detail.js"></script>

</head>
<body>

<!-- 	LOT, BOM, 공정 내용 추가해야 함 -->

	<%@ include file="/WEB-INF/views/P00_layout/header.jsp" %>
	
	<div class="layout_snb">
		<div class="snbContent">
			<%@ include file="/WEB-INF/views/P00_layout/snb.jsp" %>
		</div>
		<div class="content">
		    <!-- 상단 타이틀 영역 -->
		    <div class="page-header">
		        <div class="title-group">
		            <h2>작업기록</h2>
		            <p class="sub-text">작업코드 : ${woInfo != null ? woInfo.woId : '-'}</p>
		        </div>
		
		        <div class="button-group">
			        <a href="/mes/worklist">
			            <button type="button" class="buttonWhite">목록으로</button>
			        </a>
			        <a href="/mes/womodify?woId=${woInfo.woId}">
			            <button type="button" class="buttonMain">지시 수정</button>
			        </a>
			        <a href="/mes/contentmodify?woId=${woInfo.woId}">
			            <button type="button" class="buttonSub">내용 수정</button>
			        </a>
		        </div>
		    </div>
		
		
		    <!-- 작업정보 -->
		    <div class="card">
		        <div class="card-header">
		            <div>
		                <strong>작업정보</strong>
		                <span class="sub">생산계획 : (${woInfo != null ? woInfo.planId : '-'})</span>
		            </div>
	            	<c:if test="${ woInfo.woStatus == 10 }">
	            		<span class="status before">작업 전</span>
	            	</c:if>
	            	<c:if test="${ woInfo.woStatus == 20 }">
	            		<span class="status ongoing">작업 중</span>
	            	</c:if>
	            	<c:if test="${ woInfo.woStatus == 30 }">
	            		<span class="status finish">작업 완료</span>
	            	</c:if>
	            	<c:if test="${ woInfo.woStatus == 40 }">
	            		<span class="status qcFin">검사 완료</span>
	            	</c:if>
	            	<c:if test="${ woInfo.woStatus == 50 }">
	            		<span class="status hold">보류</span>
	            	</c:if>
	            	<c:if test="${ woInfo == null }">
	            		<span class="status">-</span>
	            	</c:if>
		        </div>
		
		        <div class="info-grid">
		            <div class="info-box">
		                <span class="label">작업일</span>
		                <span class="value">${woInfo != null ? woInfo.workDate : ' - '}</span>
		            </div>
		
		            <div class="info-box">
		                <span class="label">작업자</span>
		                <span class="value">${woInfo != null ? woInfo.wName : ' - '} (${woInfo != null ? woInfo.worker : ' - '})</span>
		            </div>
		
		            <div class="info-box">
		                <span class="label">제품</span>
		                <span class="value">${woInfo != null ? woInfo.itemName : ' - '} (${woInfo != null ? woInfo.itemId : ' - '})</span>
		            </div>
		
		            <div class="info-box">
		                <span class="label">완제품 LOT</span>
		                <span class="value"> - </span>
		            </div>
		        </div>
		        
		        <hr>
		
		        <!-- 진행률 -->
		        <div class="progress-area">
		            <div class="progress-header">
		                <span class="progress-title">진행률</span>
		                <span class="percent">( ${woInfo != null ? woInfo.prevQty : ' - '} / ${woInfo != null ? woInfo.woQty : ' - '} ) <strong><fmt:formatNumber value="${woInfo != null ? (woInfo.prevQty/woInfo.woQty)*100 : ' 0 '}" maxFractionDigits="1"/>%</strong></span>
		            </div>
		
		            <div class="progress-bar">
		                <div class="progress-fill" style="width: ${woInfo != null ? (woInfo.prevQty/woInfo.woQty)*100 : '0'}%;"></div>
		            </div>
		        </div>
		    </div>
		    
		    
		    
		    <!-- 작업지시 상세사항 -->
		    <div class="card">
		    	<div class="card-header">
		    		<strong>작업지시 상세사항</strong>
		    	</div>
		    	
		    	<table class="table">
		    		<tr>
		    			<td class="woContent">
		    				<c:if test="${empty woInfo.content}">
		    					내용 없음
		    				</c:if>
		    				<c:if test="${not empty woInfo.content}">
		    					${woInfo.content}
		    				</c:if>
		    			</td>
		    		</tr>
		    	</table>
		    </div>
		
			<div class="procInfo">
			    <!-- BOM -->
			    <div class="card">
			        <div class="card-header">
			            <strong>BOM (자재 구성)</strong>
			        </div>
			
			        <table class="bomList">
			            <thead>
			                <tr>
			                    <th>자재</th>
			                    <th>소요량</th>
			                    <th>단위</th>
			                </tr>
			            </thead>
			            <tbody>
			                <tr>
			                    <td>자재명 (자재코드)</td>
			                    <td>1</td>
			                    <td>장</td>
			                </tr>
			            </tbody>
			        </table>
			    </div>
			
			
			    <!-- 공정 정보 -->
			    <div class="card">
			        <div class="card-header">
			            <strong>공정 정보 (공정번호)</strong>
			        </div>
			
			        <div class="process-list">
			            <div class="process-item">
			                <div class="step">1</div>
			                <div>
			                    <div class="process-title">용액 합침</div>
			                    <div class="process-desc">재단된 원단을 용액에 합침</div>
			                </div>
			            </div>
			
			            <div class="process-item">
			                <div class="step">2</div>
			                <div>
			                    <div class="process-title">롤러 프레싱</div>
			                    <div class="process-desc">롤러로 이동, 과다 용액 제거</div>
			                </div>
			            </div>
			
			            <div class="process-item">
			                <div class="step">3</div>
			                <div>
			                    <div class="process-title">포장 준비</div>
			                    <div class="process-desc">포장지 위로 이동</div>
			                </div>
			            </div>
			
			            <div class="process-item">
			                <div class="step">4</div>
			                <div>
			                    <div class="process-title">포장지 실링</div>
			                    <div class="process-desc">포장지 4면 heat sealing</div>
			                </div>
			            </div>
			        </div>
			    </div>
			</div>
		    
		</div>
	</div>

</body>
</html>