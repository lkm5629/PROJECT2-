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
			        <a href="/mes/equipment">
			            <button type="button" class="buttonWhite">목록으로</button>
			        </a>
			        <a href="/mes/eqlogadd?eqId=${eqInfo.eqId}&eqName=${eqInfo.eqName}">
			            <button type="button" class="buttonMain">설비 점검이력 추가</button>
			        </a>
<%-- 			        <c:if test="${ eqInfo.status == '가동' }"> --%>
<%-- 			        	<a href="/mes/eqdetail?eqId=${eqInfo.eqId}&cmd=eqStop" id="btnStop" class="buttonSub"> --%>
<!-- 						    <button type="button" id="btnStop" class="buttonSub">설비 정지</button> -->
<!-- 						</a> -->
<%-- 			        </c:if> --%>
<%-- 			        <c:if  test="${ eqInfo.status != '가동' }"> --%>
<%-- 			        	<a href="/mes/eqdetail?eqId=${eqInfo.eqId}&cmd=eqRun"> --%>
<!-- 					        <button type="button" id="btnRun" class="buttonSub">설비 가동</button> -->
<!-- 						</a> -->
<%-- 			        </c:if> --%>
			        <c:choose>
					    <c:when test="${eqInfo.status == '가동'}">
					        <a href="${pageContext.request.contextPath}/eqdetail?eqId=${eqInfo.eqId}&cmd=eqStop"
					           id="btnStop"
					           class="buttonSub error" <c:if test="${(empty dto.auth) || dto.auth < 2}">style="display: none;"</c:if> >설비 정지</a>
					    </c:when>
					    <c:otherwise>
					        <a href="${pageContext.request.contextPath}/eqdetail?eqId=${eqInfo.eqId}&cmd=eqRun"
					           id="btnRun"
					           class="buttonSub run" <c:if test="${(empty dto.auth) || dto.auth < 2}">style="display: none;"</c:if> >설비 가동</a>
					    </c:otherwise>
					</c:choose>
		        </div>
		    </div>
		
		
		    <!-- 작업정보 -->
		    <div class="card">
		        <div class="card-header">
		            <div>
		                <strong>설비정보</strong>
		                <span class="sub">${eqInfo.eqName} (${eqInfo.eqId})</span>
		            </div>
		            <div class="button-group">
		            	<div class="status-flex">
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
		            	<c:if test="${ eqInfo.status != '가동' && (!(empty dto.auth) || dto.auth >= 2)}">
			            	<form id="statusForm" method="post" action="/mes/eqdetail" class="button-group">
			            		<input type="hidden" name="cmd" value="statusChange">
			            		<input type="hidden" name="eqId" value="${eqInfo.eqId}">
			            		<select name="status" id="statusSelecthhvh">
								    <option value="점검 중" selected disabled>상태 선택</option>
								    <option value="점검 중" ${eqInfo.status == '점검 중' ? 'selected' : ''}>점검 중</option>
								    <option value="고장" ${eqInfo.status == '고장' ? 'selected' : ''}>고장</option>
								    <option value="사용중단" ${eqInfo.status == '사용중단' ? 'selected' : ''}>사용중단</option>
								</select>
								<button type="button" id="statusChange" class="buttonSub">상태 변경</button>
			            	</form>
		            	</c:if>
		            </div>
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
		                <span class="progress-title">가동률</span>
		                <span class="percent"><strong><fmt:formatNumber value="${eqInfo != null ? (eqInfo.runMin/eqInfo.totalMin)*100 : ' 0 '}" maxFractionDigits="1"/>%</strong></span>
		            </div>
		
		            <div class="progress-bar">
		                <div class="progress-fill" style="width: ${eqInfo != null ? (eqInfo.runMin/eqInfo.totalMin)*100 : '0'}%;"></div>
		            </div>
		        </div>
		    </div>
		    
		    <% 
				Map eqMap = (Map)request.getAttribute("eqMap");
		
				int size = (int)eqMap.get("size"); // 현재 페이지
				int totalPage = (int)eqMap.get("totalPage");
				
				int section = 5; // 한 번에 보여줄 페이지들의 수
				int pageNum = (int)eqMap.get("page"); // 현재 페이지
				
				int endSection = (int)Math.ceil((double)pageNum/section)*section;
				int startSection = endSection - section + 1;
				
				if (endSection > totalPage) {
					endSection = totalPage;
				}
			%>
			
		    <div class="card">
		    	<div class="card-header">
		    		<strong>설비 점검이력</strong>
		    	</div>
		    	
		    	<table class="table">
		    		<thead>
		    			<tr>
		    				<th>점검 시작일</th>
		    				<th>점검 종료일</th>
		    				<th>담당자</th>
		    				<th>관리 항목</th>
		    				<th>결과 및 조치</th>
		    			</tr>
		    		</thead>
		    		<tbody>
		    			<c:forEach var="i" items="${ log }">
			        		<tr class="logDetail" onclick="location.href='/mes/eqlogmodify?logId=${i.logId}'">
				                <td>${ i.sTime }</td>
				                <td>${ i.eTime }</td>
				                <td>${ i.wName } (${ i.wId })</td>
				                <td>${ i.inspType }</td>
				                <td>${ i.inspContent }</td>
				            </tr>
			        	</c:forEach>
			        	
			        	<c:if var="i" test="${ empty log }">
			        		<tr>
			        			<td colspan="5">내용 없음</td>
			        		</tr>
			        	</c:if>
		    		</tbody>
		    	</table>
		    	
		    	<div class="page">
			    	<c:if test="<%= startSection == 1 %>">
						&lt;
					</c:if>
					<c:if test="<%= startSection != 1 %>">
						<a href="./equipment?page=<%= startSection-1 %>&size=10">
							&lt;
						</a>
					</c:if>
					<c:forEach var="i" begin="<%= startSection %>" end="<%= endSection %>">
						<a href="./equipment?page=${ i }&size=10">
							<c:if test="${eqMap.page eq i}">
								<strong>
									${ i }
								</strong>
							</c:if>
							<c:if test="${!(eqMap.page eq i)}">
									${ i }
							</c:if>
						</a>
					</c:forEach>
					
					<c:if test="<%= endSection <= totalPage %>">
						&gt;
					</c:if>
					<c:if test="<%= !(endSection <= totalPage) %>">
						<a href="./equipment?page=<%= endSection+1 %>&size=10">
							&gt;
						</a>
					</c:if>
			    </div>
		    </div>
		    
			
        </div>
    </div>
    
</body>
</html>