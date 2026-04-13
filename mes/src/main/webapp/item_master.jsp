<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="java.util.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>품목 마스터</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/P00_common/common.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/P00_layout/header.css">
<script
	src="${pageContext.request.contextPath}/static/js/00_layout/header.js"></script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/P00_layout/snb.css">
<script
	src="${pageContext.request.contextPath}/static/js/00_layout/snb.js"></script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/P11_masterdata/item_master.css">
</head>

<body>

	<%@ include file="/WEB-INF/views/P00_layout/header.jsp"%>

	<div class="layout_snb">
		<div class="snbContent">
			<%@ include file="/WEB-INF/views/P00_layout/snb.jsp"%>
		</div>

		<div class="content">
			<div class="page-wrapper">
				<div class="page-top">
					<div class="page-header">
						<h1>품목 마스터</h1>
						<p>제품 및 원자재 품목 정보를 관리합니다.</p>
					</div>
					<button type="button" class="btn-add">+ 품목 등록</button>
				</div>

				<div class="summary-cards">
					<div class="summary-box">
						<div class="summary-title">전체 품목</div>
						<div class="summary-value">12</div>
					</div>
					<div class="summary-box">
						<div class="summary-title">완제품</div>
						<div class="summary-value">4</div>
					</div>
					<div class="summary-box">
						<div class="summary-title">반자재</div>
						<div class="summary-value">1</div>
					</div>
					<div class="summary-box">
						<div class="summary-title">원자재</div>
						<div class="summary-value">7</div>
					</div>
				</div>

				<div class="list-section">
					<h2>품목 목록</h2>

					<div class="filter-row">
						<select name="itemGroup">
							<option value="">선택</option>
							<option value="완제품">완제품</option>
							<option value="반자재">반자재</option>
							<option value="원자재">원자재</option>
						</select>

						<div class="search-wrap">
							<input type="text" name="keyword" placeholder="품목코드/품목명 검색..." />
							<button type="button" class="btn-search">검색</button>
						</div>
					</div>

					<div class="table-wrap">
						<table>
							<thead>
								<tr>
									<th>품목코드</th>
									<th>품목그룹</th>
									<th>품목명</th>
									<th>규격</th>
									<th>단위</th>
									<th>관리</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${itemList}">
									<tr>
										<td>${item.item_id}</td>
										<td><c:if test="${item.g_id == 30}">
												완제품
											</c:if> <c:if test="${item.g_id == 20}">
												반제품
											</c:if> <c:if test="${item.g_id == 10}">
												원자재
											</c:if></td>
										<td>${item.item_name}</td>
										<td>${item.spec}</td>
										<td>${item.unit}</td>
										<td>
											<div class="action-btns">
												<button type="button" class="icon-btn edit"
													data-item-id="${item.item_id}"
													data-item-name="${fn:escapeXml(item.item_name)}"
													data-g-id="${item.g_id}" data-spec="${item.spec}"
													data-unit="${item.unit}">수정</button>
												<button type="button" class="icon-btn delete">삭제</button>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

						<div class="pagination">
							<a href="#">&lt;</a> <a href="#" class="active">1</a> <a href="#">2</a>
							<a href="#">3</a> <a href="#">4</a> <a href="#">5</a> <a href="#">&gt;</a>
						</div>
					</div>
				</div>
			</div>
			<div class="edit_item_modal">
				<div class="edit_item_modal_popup">
					<form action="${pageContext.request.contextPath}/itemUpdate"
						method="post">
						<h3 class="edit_item_modal_title">품목마스터 수정</h3>

						<div class="edit_item_form_row">
							<div class="edit_item_form_group code">
								<label>품목코드</label> <input id="edit_item_id" name="item_id"
									class="edit_item_info readonly" type="text" readonly>

							</div>

							<div class="edit_item_form_group group">
								<label>품목 그룹</label> <select id="edit_g_id" name="g_id" class="edit_item_info">

									<option value="30">완제품</option>
									<option value="20">반제품</option>
									<option value="10">원자재</option>
								</select>
							</div>

							<div class="edit_item_form_group small">
								<label>규격</label> <select id="edit_spec" name="spec" class="edit_item_info">

									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
									<option value="20">20</option>
									<option value="30">30</option>
									<option value="40">40</option>
									<option value="50">50</option>
									<option value="60">60</option>
									<option value="70">70</option>
									<option value="80">80</option>
									<option value="90">90</option>
									<option value="100">100</option>
									<option value="1000">1000</option>
								</select>
							</div>

							<div class="edit_item_form_group small">
								<label>단위</label> <select id="edit_unit" name="unit" class="edit_item_info">

									<option value="L">L</option>
									<option value="m">m</option>
									<option value="cm">cm</option>
									<option value="장">장</option>
								</select>
							</div>
						</div>

						<div class="edit_item_form_row second">
							<div class="edit_item_form_group name-group">
								<label>품목명</label>
								<div class="edit_item_name_wrap">
									<input id="edit_item_name" name="item_name" type="text">

									<ul id="ul_li"></ul>
