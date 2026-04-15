<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>건의사항 상세</title>
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

    <div id="page-suggest-detail">
        <div class="page-header-row">
            <div>
                <h1>건의사항 상세</h1>
                <p>건의사항 내용을 확인합니다</p>
            </div>
            <div class="action-header">
                <button class="btn btn-outline btn-sm"
                        onclick="location.href='${pageContext.request.contextPath}/suggestion/list?page=${page}&size=${size}'">
                    목록
                </button>
                <button class="btn btn-danger btn-sm" id="btnDelete">삭제</button>
            </div>
        </div>

        <!-- 건의사항 정보 -->
        <div class="card">
            <div class="card-title">건의사항 정보</div>

            <div class="form-row">
                <div class="form-group" style="margin-bottom:0;">
                    <label class="form-label">번호</label>
                    <input type="text" class="form-control" value="${detail.boardno}" readonly>
                </div>
                <div class="form-group" style="margin-bottom:0;">
                    <label class="form-label">작성일</label>
                    <input type="text" class="form-control"
                           value="<fmt:formatDate value='${detail.ctime}' pattern='yyyy-MM-dd'/>"
                           readonly>
                </div>
                <div class="form-group" style="margin-bottom:0;">
                    <label class="form-label">최종수정일</label>
                    <input type="text" class="form-control"
                           value="<fmt:formatDate value='${detail.mtime}' pattern='yyyy-MM-dd'/>"
                           readonly>
                </div>
            </div>

            <div class="form-row" style="margin-top:4px;">
                <div class="form-group" style="margin-bottom:0;">
                    <label class="form-label">작성자</label>
                    <input type="text" class="form-control" value="${detail.empId}" readonly>
                </div>
                <div class="form-group" style="margin-bottom:0;">
                    <label class="form-label">조회수</label>
                    <input type="text" class="form-control" value="${detail.views}" readonly>
                </div>
                <div class="form-group" style="margin-bottom:0;">
                    <label class="form-label">상태</label>
                    <div style="display:flex; align-items:center; height:40px;">
                        <c:choose>
                            <c:when test="${detail.complete == 0}">
                                <span class="badge badge-blue">처리중</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-gray">처리완료</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>

            <div class="form-group" style="margin-top:4px;">
                <label class="form-label">제목</label>
                <input type="text" class="form-control" value="${detail.title}" readonly>
            </div>

            <div class="form-group">
                <label class="form-label">내용</label>
                <textarea class="form-control" rows="6" readonly>${detail.content}</textarea>
            </div>
        </div>

        <!-- 답변 (댓글) — 추후 구현 -->
        <div class="card">
            <div class="comment-section">
                <div class="comment-section-title">
                    답변 <span class="comment-count">0건</span>
                </div>
                <div class="comment-input-row">
                    <input type="text" class="comment-input"
                           placeholder="답글을 입력하세요" id="commentInput">
                    <button type="button" class="btn btn-primary btn-sm"
                            style="height:40px; padding:0 16px;"
                            id="btnCommentInsert">등록</button>
                </div>
            </div>
        </div>
    </div>

    </main>
    </div>
</div>

<script>
    // ── 삭제
    document.querySelector('#btnDelete').addEventListener('click', function () {
        if (!confirm('정말 삭제하시겠습니까?')) return;

        const form = document.createElement('form');
        form.method = 'post';
        form.action = '${pageContext.request.contextPath}/suggestion/delete';

        const input = document.createElement('input');
        input.type  = 'hidden';
        input.name  = 'boardno';
        input.value = '${detail.boardno}';

        form.appendChild(input);
        document.body.appendChild(form);
        form.submit();
    });
</script>

</body>
</html>