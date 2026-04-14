<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    /* ── 페이지네이션 계산 ── */
    Map map         = (Map) request.getAttribute("map");
    int total       = (int) map.get("totalCount");
    int size        = (int) map.get("size");
    int pageNum     = (int) map.get("page");

    int totalPage   = (int) Math.ceil((double) total / size);
    if (totalPage < 1) totalPage = 1;

    int section       = 5;
    int end_section   = (int) Math.ceil((double) pageNum / section) * section;
    int start_section = end_section - section + 1;

    if (end_section > totalPage) end_section = totalPage;
%>

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

    <div id="page-suggest-list">
        <div class="page-header-row">
            <div>
                <h1>건의사항</h1>
                <p>건의사항을 등록하고 확인하세요</p>
            </div>
            <button class="btn btn-primary btn-sm"
                    onclick="location.href='${pageContext.request.contextPath}/suggestion/register'">
                + 건의사항 등록
            </button>
        </div>

        <!-- 툴바 : 페이지 사이즈 선택 -->
        <div class="table-toolbar">
            <select id="sizeSelect" class="form-control" style="width:80px; height:34px;">
                <option value="5"  <c:if test="${map.size == 5}">selected</c:if>>5</option>
                <option value="10" <c:if test="${map.size == 10}">selected</c:if>>10</option>
                <option value="15" <c:if test="${map.size == 15}">selected</c:if>>15</option>
                <option value="20" <c:if test="${map.size == 20}">selected</c:if>>20</option>
            </select>

            <div class="search-wrap">
                <svg class="search-icon" width="14" height="14" viewBox="0 0 14 14" fill="none">
                    <circle cx="6" cy="6" r="4.5" stroke="#94A3B8" stroke-width="1.4"/>
                    <path d="M9.5 9.5L12 12" stroke="#94A3B8" stroke-width="1.4" stroke-linecap="round"/>
                </svg>
                <input type="text" class="search-input" placeholder="검색" id="suggestKeyword">
            </div>
        </div>

        <!-- 테이블 -->
        <div class="table-wrap">
            <table>
                <thead>
                    <tr>
                        <th class="num-col">번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>등록일</th>
                        <th>조회수</th>
                        <th>상태</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${map.list}">
                        <tr onclick="location.href='${pageContext.request.contextPath}/suggestion/detail?boardno=${dto.boardno}&page=${map.page}&size=${map.size}'">
                            <td class="center">${dto.boardno}</td>
                            <td>${dto.title}</td>
                            <td>${dto.empId}</td>
                            <td><fmt:formatDate value="${dto.ctime}" pattern="yyyy-MM-dd"/></td>
                            <td class="center">${dto.views}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${dto.complete == 0}">
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
                            <td colspan="6" class="center" style="padding:32px; color:#94A3B8;">
                                등록된 건의사항이 없습니다.
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>

        <!-- 페이지네이션 -->
        <div class="pagination">

            <%-- 이전 버튼 --%>
            <c:if test="<%= start_section == 1 %>">
                <button class="page-btn disabled">&lt;</button>
            </c:if>
            <c:if test="<%= start_section != 1 %>">
                <button class="page-btn"
                        onclick="goPage(<%= start_section - 1 %>, ${map.size})">&lt;</button>
            </c:if>

            <%-- 페이지 번호 --%>
            <c:forEach var="i" begin="<%= start_section %>" end="<%= end_section %>">
                <c:choose>
                    <c:when test="${map.page == i}">
                        <button class="page-btn active">${i}</button>
                    </c:when>
                    <c:otherwise>
                        <button class="page-btn" onclick="goPage(${i}, ${map.size})">${i}</button>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <%-- 다음 버튼 --%>
            <c:if test="<%= end_section == totalPage %>">
                <button class="page-btn disabled">&gt;</button>
            </c:if>
            <c:if test="<%= end_section != totalPage %>">
                <button class="page-btn"
                        onclick="goPage(<%= end_section + 1 %>, ${map.size})">&gt;</button>
            </c:if>

        </div>
    </div>

    </main>
    </div>
</div>

<script>
    function goPage(page, size) {
        location.href = '${pageContext.request.contextPath}/suggestion/list?page=' + page + '&size=' + size;
    }

    document.querySelector('#sizeSelect').addEventListener('change', function () {
        goPage(1, this.value);
    });
</script>

</body>
</html>