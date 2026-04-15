<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>건의사항</title>

<link rel="stylesheet" href="/mes/static/css/P00_common/common.css">
<link rel="stylesheet" href="/mes/static/css/P00_layout/header.css">
<script src="/mes/static/js/00_layout/header.js"></script>
<link rel="stylesheet" href="/mes/static/css/P00_layout/snb.css">
<script src="/mes/static/js/00_layout/snb.js"></script>
<link rel="stylesheet" href="/mes/static/css/P03_suggestion/suggestion.css">

</head>
<body>

<%@ include file="/WEB-INF/views/P00_layout/header.jsp" %>

<div class="layout_snb">
    <div class="snbContent">
        <%@ include file="/WEB-INF/views/P00_layout/snb.jsp" %>
    </div>
    <div class="content">
        <main class="sg">

<%
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

  <div id="page-suggest-list">
    <div class="page-header-row">
      <div>
        <h1>건의사항</h1>
        <p class="page-header-desc">건의사항 목록을 조회하고 관리합니다</p>
      </div>
      <button class="btn btn-primary"
              onclick="location.href='${pageContext.request.contextPath}/suggestion/register'">
        <svg width="14" height="14" viewBox="0 0 14 14" fill="none">
          <path d="M7 2v10M2 7h10" stroke="currentColor" stroke-width="1.8" stroke-linecap="round"/>
        </svg>
        건의사항 등록
      </button>
    </div>

    <div class="table-toolbar">
      <div class="search-wrap">
        <svg class="search-icon" width="14" height="14" viewBox="0 0 14 14" fill="none">
          <circle cx="6" cy="6" r="4.5" stroke="#94A3B8" stroke-width="1.4"/>
          <path d="M9.5 9.5L12 12" stroke="#94A3B8" stroke-width="1.4" stroke-linecap="round"/>
        </svg>
        <input type="text" class="search-input" placeholder="제목으로 검색"
               id="searchKeyword" name="searchKeyword">
      </div>
      <button class="btn btn-outline btn-sm toolbar-btn">검색</button>

      <select id="sizeSelect" class="date-input size-select">
        <option value="5"  <%= size==5  ? "selected" : "" %>>5건</option>
        <option value="10" <%= size==10 ? "selected" : "" %>>10건</option>
        <option value="15" <%= size==15 ? "selected" : "" %>>15건</option>
        <option value="20" <%= size==20 ? "selected" : "" %>>20건</option>
      </select>
    </div>

    <div class="table-wrap">
      <table>
        <thead>
          <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
            <th>조회수</th>
            <th>상태</th>
          </tr>
        </thead>
        <tbody id="suggestListBody">
          <c:forEach var="sg" items="${map.list}">
            <tr>
              <td>${fn:substring(sg.boardno, 5, fn:length(sg.boardno))}</td>
              <td>
                <a href="${pageContext.request.contextPath}/suggestion/detail?boardno=${sg.boardno}&page=${map.page}&size=${map.size}"
                   class="item-name-link">
                  ${sg.title}
                </a>
              </td>
              <td>${sg.ename}</td>
              <td><fmt:formatDate value="${sg.ctime}" pattern="yyyy-MM-dd"/></td>
              <td>${sg.views}</td>
              <td>
                <c:choose>
                  <c:when test="${sg.complete == 0}">
                    <span class="badge badge-blue">처리중</span>
                  </c:when>
                  <c:otherwise>
                    <span class="badge badge-gray">처리완료</span>
                  </c:otherwise>
                </c:choose>
              </td>
            </tr>
          </c:forEach>
          <c:if test="${empty map.list}">
            <tr>
              <td colspan="6" class="empty-row">등록된 건의사항이 없습니다.</td>
            </tr>
          </c:if>
        </tbody>
      </table>
    </div>

    <!-- 페이지네이션 -->
    <div class="pagination">
      <% if (pageNum == 1) { %>
        <button class="page-btn" disabled>이전</button>
      <% } else { %>
        <a class="page-btn" href="${pageContext.request.contextPath}/suggestion/list?page=<%= pageNum-1 %>&size=<%= size %>">이전</a>
      <% } %>

      <c:forEach var="i" begin="<%= start_section %>" end="<%= end_section %>">
        <c:choose>
          <c:when test="${map.page eq i}">
            <button class="page-btn page-btn-active">${i}</button>
          </c:when>
          <c:otherwise>
            <a class="page-btn" href="${pageContext.request.contextPath}/suggestion/list?page=${i}&size=<%= size %>">${i}</a>
          </c:otherwise>
        </c:choose>
      </c:forEach>

      <% if (pageNum >= totalPage) { %>
        <button class="page-btn" disabled>다음</button>
      <% } else { %>
        <a class="page-btn" href="${pageContext.request.contextPath}/suggestion/list?page=<%= pageNum+1 %>&size=<%= size %>">다음</a>
      <% } %>
    </div>

  </div>

  <script>
    document.getElementById("sizeSelect").addEventListener("change", function () {
      location.href = "${pageContext.request.contextPath}/suggestion/list?page=1&size=" + this.value;
    });
  </script>

        </main>
    </div>
</div>

</body>
</html>