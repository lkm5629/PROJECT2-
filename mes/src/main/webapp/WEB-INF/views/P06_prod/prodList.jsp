<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>생산관리</title>

<link rel="stylesheet" href="/mes/static/css/P00_common/common.css">
<link rel="stylesheet" href="/mes/static/css/P00_layout/header.css">
<script src="/mes/static/js/00_layout/header.js"></script>
<link rel="stylesheet" href="/mes/static/css/P00_layout/snb.css">
<script src="/mes/static/js/00_layout/snb.js"></script>
<link rel="stylesheet" href="/mes/static/css/P06_prod/prod.css">

</head>
<body>

<%@ include file="/WEB-INF/views/P00_layout/header.jsp" %>

<div class="layout_snb">
    <div class="snbContent">
        <%@ include file="/WEB-INF/views/P00_layout/snb.jsp" %>
    </div>
    <div class="content">
        <main class="pp">

<%
    /* ── 페이지네이션 계산 ─────────────────────────────── */
    Map  map        = (Map)  request.getAttribute("map");
    int  total      = (int)  map.get("totalCount");
    int  size       = (int)  map.get("size");
    int  pageNum    = (int)  map.get("page");

    int  totalPage  = (int) Math.ceil((double) total / size);
    if (totalPage < 1) totalPage = 1;

    int  section       = 5;
    int  end_section   = (int) Math.ceil((double) pageNum / section) * section;
    int  start_section = end_section - section + 1;
    if (end_section > totalPage) end_section = totalPage;
