<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 등록</title>
<link rel="stylesheet" href="/mes/static/css/P00_common/common.css">
<link rel="stylesheet" href="/mes/static/css/P00_layout/header.css">
<script src="/mes/static/js/00_layout/header.js"></script>
<link rel="stylesheet" href="/mes/static/css/P00_layout/snb.css">
<script src="/mes/static/js/00_layout/snb.js"></script>
<link rel="stylesheet" href="/mes/static/css/P07_work/main.css">
<link rel="stylesheet" href="/mes/static/css/P03_notice/notice.css">
</head>
<body>
<%@ include file="/WEB-INF/views/P00_layout/header.jsp" %>
<div class="layout_snb">
  <div class="snbContent"><%@ include file="/WEB-INF/views/P00_layout/snb.jsp" %></div>
  <div class="content">
  <main class="nc">

  <c:set var="ctx" value="${pageContext.request.contextPath}" />

  <div id="page-notice-register">
    <div class="page-header">
      <h1>공지사항 등록</h1>
      <p>새로운 공지사항을 등록합니다</p>
    </div>

    <div class="card">
      <div class="card-title">공지 작성</div>
      <%-- action=insert, POST --%>
      <form id="noticeRegisterForm"
            action="${ctx}/notice/insert" method="post"
            enctype="multipart/form-data">

        <div class="form-group">
          <label class="form-label" for="noticeRegTitle">제목 <span class="req">*</span></label>
          <input type="text" class="form-control" id="noticeRegTitle" name="title"
                 placeholder="공지 제목을 입력하세요" required>
        </div>

        <div class="form-group">
          <label class="form-label">내용 <span class="req">*</span></label>
          <div class="editor-toolbar">
            <button type="button" class="editor-btn" title="굵게"><b>B</b></button>
            <button type="button" class="editor-btn" title="기울임"><i>I</i></button>
            <button type="button" class="editor-btn" title="밑줄"><u>U</u></button>
            <div class="editor-sep"></div>
            <button type="button" class="editor-btn">≡</button>
            <button type="button" class="editor-btn">☰</button>
            <button type="button" class="editor-btn">≡</button>
            <div class="editor-sep"></div>
            <button type="button" class="editor-btn">T</button>
            <button type="button" class="editor-btn">A</button>
            <button type="button" class="editor-btn">🔗</button>
            <button type="button" class="editor-btn">🖼</button>
          </div>
          <textarea class="editor-area" id="noticeRegContent" name="content"
                    placeholder="공지 내용을 입력하세요" rows="8"></textarea>
        </div>

        <div class="form-actions">
          <button type="button" class="btn btn-outline"
            onclick="location.href='${ctx}/notice/list'">취소</button>
          <button type="submit" class="btn btn-primary">등록</button>
        </div>
      </form>
    </div>
  </div>

  </main>
  </div>
</div>
</body>
</html>