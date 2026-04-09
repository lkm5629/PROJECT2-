<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>재고관리</title>

<link rel="stylesheet" href="/mes/static/css/P00_common/common.css">

<link rel="stylesheet" href="/mes/static/css/P00_layout/header.css">
<script src="/mes/static/js/00_layout/header.js"></script>

<link rel="stylesheet" href="/mes/static/css/P00_layout/snb.css">
<script src="/mes/static/js/00_layout/snb.js"></script>

<link rel="stylesheet" href="/mes/static/css/P05_stock/stock.css">
<script src="/mes/static/js/06_prod/stock.js"></script>
</head>
<body>
	<%-- 공통 헤더 --%>
	<%@ include file="/WEB-INF/views/P00_layout/header.jsp"%>

	<div class="layout_snb">
		<div class="snbContent">
			<%-- 공통 사이드 네비게이션 --%>
			<%@ include file="/WEB-INF/views/P00_layout/snb.jsp"%>
		</div>
		<div class="content">
			<div class="page-wrapper">

				<%-- 페이지 헤더 --%>
				<div class="page-header">
					<h1>입출고 관리</h1>
					<p>입출고 현황을 조회합니다</p>
					<%-- 입출고 등록 모달 오픈 버튼 --%>
					<button class="btn-register">+ 입출고 등록</button>
				</div>

				<%-- 필터 / 검색 영역 --%>
				<div class="filter-bar">
					<%-- 입출고 분류 필터 --%>
					<select>
						<option>입출고 분류</option>
						<option>입고</option>
						<option>출고</option>
					</select>

					<%-- 거래처 필터 --%>
					<select>
						<option>거래처명</option>
						<option>A사</option>
						<option>B사</option>
					</select>

					<%-- 자재 대분류 필터 --%>
					<select>
						<option>자재 대분류</option>
						<option>완제품</option>
						<option>반제품</option>
					</select>

					<%-- 자재 소분류 필터 --%>
					<select>
						<option>자재 소분류</option>
						<option>알콜</option>
						<option>알콜솜</option>
					</select>

					<%-- 작업자 검색 (모달 연동 예정) --%>
					<input type="text" value="작업자 검색 모달">

					<%-- 기간 검색 --%>
					기간: <input type="date" title="시작일">~<input type="date"
						title="종료일">

					<%-- 자재명/자재코드 검색 --%>
					<div class="search-wrap">
						<input type="text" placeholder="자재명 또는 자재코드로 검색" />
						<button class="btn-search">검색</button>
					</div>
				</div>

				<%-- 페이지당 표시 건수 선택 --%>
				<select id="size">
					<option value="5" ${map.size == 5  ? 'selected' : ''}>5</option>
					<option value="10" ${map.size == 10 ? 'selected' : ''}>10</option>
					<option value="15" ${map.size == 15 ? 'selected' : ''}>15</option>
					<option value="20" ${map.size == 20 ? 'selected' : ''}>20</option>
				</select>

				<%-- 테이블 영역 --%>
				<div class="table-wrap">
					<table>
						<thead>
							<tr>
								<th>입출고코드</th>
								<th>자재코드</th>
								<th>자재명</th>
								<th>분류</th>
								<th>LOT</th>
								<th>규격</th>
								<th>단위</th>
								<th>수량</th>
								<th>입출고일</th>
								<th>유통기한</th>
								<th>입출고사유</th>
								<th>거래처</th>
								<th>관리</th>
							</tr>
						</thead>
						<tbody>
							<%-- Controller에서 넘어온 map.list를 forEach로 출력 --%>
							<c:forEach var="dto" items="${map.list}">
								<tr>
									<%-- io 테이블 --%>
									<td>${dto.io_id}</td>
									<%-- item 테이블 --%>
									<td>${dto.item_id}</td>
									<td>${dto.item_name}</td>
									<%-- 자재 대분류 --%>
									<td>${dto.g_id}</td>
									<%-- lot 테이블 --%>
									<td>${dto.lot_id}</td>
									<%-- item 규격/단위 --%>
									<td>${dto.spec}</td>
									<td>${dto.unit}</td>
									<%-- lot 수량 --%>
									<td>${dto.lot_qty}</td>
									<%-- io 입출고일 --%>
									<td>${dto.io_time}</td>
									<%-- lot 유통기한 --%>
									<td>${dto.expiry_date}</td>
									<%-- io 입출고사유 --%>
									<td>${dto.io_reason}</td>
									<%-- vendor 거래처명 --%>
									<td>${dto.vender_name}</td>
									<%-- 수정/삭제 버튼 --%>
									<td>
										<div class="action-btns">
											<button class="btn-edit">수정</button>
											<button class="btn-delete">삭제</button>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<%-- 페이지네이션 계산 --%>
					<%
					Map map = (Map) request.getAttribute("map");
					int total = (int) map.get("totalCount");
					int size = (int) map.get("size");

					// 전체 페이지 수 계산
					int totalPage = (int) Math.ceil((double) total / size);

					int section = 5; // 한번에 보여줄 페이지 번호 수
					int pageNum = (int) map.get("page");

					// 현재 섹션의 끝/시작 페이지 계산
					int end_section = (int) Math.ceil((double) pageNum / section) * section;
					int start_section = end_section - section + 1;

					// 끝 섹션이 전체 페이지를 초과하면 보정
					if (end_section > totalPage) {
						end_section = totalPage;
					}
					%>

					<div class="pagination">
						<%-- 이전 버튼: 첫 섹션이면 링크 없음 --%>
						<c:if test="<%=start_section == 1%>">
							<a>&laquo;</a>
						</c:if>
						<c:if test="<%=start_section != 1%>">
							<a
								href="/mes/stockcontroller?page=<%= start_section-1 %>&size=${map.size}">&laquo;</a>
						</c:if>

						<%-- 페이지 번호 목록 --%>
						<c:forEach var="i" begin="<%=start_section%>"
							end="<%=end_section%>">
							<%-- 현재 페이지는 굵게 표시 --%>
							<c:if test="${map.page eq i}">
								<a href="/mes/stockcontroller?page=${i}&size=${map.size}"
									class="active"><strong>${i}</strong></a>
							</c:if>
							<c:if test="${map.page ne i}">
								<a href="/mes/stockcontroller?page=${i}&size=${map.size}">${i}</a>
							</c:if>
						</c:forEach>

						<%-- 다음 버튼: 마지막 섹션이면 링크 없음 --%>
						<c:if test="<%=end_section == totalPage%>">
							<a>&raquo;</a>
						</c:if>
						<c:if test="<%=end_section != totalPage%>">
							<a
								href="/mes/stockcontroller?page=<%= end_section+1 %>&size=${map.size}">&raquo;</a>
						</c:if>
					</div>
				</div>

				<%-- 입출고 등록 모달 --%>

				<%--
                        규격, 단위는 자재 소분류 선택 시 자동입력 예정
                        작업자는 로그인한 사용자 자동입력 예정
                        입출고일은 기본값 오늘, 수정 가능
                        대분류 선택 시 소분류 동적 로딩 예정
                    --%>
				<dialog id="myModal" class="modal-box">
    <h2 class="modal-title">입출고 등록</h2>
    <form method="post" action="/mes/stockcontroller">
        <div class="modal-grid">

            <div class="modal-field">
                <label>입출고 분류</label>
                <select name="io_type">
                    <option value="">분류 선택</option>
                    <option value="0">입고</option>
                    <option value="1">출고</option>
                </select>
            </div>

            <%-- 거래처 --%>