%>

  <!-- ====================================================
       생산관리 목록
       ==================================================== -->
  <div id="page-list">
    <div class="page-header-row">
      <div>
        <h1>생산관리</h1>
        <p style="font-size:13px; color:var(--text-secondary); margin-top:3px;">
          주간 생산 계획을 조회하고 관리합니다
        </p>
      </div>
      <button class="btn btn-primary" onclick="openRegisterModal()">
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
        <input type="text" class="search-input" placeholder="제품명으로 검색"
               id="searchKeyword" name="searchKeyword">
      </div>
      <button class="btn btn-outline btn-sm" style="height:36px;">검색</button>
      <div class="toolbar-sep"></div>
      <input type="date" class="date-input" id="startDate" name="startDate" title="시작일">
      <input type="date" class="date-input" id="endDate"   name="endDate"   title="종료일">

      <!-- 페이지당 표시 수 -->
      <select id="sizeSelect" class="date-input" style="height:36px; cursor:pointer;">
        <option value="5"  <%= size==5  ? "selected" : "" %>>5건</option>
        <option value="10" <%= size==10 ? "selected" : "" %>>10건</option>
        <option value="15" <%= size==15 ? "selected" : "" %>>15건</option>
        <option value="20" <%= size==20 ? "selected" : "" %>>20건</option>
      </select>

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
            <th>상태</th>
            <th>관리</th>
          </tr>
        </thead>
        <tbody id="planListBody">
          <c:forEach var="dto" items="${map.list}">
            <tr>
              <td><input type="checkbox" name="chk" value="${dto.planId}"></td>
              <td>
                <span style="font-family:monospace; font-size:12px; color:var(--text-secondary);">
                  ${dto.planId}
                </span>
              </td>
              <td style="font-weight:500;">${dto.itemName}</td>
              <td>
                <fmt:formatNumber value="${dto.planQty}" pattern="#,###"/> EA
              </td>
              <td>
                <span class="text-secondary" style="font-size:12px;">
                  <%-- MM-dd ~ MM-dd 형식 --%>
                  <fmt:formatDate value="${dto.planSdate}" pattern="MM-dd"/>
                  &nbsp;~&nbsp;
                  <fmt:formatDate value="${dto.planEdate}" pattern="MM-dd"/>
                </span>
              </td>
              <td>
                <c:choose>
                  <c:when test="${dto.status == 0}"><span class="badge badge-gray">대기</span></c:when>
                  <c:when test="${dto.status == 1}"><span class="badge badge-blue">진행중</span></c:when>
                  <c:when test="${dto.status == 2}"><span class="badge badge-green">완료</span></c:when>
                  <c:when test="${dto.status == 3}"><span class="badge badge-yellow">보류</span></c:when>
                </c:choose>
              </td>
              <td>
                <button class="btn btn-outline btn-sm"
                        onclick="openEditModal(
                          '${dto.planId}',
                          '${dto.itemId}',
                          '${dto.itemName}',
                          ${dto.planQty},
                          '${dto.planSdate}',
                          '${dto.planEdate}',
                          ${dto.status},
                          '${dto.empId}',
                          '${dto.ename}'
                        )">수정</button>
              </td>
            </tr>
          </c:forEach>
          <c:if test="${empty map.list}">
            <tr>
              <td colspan="7" style="text-align:center; padding:32px; color:var(--text-secondary);">
                등록된 생산계획이 없습니다.
              </td>
            </tr>
          </c:if>
        </tbody>
      </table>
    </div>

    <!-- ── 페이지네이션 ───────────────────────────────── -->
    <div class="pagination">

      <%-- 이전 --%>
      <% if (start_section == 1) { %>
        <button class="page-btn" disabled>[이전]</button>
      <% } else { %>
        <a class="page-btn" href="list?page=<%= start_section-1 %>&size=<%= size %>">[이전]</a>
      <% } %>

      <%-- 페이지 번호 --%>
      <c:forEach var="i" begin="<%= start_section %>" end="<%= end_section %>">
        <c:choose>
          <c:when test="${map.page eq i}">
            <button class="page-btn page-btn-active">${i}</button>
          </c:when>
          <c:otherwise>
            <a class="page-btn" href="list?page=${i}&size=<%= size %>">${i}</a>
          </c:otherwise>
        </c:choose>
      </c:forEach>

      <%-- 다음 --%>
      <% if (end_section >= totalPage) { %>
        <button class="page-btn" disabled>[다음]</button>
      <% } else { %>
        <a class="page-btn" href="list?page=<%= end_section+1 %>&size=<%= size %>">[다음]</a>
      <% } %>

    </div>
    <!-- /페이지네이션 -->

  </div>
  <!-- /page-list -->


  <!-- ====================================================
       등록 모달
       ==================================================== -->
  <div id="modalRegister" class="pp-modal-overlay" style="display:none;">
    <div class="pp-modal">
      <div class="pp-modal-header">
        <h2>생산계획 등록</h2>
        <button class="pp-modal-close" onclick="closeRegisterModal()">&#x2715;</button>
      </div>
      <div class="pp-modal-body">
        <form id="registerForm" action="/production/plan/insert" method="post">
          <div class="form-grid">
            <div class="form-group">
              <label class="form-label" for="regProduct">제품 <span class="req">*</span></label>
              <select class="form-control" id="regProduct" name="itemId" required>
                <option value="">제품 선택</option>
                <%-- item 목록은 Controller에서 request attribute로 전달 필요 --%>
                <c:forEach var="item" items="${itemList}">
                  <option value="${item.itemId}">${item.itemName}</option>
                </c:forEach>
              </select>
            </div>
            <div class="form-group">
              <label class="form-label" for="regEmp">담당자 <span class="req">*</span></label>
              <select class="form-control" id="regEmp" name="empId" required>
                <option value="">담당자 선택</option>
                <c:forEach var="emp" items="${empList}">
                  <option value="${emp.empId}">${emp.ename}</option>
                </c:forEach>
              </select>
            </div>
            <div class="form-group">
              <label class="form-label" for="regQty">목표수량 <span class="req">*</span></label>
              <input type="number" class="form-control" id="regQty" name="planQty"
                     placeholder="목표 수량 입력" min="1" required>
            </div>
            <div class="form-group">
              <label class="form-label" for="regStatus">상태 <span class="req">*</span></label>
              <select class="form-control" id="regStatus" name="status" required>
                <option value="0">대기</option>
                <option value="1">진행중</option>
                <option value="2">완료</option>
                <option value="3">보류</option>
              </select>
            </div>
            <div class="form-group">
              <label class="form-label" for="regStartDate">시작일 <span class="req">*</span></label>
              <input type="date" class="form-control" id="regStartDate" name="planSdate" required>
            </div>
            <div class="form-group">
              <label class="form-label" for="regEndDate">종료일 <span class="req">*</span></label>
              <input type="date" class="form-control" id="regEndDate" name="planEdate" required>
            </div>
          </div>
          <div class="form-actions">
            <button type="button" class="btn btn-outline" onclick="closeRegisterModal()">취소</button>
            <button type="submit" class="btn btn-primary">등록</button>
          </div>
        </form>
      </div>
    </div>
  </div>
  <!-- /등록 모달 -->


  <!-- ====================================================
       수정 모달
       ==================================================== -->
  <div id="modalEdit" class="pp-modal-overlay" style="display:none;">
    <div class="pp-modal">
      <div class="pp-modal-header">
        <h2>생산계획 수정</h2>
        <button class="pp-modal-close" onclick="closeEditModal()">&#x2715;</button>
      </div>
      <div class="pp-modal-body">
        <form id="editForm" action="/production/plan/update" method="post">
          <input type="hidden" id="editPlanId" name="planId">
          <div class="form-grid">
            <div class="form-group">
              <label class="form-label" for="editProduct">제품 <span class="req">*</span></label>
              <select class="form-control" id="editProduct" name="itemId" required>
                <option value="">제품 선택</option>
                <c:forEach var="item" items="${itemList}">
                  <option value="${item.itemId}">${item.itemName}</option>
                </c:forEach>
              </select>
            </div>
            <div class="form-group">
              <label class="form-label" for="editEmp">담당자 <span class="req">*</span></label>
              <select class="form-control" id="editEmp" name="empId" required>
                <option value="">담당자 선택</option>
                <c:forEach var="emp" items="${empList}">
                  <option value="${emp.empId}">${emp.ename}</option>
                </c:forEach>
              </select>
            </div>
            <div class="form-group">
              <label class="form-label" for="editQty">목표수량 <span class="req">*</span></label>
              <input type="number" class="form-control" id="editQty" name="planQty"
                     min="1" required>
            </div>
            <div class="form-group">
              <label class="form-label" for="editStatus">상태 <span class="req">*</span></label>
              <select class="form-control" id="editStatus" name="status" required>
                <option value="0">대기</option>
                <option value="1">진행중</option>
                <option value="2">완료</option>
                <option value="3">보류</option>
              </select>
            </div>
            <div class="form-group">
              <label class="form-label" for="editStartDate">시작일 <span class="req">*</span></label>
              <input type="date" class="form-control" id="editStartDate" name="planSdate" required>
            </div>
            <div class="form-group">
              <label class="form-label" for="editEndDate">종료일 <span class="req">*</span></label>
              <input type="date" class="form-control" id="editEndDate" name="planEdate" required>
            </div>
          </div>
          <div class="form-actions">
            <button type="button" class="btn btn-outline" onclick="closeEditModal()">취소</button>
            <button type="submit" class="btn btn-primary">수정</button>
          </div>
        </form>
      </div>
    </div>
  </div>
  <!-- /수정 모달 -->


  <script>
    /* ── size 변경 시 page=1 로 재조회 ──────────────────── */
    document.getElementById("sizeSelect").addEventListener("change", function () {
      location.href = "list?page=1&size=" + this.value;
    });

    /* ── 전체 체크박스 ───────────────────────────────────── */
    document.getElementById("chkAll").addEventListener("change", function () {
      document.querySelectorAll("input[name='chk']")
              .forEach(chk => chk.checked = this.checked);
    });

    /* ── 등록 모달 ───────────────────────────────────────── */
    function openRegisterModal() {
      document.getElementById("modalRegister").style.display = "flex";
    }
    function closeRegisterModal() {
      document.getElementById("registerForm").reset();
      document.getElementById("modalRegister").style.display = "none";
    }

    /* ── 수정 모달 ───────────────────────────────────────── */
    function openEditModal(planId, itemId, itemName, planQty,
                           planSdate, planEdate, status, empId, ename) {
      document.getElementById("editPlanId").value       = planId;
      document.getElementById("editQty").value          = planQty;
      document.getElementById("editStatus").value       = status;
      // Date 형식 yyyy-MM-dd 로 잘라내기 (Oracle Date → toString 앞 10자)
      document.getElementById("editStartDate").value    = String(planSdate).substring(0, 10);
      document.getElementById("editEndDate").value      = String(planEdate).substring(0, 10);

      // select에서 해당 itemId / empId 선택
      setSelectValue("editProduct", itemId);
      setSelectValue("editEmp",     empId);

      document.getElementById("modalEdit").style.display = "flex";
    }
    function closeEditModal() {
      document.getElementById("editForm").reset();
      document.getElementById("modalEdit").style.display = "none";
    }

    /* ── select 값 세팅 헬퍼 ─────────────────────────────── */
    function setSelectValue(selectId, value) {
      const sel = document.getElementById(selectId);
      for (let i = 0; i < sel.options.length; i++) {
        if (sel.options[i].value === String(value)) {
          sel.selectedIndex = i;
          break;
        }
      }
    }

    /* ── 오버레이 클릭 시 닫기 ──────────────────────────── */
    document.querySelectorAll(".pp-modal-overlay").forEach(overlay => {
      overlay.addEventListener("click", function (e) {
        if (e.target === this) {
          this.style.display = "none";
        }
      });
    });
  </script>

        </main>
    </div>
</div>

</body>
</html>