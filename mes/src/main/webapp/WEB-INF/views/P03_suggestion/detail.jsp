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
<style>
    /* 댓글 계층 스타일 */
    .comment-item {
        padding: 10px 0;
        border-bottom: 1px solid #f0f0f0;
    }
    .comment-item:last-child {
        border-bottom: none;
    }
    .comment-meta {
        display: flex;
        align-items: center;
        gap: 8px;
        margin-bottom: 4px;
    }
    .reply-icon {
        color: #888;
        font-size: 13px;
        margin-right: 2px;
    }
    .comment-content-text {
        font-size: 14px;
        color: #333;
    }
    .comment-time {
        font-size: 12px;
        color: #aaa;
    }
    .btn-reply-small {
        font-size: 12px;
        color: #5b8dee;
        background: none;
        border: none;
        cursor: pointer;
        padding: 2px 6px;
    }
    .btn-reply-small:hover {
        text-decoration: underline;
    }
    /* 답글 입력창 */
    .reply-input-box {
        display: flex;
        gap: 8px;
        align-items: center;
        margin: 6px 0 6px 0;
        padding: 8px;
        background: #f8f9fa;
        border-radius: 6px;
    }
    .reply-input-box input {
        flex: 1;
        padding: 6px 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
        font-size: 13px;
    }
    .reply-input-box .btn-cancel {
        font-size: 12px;
        color: #888;
        background: none;
        border: none;
        cursor: pointer;
    }
</style>
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
                <c:if test="${detail.complete == 0}">
                    <button class="btn btn-success btn-sm" id="btnComplete">답변완료</button>
                </c:if>
            </div>
        </div>

        <!-- 건의사항 정보 -->
        <div class="card">
            <div class="card-title">건의사항 정보</div>

            <div class="form-row">
                <div class="form-group form-group-no-mb">
                    <label class="form-label">번호</label>
                    <input type="text" class="form-control"
                           value="${fn:substring(detail.boardno, 5, fn:length(detail.boardno))}"
                           readonly>
                </div>
                <div class="form-group form-group-no-mb">
                    <label class="form-label">작성일</label>
                    <input type="text" class="form-control"
                           value="<fmt:formatDate value='${detail.ctime}' pattern='yyyy-MM-dd'/>"
                           readonly>
                </div>
                <div class="form-group form-group-no-mb">
                    <label class="form-label">최종수정일</label>
                    <input type="text" class="form-control"
                           value="<fmt:formatDate value='${detail.mtime}' pattern='yyyy-MM-dd'/>"
                           readonly>
                </div>
            </div>

            <div class="form-row form-row-mt">
                <div class="form-group form-group-no-mb">
                    <label class="form-label">작성자</label>
                    <input type="text" class="form-control" value="${detail.ename}" readonly>
                </div>
                <div class="form-group form-group-no-mb">
                    <label class="form-label">조회수</label>
                    <input type="text" class="form-control" value="${detail.views}" readonly>
                </div>
                <div class="form-group form-group-no-mb">
                    <label class="form-label">상태</label>
                    <div class="badge-cell">
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

            <div class="form-group form-group-mt">
                <label class="form-label">제목</label>
                <input type="text" class="form-control" value="${detail.title}" readonly>
            </div>

            <div class="form-group">
                <label class="form-label">내용</label>
                <textarea class="form-control" rows="6" readonly>${detail.content}</textarea>
            </div>
        </div>

        <!-- ===== 답변 (무한 대댓글) ===== -->
        <div class="card">
            <div class="comment-section">
                <div class="comment-section-title">
                    답변 <span class="comment-count">${fn:length(commentList)}건</span>
                </div>

                <!-- 댓글 목록: CONNECT BY가 이미 계층 순서로 정렬해서 내려줌 -->
                <div id="commentList">
                    <c:forEach var="c" items="${commentList}">
                        <div class="comment-item"
                             data-comno="${c.comno}"
                             data-depth="${c.depth}"
                             style="padding-left: ${c.depth * 30}px;">

                            <div class="comment-meta">
                                <!-- depth > 0 이면 대댓글 화살표 표시 -->
                                <c:if test="${c.depth > 0}">
                                    <span class="reply-icon">↳</span>
                                </c:if>
                                <span class="comment-content-text">${c.content}</span>
                                <span class="comment-time">${c.ctime}</span>
                                <button class="btn-reply-small"
                                        data-comno="${c.comno}"
                                        data-depth="${c.depth}">
                                    답글
                                </button>
                            </div>

                        </div>
                    </c:forEach>
                </div>

                <!-- 원댓글 입력 폼 (대댓글 클릭 시 JS로 이동) -->
                <form id="commentForm" method="post"
                      action="${pageContext.request.contextPath}/suggestion/comment">
                    <input type="hidden" name="boardno"     value="${detail.boardno}">
                    <input type="hidden" name="parentComno" id="parentComno" value="">
                    <div class="comment-input-row">
                        <input type="text" class="comment-input"
                               name="commentContent"
                               placeholder="답글을 입력하세요"
                               id="commentInput">
                        <button type="submit" class="btn btn-primary comment-submit-btn">등록</button>
                    </div>
                </form>

            </div>
        </div>
    </div>

    </main>
    </div>
</div>

<script>
    /* ── 삭제 ── */
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

    /* ── 답변완료 ── */
    const btnComplete = document.querySelector('#btnComplete');
    if (btnComplete) {
        btnComplete.addEventListener('click', function () {
            if (!confirm('답변완료로 처리하시겠습니까?')) return;

            const form = document.createElement('form');
            form.method = 'post';
            form.action = '${pageContext.request.contextPath}/suggestion/detail';

            const inputBoardno = document.createElement('input');
            inputBoardno.type  = 'hidden';
            inputBoardno.name  = 'boardno';
            inputBoardno.value = '${detail.boardno}';

            const inputAction = document.createElement('input');
            inputAction.type  = 'hidden';
            inputAction.name  = 'action';
            inputAction.value = 'complete';

            form.appendChild(inputBoardno);
            form.appendChild(inputAction);
            document.body.appendChild(form);
            form.submit();
        });
    }

    /* ── 무한 대댓글: 답글 버튼 클릭 ── */
    document.addEventListener('click', function (e) {
        if (!e.target.classList.contains('btn-reply-small')) return;

        const comno = e.target.dataset.comno;
        const depth = parseInt(e.target.dataset.depth);

        // parentComno hidden에 클릭한 댓글의 comno 세팅
        document.getElementById('parentComno').value = comno;

        // 입력 폼을 해당 댓글 아래로 이동 + 들여쓰기
        const form = document.getElementById('commentForm');
        form.style.paddingLeft = (depth + 1) * 30 + 'px';

        const targetItem = e.target.closest('.comment-item');
        targetItem.after(form);

        document.getElementById('commentInput').placeholder = '답글을 입력하세요';
        document.getElementById('commentInput').focus();
    });

    /* ── 원댓글 입력 시 parentComno 초기화 ── */
    // 댓글 목록 마지막으로 폼 복귀 버튼 (선택적으로 추가 가능)
    document.getElementById('commentInput').addEventListener('focus', function () {
        // 직접 입력창 클릭 시 parentComno 유지 (답글 버튼으로 세팅된 것 그대로 사용)
    });
</script>

</body>
</html>