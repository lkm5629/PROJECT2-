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
<title>작업지시</title>

<link rel="stylesheet" href="/mes/static/css/P00_common/common.css">

<link rel="stylesheet" href="/mes/static/css/P00_layout/header.css">
<script src="/mes/static/js/00_layout/header.js"></script>

<link rel="stylesheet" href="/mes/static/css/P00_layout/snb.css">
<script src="/mes/static/js/00_layout/snb.js"></script>
    
<link rel="stylesheet" href="/mes/static/css/P07_work/main.css">
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
 
  <!-- ==================================================
       ① 건의사항 목록
       ================================================== -->
  <div id="page-suggest-list">
    <div class="page-header-row">
      <div>
        <h1>건의사항</h1>
        <p>건의사항을 등록하고 확인하세요</p>
      </div>
      <button class="btn btn-primary btn-sm">+ 건의사항 등록</button>
    </div>
 
    <div class="table-toolbar">
      <div class="search-wrap">
        <svg class="search-icon" width="14" height="14" viewBox="0 0 14 14" fill="none">
          <circle cx="6" cy="6" r="4.5" stroke="#94A3B8" stroke-width="1.4"/>
          <path d="M9.5 9.5L12 12" stroke="#94A3B8" stroke-width="1.4" stroke-linecap="round"/>
        </svg>
        <input type="text" class="search-input" placeholder="검색"
               id="suggestKeyword" name="keyword">
      </div>
    </div>
 
    <div class="table-wrap">
      <table>
        <thead>
          <tr>
            <th class="num-col">번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>부서</th>
            <th>등록일</th>
            <th>상태</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="center">1</td>
            <td>제목1</td>
            <td>박성민1</td>
            <td>부서1</td>
            <td>등록일1</td>
            <td><span class="badge badge-blue">검토중</span></td>
          </tr>
          <tr>
            <td class="center">2</td>
            <td>제목2</td>
            <td>이정규2</td>
            <td>부서2</td>
            <td>등록일2</td>
            <td><span class="badge badge-blue">교육처리</span></td>
          </tr>
          <tr>
            <td class="center">3</td>
            <td>제목3</td>
            <td>박성자3</td>
            <td>부서3</td>
            <td>등록일3</td>
            <td><span class="badge badge-gray">등록완료</span></td>
          </tr>
        </tbody>
      </table>
    </div>
 
    <div class="pagination">
      <button class="page-btn disabled">&lt;</button>
      <button class="page-btn active">1</button>
      <button class="page-btn">2</button>
      <button class="page-btn">3</button>
      <button class="page-btn">&gt;</button>
    </div>
  </div>
  <!-- /page-suggest-list -->
 
 
  <!-- ==================================================
       ② 건의사항 등록
       ================================================== -->
  <div id="page-suggest-register">
    <div class="page-header">
      <h1>건의사항 등록</h1>
      <p>새로운 건의사항을 등록합니다</p>
    </div>
 
    <div class="card">
      <div class="card-title">건의사항 작성</div>
      <form id="suggestRegisterForm" action="/board/suggest/insert" method="post">
 
        <div class="form-group">
          <label class="form-label" for="suggestRegTitle">제목 <span class="req">*</span></label>
          <input type="text" class="form-control" id="suggestRegTitle" name="title"
                 placeholder="건의사항 제목을 입력하세요" required>
        </div>
 
        <div class="form-group">
          <label class="form-label" for="suggestRegContent">내용 <span class="req">*</span></label>
          <textarea class="form-control" id="suggestRegContent" name="content"
                    placeholder="건의사항 내용을 입력하세요" rows="7" required></textarea>
        </div>
 
        <div class="form-actions">
          <button type="button" class="btn btn-outline">취소</button>
          <button type="submit" class="btn btn-primary">등록</button>
        </div>
      </form>
    </div>
  </div>
  <!-- /page-suggest-register -->
 
 
  <!-- ==================================================
       ③ 건의사항 상세
       ================================================== -->
  <div id="page-suggest-detail">
    <div class="page-header-row">
      <div>
        <h1>건의사항 상세</h1>
        <p>건의사항 내용을 확인합니다</p>
      </div>
      <div class="action-header">
        <button class="btn btn-outline btn-sm">목록</button>
        <button class="btn btn-danger btn-sm">삭제</button>
      </div>
    </div>
 
    <!-- 건의사항 정보 -->
    <div class="card">
      <div class="card-title">건의사항 정보</div>
 
      <div class="form-row">
        <div class="form-group" style="margin-bottom:0;">
          <label class="form-label">번호</label>
          <input type="text" class="form-control" value="1" readonly>
        </div>
        <div class="form-group" style="margin-bottom:0;">
          <label class="form-label">작성일</label>
          <input type="text" class="form-control" value="2024-01-15" readonly>
        </div>
        <div class="form-group" style="margin-bottom:0;">
          <label class="form-label">부서</label>
          <input type="text" class="form-control" value="생산1팀" readonly>
        </div>
      </div>
 
      <div class="form-row" style="margin-top:4px;">
        <div class="form-group" style="margin-bottom:0;">
          <label class="form-label">목표일</label>
          <input type="text" class="form-control" value="2024-01-20" readonly>
        </div>
        <div class="form-group" style="margin-bottom:0;">
          <label class="form-label">상태</label>
          <div style="display:flex; align-items:center; height:40px;">
            <span class="badge badge-blue">검토중</span>
          </div>
        </div>
        <div></div>
      </div>
 
      <div class="form-group" style="margin-top:4px;">
        <label class="form-label">제목</label>
        <input type="text" class="form-control" id="suggestDetailTitle"
               value="건의사항 제목이 표시됩니다" readonly>
      </div>
 
      <div class="form-group">
        <label class="form-label">내용</label>
        <textarea class="form-control" id="suggestDetailContent"
                  rows="5" readonly>건의사항 내용이 여기에 표시됩니다.</textarea>
      </div>
    </div>
 
    <!-- 답변 -->
    <div class="card">
      <div class="comment-section">
        <div class="comment-section-title">
          답변 <span class="comment-count">3건</span>
        </div>
 
        <div class="comment-item">
          <div class="comment-header">
            <div class="comment-avatar">한</div>
            <span class="comment-author">한철수</span>
            <span class="comment-date">2024.01.15 08:32</span>
          </div>
          <div class="comment-body">답글</div>
        </div>
 
        <div class="comment-item">
          <div class="comment-reply">
            <div class="comment-header">
              <div class="comment-avatar admin">이</div>
              <span class="comment-author">이명희</span>
              <span class="comment-date">2024.01.15 10:45</span>
              <span class="badge badge-green"
                    style="margin-left:4px; height:18px; font-size:10px;">담당</span>
            </div>
            <div class="comment-body" style="padding-left:36px;">답글</div>
          </div>
        </div>
 
        <div class="comment-item">
          <div class="comment-header">
            <div class="comment-avatar">박</div>
            <span class="comment-author">박민수</span>
            <span class="comment-date">2024.01.16 14:20</span>
          </div>
          <div class="comment-body">답글</div>
        </div>
 
        <div class="comment-input-row">
          <input type="text" class="comment-input"
                 placeholder="답글을 입력하세요" id="commentInput" name="comment">
          <button type="button" class="btn btn-primary btn-sm"
                  style="height:40px; padding:0 16px;">등록</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /page-suggest-detail -->
 
</main>
            <!-- 작업공간 -->
        </div>
    </div>
    
</body>
</html>