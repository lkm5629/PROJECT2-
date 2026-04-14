<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 상세</title>
<link rel="stylesheet" href="/mes/static/css/P00_common/common.css">
<link rel="stylesheet" href="/mes/static/css/P00_layout/header.css">
<script src="/mes/static/js/00_layout/header.js"></script>
<link rel="stylesheet" href="/mes/static/css/P00_layout/snb.css">
<script src="/mes/static/js/00_layout/snb.js"></script>
<link rel="stylesheet" href="/mes/static/css/P07_work/main.css">
<link rel="stylesheet" href="/mes/static/css/P03_notice/notice.css">
<script src="${ctx}/static/js/P03_notice/noticeDetail.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/P00_layout/header.jsp" %>
<div class="layout_snb">
  <div class="snbContent"><%@ include file="/WEB-INF/views/P00_layout/snb.jsp" %></div>
  <div class="content">
  <main class="nc">

  <c:set var="ctx"  value="${pageContext.request.contextPath}" />

  <div id="page-notice-detail">
    <div class="page-header-row">
      <div><h1>공지사항</h1></div>
      <div class="action-header">
        <button class="btn btn-outline btn-sm"
          onclick="location.href='${ctx}/board/notice/list?page=${page}&size=${size}'">목록</button>
        <%-- ④ auth=3인 경우에만 수정/삭제 버튼 노출 --%>
        <c:if test="${auth == 3}">
          <button class="btn btn-outline btn-sm"
            onclick="location.href='${ctx}/board/notice/edit?boardno=${dto.boardno}'">수정</button>
          <button class="btn btn-danger btn-sm" onclick="submitDelete()">삭제</button>
        </c:if>
      </div>
    </div>

    <div class="card">
      <div class="post-title-row">
        <span class="post-title-text">${dto.title}</span>
      </div>
      <div class="post-meta-grid">
        <div class="post-meta-item">
          <span class="post-meta-label">작성자</span>
          <span class="post-meta-value">${dto.ename}</span>  <%-- ③ ename 표시 --%>
        </div>
        <div class="post-meta-item">
          <span class="post-meta-label">등록일</span>
          <span class="post-meta-value">
            <fmt:formatDate value="${dto.ctime}" pattern="yyyy-MM-dd"/>
          </span>
        </div>
        <div class="post-meta-item">
          <span class="post-meta-label">조회수</span>
          <span class="post-meta-value">${dto.views}</span>
        </div>
      </div>
      <div class="post-body">${dto.content}</div>
    </div>
  </div>

  <form id="deleteForm" action="${ctx}/board/notice/delete" method="post">
    <input type="hidden" name="boardno" value="${dto.boardno}">
  </form>

  </main>
  </div>
</div>


</body>
</html>