<div class="modal-field">
    <label>거래처</label>
    <select name="vender_id">
        <option value="">거래처 선택</option>
        <c:forEach var="v" items="${map.vendorList}">
            <option value="${v.vender_id}">${v.vender_name}</option>
        </c:forEach>
    </select>
</div>


            <%-- 자재 대분류 --%>
<div class="modal-field">
    <label>자재 대분류</label>
    <select name="g_id" id="g_id">
        <option value="">대분류 선택</option>
        <c:forEach var="item" items="${map.itemList}">
            <option value="${item.g_id}">${item.g_id}</option>
        </c:forEach>
    </select>
</div>

            <%-- 자재 소분류 --%>
<div class="modal-field">
    <label>자재 소분류</label>
    <select name="item_id" id="item_id">
        <option value="">소분류 선택</option>
        <c:forEach var="item" items="${map.itemList}">
            <option value="${item.item_id}" 
                    data-spec="${item.spec}" 
                    data-unit="${item.unit}">
                ${item.item_name}
            </option>
        </c:forEach>
    </select>
</div>
            <div class="modal-field">
                <label>규격</label>
                <input type="number" name="spec" id="spec" placeholder="자동입력" readonly>
            </div>

            <div class="modal-field">
                <label>단위</label>
                <input type="text" name="unit" id="unit" placeholder="자동입력" readonly>
            </div>

            <div class="modal-field">
                <label>수량</label>
                <input type="number" name="lot_qty" min="1" placeholder="수량 입력">
            </div>

            <div class="modal-field">
                <label>입출고일</label>
                <input type="date" name="io_time" id="io_time">
            </div>

            <div class="modal-field">
                <label>유통기한</label>
                <input type="date" name="expiry_date">
            </div>

            <div class="modal-field">
                <label>입출고사유</label>
                <select name="io_reason">
                    <option value="">입출고사유 선택</option>
                    <option value="purchase">구매</option>
                    <option value="sale">판매</option>
                    <option value="disposal">폐기</option>
                    <option value="return">반품</option>
                </select>
            </div>

            <div class="modal-field">
                <label>LOT 번호</label>
                <input type="text" name="lot_id" placeholder="LOT번호 입력">
            </div>

            <div class="modal-field">
                <label>작업자</label>
                <input type="text" id="empName" placeholder="자동입력" readonly>
                <input type="hidden" name="emp_id" value="user_1001"> <%-- 임시: 로그인 세션으로 대체 예정 --%>
            </div>

            <%-- 하단 버튼 --%>
            <div class="modal-footer">
                <button type="button" class="btn-cancel" id="btnCancel">← 취소</button>
                <button type="submit" class="btn-submit">등록</button>
            </div>

        </div>
    </form>
</dialog>


			</div>
		</div>
	</div>


</body>
</html>