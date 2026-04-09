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
						<div class="summary-value">8</div>
					</div>
				</div>

				<div class="list-section">
					<h2>품목 목록</h2>

					<div class="filter-row">
						<select name="itemGroup">
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
									<th>용액</th>
									<th>단위</th>
									<th>관리</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>V001</td>
									<td>완제품</td>
									<td>알콜솝 소(3X3) - 에탄올</td>
									<td><span class="badge">3×3</span></td>
									<td>에탄올</td>
									<td>box</td>
									<td>
										<div class="action-btns">
											<button type="button" class="icon-btn edit">✏</button>
											<button type="button" class="icon-btn delete">🗑</button>
										</div>
									</td>
								</tr>
								<tr>
									<td>V002</td>
									<td>완제품</td>
									<td>알콜솝 소(3X3) - 이소프로판올</td>
									<td><span class="badge">3×3</span></td>
									<td>이소프로판올</td>
									<td>box</td>
									<td>
										<div class="action-btns">
											<button type="button" class="icon-btn edit">✏</button>
											<button type="button" class="icon-btn delete">🗑</button>
										</div>
									</td>
								</tr>
								<tr>
									<td>V003</td>
									<td>완제품</td>
									<td>알콜솝 소(5X5) - 에탄올</td>
									<td><span class="badge">3×3</span></td>
									<td>에탄올</td>
									<td>box</td>
									<td>
										<div class="action-btns">
											<button type="button" class="icon-btn edit">✏</button>
											<button type="button" class="icon-btn delete">🗑</button>
										</div>
									</td>
								</tr>
								<tr>
									<td>V004</td>
									<td>완제품</td>
									<td>알콜솝 대(5X5) - 에탄올</td>
									<td><span class="badge large">5×5</span></td>
									<td>에탄올</td>
									<td>box</td>
									<td>
										<div class="action-btns">
											<button type="button" class="icon-btn edit">✏</button>
											<button type="button" class="icon-btn delete">🗑</button>
										</div>
									</td>
								</tr>
								<tr>
									<td>V005</td>
									<td>완제품</td>
									<td>알콜솝 대(5X5) - 이소프로판올</td>
									<td><span class="badge large">5×5</span></td>
									<td>이소프로판올</td>
									<td>box</td>
									<td>
										<div class="action-btns">
											<button type="button" class="icon-btn edit">✏</button>
											<button type="button" class="icon-btn delete">🗑</button>
										</div>
									</td>
								</tr>
							</tbody>
						</table>

						<div class="pagination">
							<a href="#">&lt;</a>
							<a href="#" class="active">1</a>
							<a href="#">2</a>
							<a href="#">3</a>
							<a href="#">4</a>
							<a href="#">5</a>
							<a href="#">&gt;</a>
						</div>
					</div>
				</div>
			</div>

			<!-- 네 JS에 맞춘 수정 모달 -->
			<div class="edit_item_modal">
				<div class="edit_item_modal_popup">
					<h3 class="edit_item_modal_title">품목마스터 수정</h3>

					<div class="edit_item_form_row">
						<div class="edit_item_form_group code">
							<label>품목코드</label>
							<input class="edit_item_info readonly" type="text" value="item_001" readonly>
						</div>

						<div class="edit_item_form_group group">
							<label>품목 그룹</label>
							<select class="edit_item_info">
								<option selected>완제품</option>
								<option>반자재</option>
								<option>원자재</option>
							</select>
						</div>

						<div class="edit_item_form_group small">
							<label>규격</label>
							<select class="edit_item_info">
								<option selected>3×3</option>
								<option>5×5</option>
							</select>
						</div>

						<div class="edit_item_form_group small">
							<label>용액</label>
							<select class="edit_item_info">
								<option selected>에탄올</option>
								<option>이소프로판올</option>
							</select>
						</div>

						<div class="edit_item_form_group small">
							<label>단위</label>
							<select class="edit_item_info">
								<option selected>box</option>
								<option>ea</option>
							</select>
						</div>
					</div>

					<div class="edit_item_form_row second">
						<div class="edit_item_form_group full">
							<label>품목명</label>
							<input class="edit_item_info" type="text" value="알콜솝 소(3X3) - 에탄올">
						</div>
					</div>

					<div class="edit_item_btn_area">
						<button type="button" class="edit_item_close_btn">닫기</button>
						<button type="button" class="edit_item_save_btn">수정</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/static/js/11_masterdata/item_master.js"></script>
</body>
</html>