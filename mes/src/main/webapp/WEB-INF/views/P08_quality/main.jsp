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
<title>품질관리</title>

<link rel="stylesheet" href="/mes/static/css/P00_common/common.css">

<link rel="stylesheet" href="/mes/static/css/P00_layout/header.css">
<script src="/mes/static/js/00_layout/header.js"></script>

<link rel="stylesheet" href="/mes/static/css/P00_layout/snb.css">
<script src="/mes/static/js/00_layout/snb.js"></script>
    
<link rel="stylesheet" href="/mes/static/css/P08_quality/main.css">

</head>
<body>
	
	<%@ include file="/WEB-INF/views/P00_layout/header.jsp" %>
    
    <div class="layout_snb">
        <div class="snbContent">
            <%@ include file="/WEB-INF/views/P00_layout/snb.jsp" %>
        </div>
        <div class="content">
        
        	<!-- 페이지 상단 -->
		    <div class="pageName">
		        <div>
		            <h2>품질 관리</h2>
		            <p>품질검사 내역</p>
		        </div>
		
		        <button class="addBtn buttonMain">
		            + 검사계획 등록
		        </button>
		    </div>
		
		    <!-- 요약 카드 -->
		    <div class="summary">
		        <div class="card">
		            <p>금일 총 검사 수량</p>
		            <strong>1,500</strong>
		        </div>
		        <div class="card">
		            <p>합격 수량</p>
		            <strong>1,475</strong>
		        </div>
		        <div class="card">
		            <p>불량 수량</p>
		            <strong>25</strong>
		        </div>
		        <div class="card">
		            <p>평균 불량률</p>
		            <strong>1.75%</strong>
		        </div>
		    </div>
		
		    <!-- 리스트 -->
		    <div class="list">
		
		        <div class="listTop">
		            <h3>검사 기록 보기</h3>
		
		            <form method="get" action="/mes/qclist">
		                <input type="hidden" name="cmd" value="search">
		
		                <div class="search-tools">
		                    <div class="category">
		                        <select name="status">
		                            <option value="0">전체 보기</option>
		                            <option value="10">진행중</option>
		                            <option value="20">완료</option>
		                        </select>
		
		                        <input type="date" name="startDate" class="date"> ~
		                        <input type="date" name="endDate" class="date">
		                    </div>
		
		                    <div class="search-area">
		                        <input type="text" name="keyword" placeholder="제품명 또는 검사자 검색">
		                        <button type="submit" class="buttonMain">검색</button>
		                    </div>
		                </div>
		            </form>
		        </div>
		
		        <!-- 테이블 -->
		        <table class="qc-table">
		            <thead>
		                <tr>
		                    <th>검사코드</th>
		                    <th>작업코드</th>
		                    <th>제품</th>
		                    <th>검사수량</th>
		                    <th>합격</th>
		                    <th>불량</th>
		                    <th>불량률</th>
		                    <th>검사일</th>
		                    <th>검사자</th>
		                    <th>상태</th>
		                </tr>
		            </thead>
		
		            <tbody>
		                <c:forEach var="i" items="${ qcMap.list }">
		                    <tr onclick="location.href='/mes/qclist?cmd=detail&qcId=${i.qcId}'">
		                        <td>${ i.qcId }</td>
		                        <td>${ i.woId }</td>
		                        <td>${ i.itemName }(${ i.itemId })</td>
		                        <td>${ i.qcQty }</td>
		                        <td>${ i.passQty }</td>
		                        <td>${ i.failQty }</td>
		                        <td>
		                            <fmt:formatNumber value="${ (i.failQty / i.qcQty) * 100 }" maxFractionDigits="1"/>%
		                        </td>
		                        <td>${ i.qcDate }</td>
		                        <td>${ i.worker }</td>
		                        <td>
		                            <c:if test="${ i.status == 10 }">
		                                <span class="status ongoing">진행중</span>
		                            </c:if>
		                            <c:if test="${ i.status == 20 }">
		                                <span class="status finish">완료</span>
		                            </c:if>
		                        </td>
		                    </tr>
		                </c:forEach>
		
		                <c:if test="${ empty qcMap.list }">
		                    <tr>
		                        <td colspan="10">내용 없음</td>
		                    </tr>
		                </c:if>
		            </tbody>
		        </table>
		
		        <!-- 페이징 -->
		        <div class="page">
		            <c:forEach var="i" begin="1" end="${qcMap.totalPage}">
		                <a href="/mes/qclist?page=${i}">
		                    <c:if test="${qcMap.page eq i}">
		                        <strong>${i}</strong>
		                    </c:if>
		                    <c:if test="${qcMap.page ne i}">
		                        ${i}
		                    </c:if>
		                </a>
		            </c:forEach>
		        </div>
        
        </div>
    </div>
	
</body>
</html>