<!-- 									<button type="button" id="edit_item_search_btn">검색</button> -->
								</div>
							</div>
						</div>

						<div class="edit_item_btn_area">
							<button type="button" class="edit_item_close_btn">닫기</button>
							<button type="submit" class="edit_item_save_btn">수정</button>

						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- 등록 모달 -->
	<div class="add_item_modal" id="addItemModal" style="display: none;">
		<div class="add_item_modal_popup">
			<form action="${pageContext.request.contextPath}/itemMasterAdd"
				method="post">
				<h3 class="add_item_modal_title">품목 등록</h3>

				<div class="add_item_form_row">
					<div class="add_item_form_group code">
						<label>품목코드</label> <input type="text" id="add_item_id"
							class="add_item_info" name="item_id" readonly>
					</div>

					<div class="add_item_form_group group">
						<label>품목 그룹</label> <select id="add_g_id" class="add_item_info"
							name="g_id">
							<option value="30">완제품</option>
							<option value="20">반자재</option>
							<option value="10">원자재</option>
						</select>
					</div>

					<div class="add_item_form_group small">
						<label>규격</label> <select id="add_spec" class="add_item_info"
							name="spec">
							<option value="">선택</option>

							<c:forEach var="i" begin="3" end="10">
								<option value="${i}">${i}</option>
							</c:forEach>

							<c:forEach var="i" begin="20" end="100" step="10">
								<option value="${i}">${i}</option>
							</c:forEach>

							<option value="1000">1000</option>
						</select>
					</div>

					<div class="add_item_form_group small">
						<label>단위</label> <select id="add_unit" class="add_item_info"
							name="unit">
							<option value="">선택</option>
							<option value="L">L</option>
							<option value="m">m</option>
							<option value="cm">cm</option>
							<option value="장">장</option>
						</select>
					</div>
				</div>

				<div class="add_item_form_row second">
					<div class="add_item_form_group name-group">
						<label>품목명</label> <input type="text" id="add_item_name"
							name="item_name" class="add_item_info" placeholder="품목명을 입력하세요">
					</div>
				</div>

				<div class="add_item_btn_area">
					<button type="button" class="add_item_close_btn"
						id="cancelAddItemModal">닫기</button>
					<button type="submit" class="add_item_save_btn"
						id="saveAddItemModal">등록</button>
				</div>
			</form>
		</div>
	</div>

	<script>
		window.itemListForCode = [
			<c:forEach var="item" items="${itemList}" varStatus="status">
				{
					itemId: "${fn:escapeXml(item.item_id)}",
					gId: ${item.g_id}
				}<c:if test="${not status.last}">,</c:if>
			</c:forEach>
		];
	</script>

	<script
		src="${pageContext.request.contextPath}/static/js/11_masterdata/item_master.js"></script>
</body>
</html>
