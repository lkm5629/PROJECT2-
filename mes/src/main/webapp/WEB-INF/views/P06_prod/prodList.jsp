<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.util.*" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>작업지시</title>

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
       ① 생산관리 목록
       ==================================================== -->
  <div id="page-list">
    <div class="page-header-row">
      <div>
        <h1>생산관리</h1>
        <p style="font-size:13px; color:var(--text-secondary); margin-top:3px;">
          주간 생산 계획을 조회하고 관리합니다
        </p>
      </div>
      <button class="btn btn-primary">
        <svg width="14" height="14" viewBox="0 0 14 14" fill="none">
          <path d="M7 2v10M2 7h10" stroke="currentColor" stroke-width="1.8" stroke-linecap="round"/>
        </svg>
        생산계획 등록
      </button>
    </div>
 
    <!-- 검색 툴바 -->
    <div class="table-toolbar">
      <div class="search-wrap">
        <svg class="search-icon" width="14" height="14" viewBox="0 0 14 14" fill="none">
          <circle cx="6" cy="6" r="4.5" stroke="#94A3B8" stroke-width="1.4"/>
          <path d="M9.5 9.5L12 12" stroke="#94A3B8" stroke-width="1.4" stroke-linecap="round"/>
        </svg>
        <input type="text" class="search-input" placeholder="제품명으로 검색" id="searchKeyword" name="searchKeyword">
      </div>
      <button class="btn btn-outline btn-sm" style="height:36px;">검색</button>
      <div class="toolbar-sep"></div>
      <input type="date" class="date-input" id="startDate" name="startDate" title="시작일">
      <input type="date" class="date-input" id="endDate"   name="endDate"   title="종료일">
      <div style="margin-left:auto;">
        <button class="btn btn-danger-outline btn-sm" style="height:36px;">선택 삭제</button>
      </div>
    </div>
 
    <!-- 테이블 -->
    <div class="table-wrap">
      <table>
        <thead>
          <tr>
            <th class="checkbox-col"><input type="checkbox" id="chkAll"></th>
            <th>계획ID</th>
            <th>제품명</th>
            <th>목표수량</th>
            <th>기간</th>
            <th>진행률</th>
            <th>상태</th>
            <th>관리</th>
          </tr>
        </thead>
        <tbody id="planListBody">
          <tr>
            <td><input type="checkbox" name="chk" value="PP-2025-001"></td>
            <td><span style="font-family:monospace; font-size:12px; color:var(--text-secondary);">PP-2025-001</span></td>
            <td style="font-weight:500;">스테인리스 볼트 M10</td>
            <td>5,000 EA</td>
            <td><span class="text-secondary" style="font-size:12px;">01/06 – 01/10</span></td>
            <td>
              <div class="progress-wrap">
                <div class="progress-bar"><div class="progress-fill high" style="width:65%"></div></div>
                <span class="progress-pct">65%</span>
              </div>
            </td>
            <td><span class="badge badge-blue">진행중</span></td>
            <td><button class="btn btn-outline btn-sm">상세</button></td>
          </tr>
          <tr>
            <td><input type="checkbox" name="chk" value="PP-2025-002"></td>
            <td><span style="font-family:monospace; font-size:12px; color:var(--text-secondary);">PP-2025-002</span></td>
            <td style="font-weight:500;">알루미늄 너트 M8</td>
            <td>3,200 EA</td>
            <td><span class="text-secondary" style="font-size:12px;">01/06 – 01/12</span></td>
            <td>
              <div class="progress-wrap">
                <div class="progress-bar"><div class="progress-fill mid" style="width:45%"></div></div>
                <span class="progress-pct">45%</span>
              </div>
            </td>
            <td><span class="badge badge-blue">진행중</span></td>
            <td><button class="btn btn-outline btn-sm">상세</button></td>
          </tr>
          <tr>
            <td><input type="checkbox" name="chk" value="PP-2025-003"></td>
            <td><span style="font-family:monospace; font-size:12px; color:var(--text-secondary);">PP-2025-003</span></td>
            <td style="font-weight:500;">티타늄 플레이트 3T</td>
            <td>800 EA</td>
            <td><span class="text-secondary" style="font-size:12px;">01/13 – 01/17</span></td>
            <td>
              <div class="progress-wrap">
                <div class="progress-bar"><div class="progress-fill low" style="width:0%"></div></div>
                <span class="progress-pct">0%</span>
              </div>
            </td>
            <td><span class="badge badge-gray">대기</span></td>
            <td><button class="btn btn-outline btn-sm">상세</button></td>
          </tr>
          <tr>
            <td><input type="checkbox" name="chk" value="PP-2025-004"></td>
            <td><span style="font-family:monospace; font-size:12px; color:var(--text-secondary);">PP-2025-004</span></td>
            <td style="font-weight:500;">카본 샤프트 Ø12</td>
            <td>1,500 EA</td>
            <td><span class="text-secondary" style="font-size:12px;">01/13 – 01/20</span></td>
            <td>
              <div class="progress-wrap">
                <div class="progress-bar"><div class="progress-fill low" style="width:0%"></div></div>
                <span class="progress-pct">0%</span>
              </div>
            </td>
            <td><span class="badge badge-gray">대기</span></td>
            <td><button class="btn btn-outline btn-sm">상세</button></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <!-- /page-list -->
 
 
  <!-- ====================================================
       ② 생산계획 등록
       ==================================================== -->
  <div id="page-register">
    <div class="page-header">
      <h1>생산계획 등록</h1>
      <p>새로운 주간 생산계획을 등록합니다</p>
    </div>
 
    <div class="card">
      <div class="card-title">생산계획 정보</div>
      <form id="registerForm" action="/production/plan/insert" method="post">
        <div class="form-grid">
          <div class="form-group">
            <label class="form-label" for="regProduct">제품 <span class="req">*</span></label>
            <select class="form-control" id="regProduct" name="productCd" required>
              <option value="">제품 선택</option>
              <option value="P001">스테인리스 볼트 M10</option>
              <option value="P002">알루미늄 너트 M8</option>
              <option value="P003">티타늄 플레이트 3T</option>
              <option value="P004">카본 샤프트 Ø12</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-label" for="regQty">목표수량 <span class="req">*</span></label>
            <input type="number" class="form-control" id="regQty" name="targetQty"
                   placeholder="목표 수량 입력" min="1" required>
          </div>
          <div class="form-group">
            <label class="form-label" for="regStartDate">시작일 <span class="req">*</span></label>
            <input type="date" class="form-control" id="regStartDate" name="startDate" required>
          </div>
          <div class="form-group">
            <label class="form-label" for="regEndDate">종료일 <span class="req">*</span></label>
            <input type="date" class="form-control" id="regEndDate" name="endDate" required>
          </div>
        </div>
        <div class="form-actions">
          <button type="button" class="btn btn-outline">취소</button>
          <button type="submit" class="btn btn-primary">등록</button>
        </div>
      </form>
    </div>
  </div>
  <!-- /page-register -->
 
 
  <!-- ====================================================
       ③ 생산계획 상세
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
        <button class="btn btn-outline btn-sm">목록</button>
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
                style="font-family:monospace; font-size:13px;">PP-2025-001</span>
        </div>
        <div class="info-item">
          <span class="info-label">제품명</span>
          <span class="info-value em" id="dtlProductNm">스테인리스 볼트 M10</span>
        </div>
        <div class="info-item">
          <span class="info-label">담당자</span>
          <span class="info-value" id="dtlManager">김생산</span>
        </div>
        <div class="info-item" style="margin-top:12px;">
          <span class="info-label">시작일</span>
          <span class="info-value" id="dtlStartDate">2025-01-06</span>
        </div>
        <div class="info-item" style="margin-top:12px;">
          <span class="info-label">종료일</span>
          <span class="info-value" id="dtlEndDate">2025-01-10</span>
        </div>
        <div class="info-item" style="margin-top:12px;">
          <span class="info-label">상태</span>
          <span class="badge badge-blue" id="dtlStatus" style="margin-top:2px;">진행중</span>
        </div>
      </div>
    </div>
 
    <!-- 진행 현황 -->
    <div class="card">
      <div class="section-title">진행 현황</div>
      <div class="metric-grid">
        <div class="metric-card">
          <span class="metric-label">목표수량</span>
          <span class="metric-value" id="dtlTargetQty">5,000</span>
          <span class="metric-unit">EA</span>
          <div class="metric-bar primary"></div>
        </div>
        <div class="metric-card">
          <span class="metric-label">생산수량</span>
          <span class="metric-value" id="dtlProdQty">3,250</span>
          <span class="metric-unit">EA</span>
          <div class="metric-bar success"></div>
        </div>
        <div class="metric-card">
          <span class="metric-label">잔여수량</span>
          <span class="metric-value" id="dtlRemainQty">1,750</span>
          <span class="metric-unit">EA</span>
          <div class="metric-bar warning"></div>
        </div>
      </div>
 
      <div class="detail-progress-wrap">
        <div class="detail-progress-header">
          <span class="detail-progress-label">진행률</span>
          <span class="detail-progress-pct" id="dtlProgressPct">65%</span>
        </div>
        <div class="detail-progress-bar">
          <div class="detail-progress-fill" id="dtlProgressFill" style="width:65%"></div>
        </div>
        <div class="detail-progress-markers">
          <span>0%</span>
          <span>100%</span>
        </div>
      </div>
    </div>
  </div>
  <!-- /page-detail -->
 
 
  <!-- ====================================================
       ④ 생산계획 수정
       ==================================================== -->
  <div id="page-edit">
    <div class="page-header">
      <h1>생산계획 수정</h1>
      <p>주간 생산계획 정보를 수정합니다</p>
    </div>
 
    <div class="card">
      <div class="card-title">생산계획 정보</div>
      <form id="editForm" action="/production/plan/update" method="post">
        <input type="hidden" id="editPlanId" name="planId" value="PP-2025-002">
        <div class="form-grid">
          <div class="form-group">
            <label class="form-label" for="editProduct">제품 <span class="req">*</span></label>
            <select class="form-control" id="editProduct" name="productCd" required>
              <option value="">제품 선택</option>
              <option value="P001">스테인리스 볼트 M10</option>
              <option value="P002" selected>알루미늄 너트 M8</option>
              <option value="P003">티타늄 플레이트 3T</option>
              <option value="P004">카본 샤프트 Ø12</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-label" for="editQty">목표수량 <span class="req">*</span></label>
            <input type="number" class="form-control" id="editQty" name="targetQty"
                   value="3200" min="1" required>
          </div>
          <div class="form-group">
            <label class="form-label" for="editStartDate">시작일 <span class="req">*</span></label>
            <input type="date" class="form-control" id="editStartDate" name="startDate"
                   value="2025-01-06" required>
          </div>
          <div class="form-group">
            <label class="form-label" for="editEndDate">종료일 <span class="req">*</span></label>
            <input type="date" class="form-control" id="editEndDate" name="endDate"
                   value="2025-01-12" required>
          </div>
        </div>
        <div class="form-actions">
          <button type="button" class="btn btn-outline">취소</button>
          <button type="submit" class="btn btn-primary">수정</button>
        </div>
      </form>
    </div>
  </div>
  <!-- /page-edit -->
 
</main>
            
            
            
        </div>
    </div>
    
</body>
</html>