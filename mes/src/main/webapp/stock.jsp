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

	<div class="layout">
		<div class="snbContent">
			<%@ include file="/WEB-INF/views/P00_layout/snb.jsp"%>
		</div>
		<div class="content">
			<div class="page-wrapper">

				<!-- 헤더 -->
				<div class="page-header">
					<h1>재고 관리</h1>
					<p>재고 현황을 조회합니다</p>
				</div>

				<!-- 요약 카드 -->
				<div class="summary-cards">
					<div class="summary-card total">
						<span class="label">수량</span> <span class="value">숫자</span> <span
							class="label">품목</span>
					</div>
					<div class="summary-card normal">
						<span class="label">수량</span> <span class="value">숫자</span> <span
							class="label">정상재고</span>
					</div>
					<div class="summary-card shortage">
						<span class="label">수량</span> <span class="value">숫자</span> <span
							class="label">부족 재고</span>
					</div>
				</div>

				<!-- 필터 / 검색 -->
				<div class="filter-bar">
		
					
					<select>
						<option>자재 대분류</option>
						<option>완제품</option>
						<option>반제품</option>
					</select>
					<select>
						<option>자재 소분류</option>
						<option>알콜</option>
						<option>알콜솜</option>
					</select>
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
								<th>재고코드</th>
								<th>자재코드</th>
								<th>자재명</th>
								<th>분류</th>
								<th>규격</th>
								<th>단위</th>
								<th>재고</th>
								<th>안전 재고</th>
								<th>관리</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>코드1</td>
								<td>자재코드1</td>
								<td>알몰</td>
								<td>원자재</td>
								<td>1</td>
								<td>L</td>
								<td>400</td>
								<td>100</td>
								<td>
									<div class="action-btns">
										<button class="btn-edit">✏️</button>
										<button class="btn-delete">🗑</button>
									</div>
								</td>
							</tr>
							<tr>
								<td>작업ID</td>
								<td>자재코드2</td>
								<td>부직포</td>
								<td>원자재</td>
								<td>1</td>
								<td>cm</td>
								<td>580</td>
								<td>200</td>
								<td>
									<div class="action-btns">
										<button class="btn-edit">✏️</button>
										<button class="btn-delete">🗑</button>
									</div>
								</td>
							</tr>
							<tr>
								<td>작업ID</td>
								<td>자재코드3</td>
								<td>부직포</td>
								<td>원자재</td>
								<td>1</td>
								<td>cm</td>
								<td>900</td>
								<td>300</td>
								<td>
									<div class="action-btns">
										<button class="btn-edit">✏️</button>
										<button class="btn-delete">🗑</button>
									</div>
								</td>
							</tr>
							<tr>
								<td>작업ID</td>
								<td>자재코드4</td>
								<td>알뮬숑3×3</td>
								<td>완제품</td>
								<td>1</td>
								<td>L</td>
								<td>750</td>
								<td>50</td>
								<td>
									<div class="action-btns">
										<button class="btn-edit">✏️</button>
										<button class="btn-delete">🗑</button>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				


				<!-- 작업공간 -->
			</div>
		</div>
	</div>
</body>
</html>