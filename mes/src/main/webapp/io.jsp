<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<title>재고관리</title>



<link rel="stylesheet" href="/mes/static/css/P00_common/common.css">

<link rel="stylesheet" href="/mes/static/css/P00_layout/header.css">
<script src="/mes/static/js/00_layout/header.js"></script>

<link rel="stylesheet" href="/mes/static/css/P00_layout/snb.css">
<script src="/mes/static/js/00_layout/snb.js"></script>

<link rel="stylesheet" href="/mes/static/css/P05_stock/stock.css">

</head>
<body>
	<%@ include file="/WEB-INF/views/P00_layout/header.jsp"%>

	<div class="layout_snb">
		<div class="snbContent">
			<%@ include file="/WEB-INF/views/P00_layout/snb.jsp"%>
		</div>
		<div class="content">
			<div class="page-wrapper">

				<!-- 헤더 -->
				<div class="page-header">
					<h1>입출고 관리</h1>
					<p>입출고 현황을 조회합니다</p>
					<button class="btn-register">+ 입출고 등록</button>
				</div>



				<!-- 필터 / 검색 -->
				<div class="filter-bar">
					<select>
						<option>입출고 분류</option>
						<option>입고</option>
						<option>출고</option>
					</select> <select>
						<option>거래처명</option>
						<option>A사</option>
						<option>B사</option>
					</select> <select>
						<option>자재 대분류</option>
						<option>완제품</option>
						<option>반제품</option>
					</select> <select>
						<option>자재 소분류</option>
						<option>알콜</option>
						<option>알콜솜</option>
					</select> <input type="text" value="작업자 검색 모달"> 기간<input type="date">
					<input type="date">
					<div class="search-wrap">
						<input type="text" placeholder="자재명 또는 자재코드로 검색하여 광색" />
						<button class="btn-search">광색</button>
					</div>
				</div>

				<!-- 테이블 -->
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
							<tr>
								<td>입출고코드1</td>
								<td>자재코드1</td>
								<td>알몰</td>
								<td>원자재</td>
								<td>LOT001</td>
								<td>1</td>
								<td>L</td>
								<td>400</td>
								<td>2025-01-01</td>
								<td>2026-12-31</td>
								<td>폐기</td>
								<td>A사</td>
								<td>
									<div class="action-btns">
										<button class="btn-edit">수정</button>
										<button class="btn-delete">삭제</button>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="pagination">
						<a href="?page=1">&laquo;</a> <a href="?page=${currentPage - 1}">&lt;</a>
						<c:forEach var="i" begin="1" end="${totalPages}">
							<a href="?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
						</c:forEach>
						<a href="?page=${currentPage + 1}">&gt;</a> <a
							href="?page=${totalPages}">&raquo;</a>
					</div>
				</div>
				<div class="modal-overlay" id="stockModal">
					<!-- 			규격, 단위는 자재 소분류 누르면 자동입력, 작업자는 로그인한 사람 이름 자동입력, 입출고일은 기본적으로 오늘이며 수정가능, 대분류 누르면 소분류 뜨는 게 결정남	             -->
					<div class="modal-box">
						<h2 class="modal-title">입출고 등록</h2>
						<form method="post" action="/mes/stockcontroller">
							<div class="modal-grid">

								<!-- 입출고 분류 -->
								<!-- 입출고 분류 -->
								<div class="modal-field">
									<label>입출고 분류</label> <select name="io_type">
										<option value="">분류 선택</option>
										<option value="0">입고</option>
										<option value="1">출고</option>
									</select>
								</div>

								<!-- 자재 대분류 -->
								<div class="modal-field">
									<label>자재 대분류</label> <select name="itemgroup_name">
										<option value="">대분류 선택</option>
										<option value="finished">완제품</option>
										<option value="semi">반제품</option>
										<option value="raw">원자재</option>
									</select>
								</div>

								<!-- 자재 소분류 -->
								<!-- 임시: 소분류를 Item_name에 담음, 추후 item_id로 변경될 수 있음 -->
								<div class="modal-field">
									<label>자재 소분류</label> <select name="item_id">
										<option value="">소분류 선택</option>
										<option value="alcohol">알콜</option>
										<option value="ethanol">에탄올</option>
									</select>
								</div>

								<!-- 규격 -->
								<div class="modal-field">
									<label>규격</label> <input type="text" name="sepc"
										placeholder="규격 입력">
								</div>

								<!-- 단위 -->
								<div class="modal-field">
									<label>단위</label> <input type="text" name="unit"
										placeholder="단위 입력">
								</div>

								<!-- 수량 -->
								<div class="modal-field">
									<label>수량</label> <input type="number" name="lot_qty" min="1"
										placeholder="수량 입력">
								</div>

								<!-- 입출고일 -->
								<div class="modal-field">
									<label>입출고일</label> <input type="date" name="io_time">
								</div>

								<!-- 유통기한 -->
								<div class="modal-field">
									<label>유통기한</label> <input type="date" name="expiry_date">
								</div>

								<!-- 입출고사유 -->
								<div class="modal-field">
									<label>입출고사유</label> <select name="io_reason">
										<option value="">입출고사유 선택</option>
										<option value="purchase">구매</option>
										<option value="sale">판매</option>
										<option value="disposal">폐기</option>
										<option value="return">반품</option>
									</select>
								</div>

								<!-- 거래처 -->
								<div class="modal-field">
									<label>거래처</label> <select name="vender_id">
										<option value="">거래처 선택</option>
										<option value="A">A사</option>
										<option value="B">B사</option>
									</select>
								</div>

								<!-- LOT 번호 -->
								<div class="modal-field">
									<label>LOT 번호</label> <input type="text" name="lot_id"
										placeholder="LOT번호 입력">
								</div>

								<!-- 작업자 -->
								<div class="modal-field">
									<label>작업자</label> <input type="text" placeholder="작업자 입력">
									<input type="hidden" name="emp_id">
								</div>
						</form>
					</div>
				</div>


				<!-- 작업공간 -->
			</div>
		</div>
	</div>
</body>
</html>