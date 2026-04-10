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
<link rel="stylesheet" href="/mes/static/css/P03_notice/notice.css">

</head>
<body>

    <%@ include file="/WEB-INF/views/P00_layout/header.jsp" %>
    
    <div class="layout_snb">
        <div class="snbContent">
            <%@ include file="/WEB-INF/views/P00_layout/snb.jsp" %>
        </div>
        <div class="content">
        
        <main class="nc">
 
  <!-- ==================================================
       ① 공지사항 목록
       ================================================== -->
  <div id="page-notice-list">
    <div class="page-header-row">
      <div>
        <h1>공지사항</h1>
        <p>공장 운영 관련 공지사항을 확인하세요</p>
      </div>
      <button class="btn btn-primary btn-sm">+ 공지사항 등록</button>
    </div>
 
    <div class="table-toolbar">
      <div class="search-wrap">
        <svg class="search-icon" width="14" height="14" viewBox="0 0 14 14" fill="none">
          <circle cx="6" cy="6" r="4.5" stroke="#94A3B8" stroke-width="1.4"/>
          <path d="M9.5 9.5L12 12" stroke="#94A3B8" stroke-width="1.4" stroke-linecap="round"/>
        </svg>
        <input type="text" class="search-input" placeholder="검색"
               id="noticeKeyword" name="keyword">
      </div>
    </div>
 
    <div class="table-wrap">
      <table>
        <thead>
          <tr>
            <th class="num-col">번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>등록일</th>
            <th>조회수</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="center">1</td>
            <td>제목1</td>
            <td>박성자</td>
            <td>등록일1</td>
            <td>조회수1</td>
          </tr>
          <tr>
            <td class="center">2</td>
            <td>제목2</td>
            <td>이유림</td>
            <td>등록일2</td>
            <td>조회수2</td>
          </tr>
          <tr>
            <td class="center">3</td>
            <td>제목3</td>
            <td>박성자</td>
            <td>등록일3</td>
            <td>조회수3</td>
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
  <!-- /page-notice-list -->
 
 
  <!-- ==================================================
       ② 공지사항 등록
       ================================================== -->
  <div id="page-notice-register">
    <div class="page-header">
      <h1>공지사항 등록</h1>
      <p>새로운 공지사항을 등록합니다</p>
    </div>
 
    <div class="card">
      <div class="card-title">공지 작성</div>
      <form id="noticeRegisterForm" action="/board/notice/insert" method="post"
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
 
        <div class="form-group" style="margin-top:16px;">
          <label class="form-label">첨부</label>
          <table class="file-table">
            <tbody id="noticeRegFileList">
              <tr>
                <td>
                  <span style="color:var(--nc-text-muted); font-size:13px;">파일명1</span>
                </td>
                <td style="width:60px; text-align:right;">
                  <span style="color:var(--nc-text-muted); font-size:12px;">크기</span>
                </td>
                <td style="width:32px; text-align:right;">
                  <button type="button" class="file-remove">✕</button>
                </td>
              </tr>
              <tr>
                <td>
                  <span style="color:var(--nc-text-muted); font-size:13px;">파일명2</span>
                </td>
                <td style="width:60px; text-align:right;">
                  <span style="color:var(--nc-text-muted); font-size:12px;">크기</span>
                </td>
                <td style="width:32px; text-align:right;">
                  <button type="button" class="file-remove">✕</button>
                </td>
              </tr>
            </tbody>
          </table>
          <button type="button" class="file-add-btn">
            <svg width="12" height="12" viewBox="0 0 12 12" fill="none">
              <path d="M6 2v8M2 6h8" stroke="currentColor" stroke-width="1.4" stroke-linecap="round"/>
            </svg>
            파일 추가
          </button>
          <input type="file" id="noticeRegFileInput" name="files" multiple style="display:none;">
        </div>
 
        <div class="form-actions">
          <button type="button" class="btn btn-outline">취소</button>
          <button type="submit" class="btn btn-primary">등록</button>
        </div>
      </form>
    </div>
  </div>
  <!-- /page-notice-register -->
 
 
  <!-- ==================================================
       ③ 공지사항 수정
       ================================================== -->
  <div id="page-notice-edit">
    <div class="page-header">
      <h1>공지사항 수정</h1>
      <p>공지사항을 수정합니다</p>
    </div>
 
    <div class="card">
      <div class="card-title">공지 정보</div>
      <div class="form-row">
        <div class="form-group" style="margin-bottom:0;">
          <label class="form-label">번호</label>
          <input type="text" class="form-control" value="1" readonly>
        </div>
        <div class="form-group" style="margin-bottom:0;">
          <label class="form-label">작성자</label>
          <input type="text" class="form-control" value="박성자" readonly>
        </div>
        <div class="form-group" style="margin-bottom:0;">
          <label class="form-label">등록일</label>
          <input type="text" class="form-control" value="2025-01-06" readonly>
        </div>
        <div class="form-group" style="margin-bottom:0;">
          <label class="form-label">조회수</label>
          <input type="text" class="form-control" value="128" readonly>
        </div>
      </div>
    </div>
 
    <div class="card">
      <div class="card-title">공지 작성</div>
      <form id="noticeEditForm" action="/board/notice/update" method="post">
        <input type="hidden" name="noticeId" value="1">
 
        <div class="form-group">
          <label class="form-label" for="editNoticeTitle">제목</label>
          <input type="text" class="form-control" id="editNoticeTitle" name="title"
                 placeholder="공지 제목을 입력하세요" value="기존 제목">
        </div>
 
        <div class="form-group">
          <label class="form-label">내용</label>
          <div class="editor-toolbar">
            <button type="button" class="editor-btn"><b>B</b></button>
            <button type="button" class="editor-btn"><i>I</i></button>
            <button type="button" class="editor-btn"><u>U</u></button>
            <div class="editor-sep"></div>
            <button type="button" class="editor-btn">≡</button>
            <button type="button" class="editor-btn">☰</button>
            <button type="button" class="editor-btn">≡</button>
            <div class="editor-sep"></div>
            <button type="button" class="editor-btn">T</button>
            <button type="button" class="editor-btn">A</button>
          </div>
          <textarea class="editor-area" name="content" rows="8">기존 내용이 표시됩니다.</textarea>
        </div>
 
        <div class="form-actions">
          <button type="button" class="btn btn-outline">취소</button>
          <button type="submit" class="btn btn-primary">수정</button>
        </div>
      </form>
    </div>
  </div>
  <!-- /page-notice-edit -->
 
 
  <!-- ==================================================
       ④ 공지사항 상세
       ================================================== -->
  <div id="page-notice-detail">
    <div class="page-header-row">
      <div>
        <h1>공지사항</h1>
      </div>
      <div class="action-header">
        <button class="btn btn-outline btn-sm">목록</button>
        <button class="btn btn-outline btn-sm">수정</button>
        <button class="btn btn-danger btn-sm">삭제</button>
      </div>
    </div>
 
    <div class="card">
      <div class="post-title-row">
        <span class="post-title-text" id="noticeDetailTitle">공지사항 제목이 표시됩니다</span>
      </div>
 
      <div class="post-meta-grid">
        <div class="post-meta-item">
          <span class="post-meta-label">작성자</span>
          <span class="post-meta-value" id="noticeDetailWriter">박성자</span>
        </div>
        <div class="post-meta-item">
          <span class="post-meta-label">등록일</span>
          <span class="post-meta-value" id="noticeDetailDate">2025-01-06</span>
        </div>
        <div class="post-meta-item">
          <span class="post-meta-label">조회수</span>
          <span class="post-meta-value" id="noticeDetailViews">128</span>
        </div>
      </div>
 
      <div class="post-body" id="noticeDetailContent">
        공지사항 내용이 여기에 표시됩니다.
      </div>
 
      <div class="attach-section">
        <span class="attach-label">첨부파일</span>
        <a class="attach-file">
          <svg width="12" height="12" viewBox="0 0 12 12" fill="none">
            <path d="M6 2v6M3 6l3 3 3-3" stroke="currentColor" stroke-width="1.4"
                  stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          첨부파일명.pdf
        </a>
      </div>
    </div>
  </div>
  <!-- /page-notice-detail -->
 
</main>
            <!-- 작업공간 -->
        </div>
    </div>
    
</body>
</html>