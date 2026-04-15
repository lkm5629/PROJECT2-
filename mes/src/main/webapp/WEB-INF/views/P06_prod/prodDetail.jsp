<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, P06_prod.ProdDTO"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    ProdDTO dto = (ProdDTO) request.getAttribute("dto");
    int planQty   = dto.getPlanQty();
    int prevQty   = dto.getPrevQty();
    int remainQty = planQty - prevQty;
    int progress  = (planQty > 0) ? (int)(prevQty * 100.0 / planQty) : 0;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>생산계획 상세/수정</title>

<link rel="stylesheet" href="/mes/static/css/P00_common/common.css">

<link rel="stylesheet" href="/mes/static/css/P00_layout/header.css">
<script src="/mes/static/js/00_layout/header.js"></script>

<link rel="stylesheet" href="/mes/static/css/P00_layout/snb.css">
<script src="/mes/static/js/00_layout/snb.js"></script>
    
<link rel="stylesheet" href="/mes/static/css/P07_work/main.css">
<link rel="stylesheet" href="/mes/static/css/P06_prod/prod.css">

</head>
<body>

    <%@ include file="/WEB-INF/views/P00_layout/header.jsp" %>
    
    <div class="layout_snb">
        <div class="snbContent">
            <%@ include file="/WEB-INF/views/P00_layout/snb.jsp" %>
        </div>
        <div class="content">
            <!-- 작업공간 -->
            <main class="pp">
 
  <!-- ====================================================
       생산계획 상세
       ==================================================== -->
  <div id="page-detail">
    <div class="page-header-row">
      <div>
        <h1>생산계획 상세</h1>
        <p style="font-size:13px; color:var(--text-secondary); margin-top:3px;">
          생산계획 상세 정보를 확인합니다
        </p>
      </div>
      <div class="action-header">
        <button class="btn btn-outline btn-sm"
                onclick="location.href='list'">목록</button>
        <button class="btn btn-success-outline btn-sm">수정</button>
        <button class="btn btn-danger btn-sm">삭제</button>
      </div>
    </div>
 
    <!-- 기본 정보 -->
    <div class="card" style="margin-bottom:16px;">
      <div class="section-title">기본 정보</div>
      <div class="info-grid">
        <div class="info-item">
          <span class="info-label">계획ID</span>
          <span class="info-value em" id="dtlPlanId"
                style="font-family:monospace; font-size:13px;">${dto.planId}</span>
        </div>
        <div class="info-item">
          <span class="info-label">제품명</span>
          <span class="info-value em" id="dtlProductNm">${dto.itemName}</span>
        </div>
        <div class="info-item">
          <span class="info-label">담당자</span>
          <span class="info-value" id="dtlManager">${dto.ename}</span>
        </div>
        <div class="info-item" style="margin-top:12px;">
          <span class="info-label">시작일</span>
          <span class="info-value" id="dtlStartDate">${dto.planSdate}</span>
        </div>
        <div class="info-item" style="margin-top:12px;">
          <span class="info-label">종료일</span>
          <span class="info-value" id="dtlEndDate">${dto.planEdate}</span>
        </div>
        <div class="info-item" style="margin-top:12px;">
          <span class="info-label">상태</span>
          <c:choose>
            <c:when test="${dto.status == 0}"><span class="badge badge-gray"  id="dtlStatus" style="margin-top:2px;">대기</span></c:when>
            <c:when test="${dto.status == 1}"><span class="badge badge-blue"  id="dtlStatus" style="margin-top:2px;">진행중</span></c:when>
            <c:when test="${dto.status == 2}"><span class="badge badge-green" id="dtlStatus" style="margin-top:2px;">완료</span></c:when>
            <c:when test="${dto.status == 3}"><span class="badge badge-yellow" id="dtlStatus" style="margin-top:2px;">보류</span></c:when>
          </c:choose>
        </div>
      </div>
    </div>
 
    <!-- 진행 현황 -->
    <div class="card">
      <div class="section-title">진행 현황</div>
      <div class="metric-grid">
        <div class="metric-card">
          <span class="metric-label">목표수량</span>
          <span class="metric-value" id="dtlTargetQty"><%= planQty %></span>
          <span class="metric-unit">EA</span>
          <div class="metric-bar primary"></div>
        </div>
        <div class="metric-card">
          <span class="metric-label">생산수량</span>
          <span class="metric-value" id="dtlProdQty"><%= prevQty %></span>
          <span class="metric-unit">EA</span>
          <div class="metric-bar success"></div>
        </div>
        <div class="metric-card">
          <span class="metric-label">잔여수량</span>
          <span class="metric-value" id="dtlRemainQty"><%= remainQty %></span>
          <span class="metric-unit">EA</span>
          <div class="metric-bar warning"></div>
        </div>
      </div>
 
      <div class="detail-progress-wrap">
        <div class="detail-progress-header">
          <span class="detail-progress-label">진행률</span>
          <span class="detail-progress-pct" id="dtlProgressPct"><%= progress %>%</span>
        </div>
        <div class="detail-progress-bar">
          <div class="detail-progress-fill" id="dtlProgressFill" style="width:<%= progress %>%"></div>
        </div>
        <div class="detail-progress-markers">
          <span>0%</span>
          <span>100%</span>
        </div>
      </div>
    </div>
  </div>
  <!-- /page-detail -->

</main>
        </div>
    </div>
    
</body>
</html>