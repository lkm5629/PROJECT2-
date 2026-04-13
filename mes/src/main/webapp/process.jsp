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
<title>공정 관리</title>

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
	href="${pageContext.request.contextPath}/static/css/P11_masterdata/process.css">
</head>
<body>

	<%@ include file="/WEB-INF/views/P00_layout/header.jsp"%>

	<div class="layout_snb">
		<div class="snbContent">
			<%@ include file="/WEB-INF/views/P00_layout/snb.jsp"%>
		</div>

		<div class="content">
			<div class="process-page">
				<div class="process-top">
					<div class="process-header">
						<h1>공정 관리</h1>
						<p>생산 공정 정보를 관리합니다.</p>
					</div>

					<button type="button" class="process-primary-btn">+ 공정 흐름도 등록</button>
				</div>

				<div class="process-filter-row">
					<select class="process-select">
						<option>원단재단</option>
						<option>용액 합침</option>
						<option>개별 포장</option>
						<option>박스 포장</option>
						<option>품질 검사</option>
					</select>
				</div>

				<section class="process-card flow-card">
					<div class="process-card-header">
						<h2>공정 흐름도</h2>
					</div>

					<div class="flow-board">
						<div class="flow-top-line">
							<div class="flow-step">
								<span class="flow-badge">1단계</span>
								<strong>원단 재단</strong>
								<p>설비 2/2</p>
							</div>

							<div class="flow-arrow">→</div>

							<div class="flow-step">
								<span class="flow-badge">2단계</span>
								<strong>용액 합침</strong>
								<p>설비 1/2</p>
							</div>

							<div class="flow-arrow">→</div>

							<div class="flow-step">
								<span class="flow-badge">3단계</span>
								<strong>개별 포장</strong>
								<p>설비 2/2</p>
							</div>

							<div class="flow-arrow">→</div>

							<div class="flow-step">
								<span class="flow-badge">4단계</span>
								<strong>박스 포장</strong>
								<p>설비 1/1</p>
							</div>

							<div class="flow-arrow">→</div>
						</div>

						<div class="flow-bottom-line">
							<div class="flow-step flow-step-bottom">
								<span class="flow-badge">5단계</span>
								<strong>품질 검사</strong>
								<p>설비 1/1</p>
							</div>
						</div>
					</div>
				</section>

				<section class="process-card list-card">
					<div class="process-list-top">
						<h2>공정 목록</h2>

						<div class="process-toolbar">
							<div class="process-search">
								<input type="text" placeholder="공정명 검색..." />
							</div>

							<button type="button" class="process-primary-btn small">+ 공정 등록</button>
						</div>
					</div>

					<div class="process-table-wrap">
						<table class="process-table">
							<thead>
								<tr>
									<th>공정코드</th>
									<th>공정명</th>
									<th>설명</th>
									<th>설비 수</th>
									<th>가동현황</th>
									<th>관리</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><span class="process-code-badge">PROC001</span></td>
									<td>3×3 원단 재단</td>
									<td>부직포를 규격에 맞게 재단</td>
									<td>2대</td>
									<td>2/2</td>
									<td>
										<div class="process-action-group">
											<button type="button" class="process-icon-btn edit">&#9998;</button>
											<button type="button" class="process-icon-btn delete">&#128465;</button>
										</div>
									</td>
								</tr>
								<tr>
									<td><span class="process-code-badge">PROC002</span></td>
									<td>에탄올 용액 70ml</td>
									<td>알콜 용액을 부직포에 합침</td>
									<td>2대</td>
									<td>1/2</td>
									<td>
										<div class="process-action-group">
											<button type="button" class="process-icon-btn edit">&#9998;</button>
											<button type="button" class="process-icon-btn delete">&#128465;</button>
										</div>
									</td>
								</tr>
								<tr>
									<td><span class="process-code-badge">PROC003</span></td>
									<td>이소프로판올 용액 70ml</td>
									<td>알루미늄 포장지로 개별 포장</td>
									<td>2대</td>
									<td>2/2</td>
									<td>
										<div class="process-action-group">
											<button type="button" class="process-icon-btn edit">&#9998;</button>
											<button type="button" class="process-icon-btn delete">&#128465;</button>
										</div>
									</td>
								</tr>
								<tr>
									<td><span class="process-code-badge">PROC004</span></td>
									<td>5×5 원단 재단</td>
									<td>외부 박스에 포장</td>
									<td>1대</td>
									<td>1/1</td>
									<td>
										<div class="process-action-group">
											<button type="button" class="process-icon-btn edit">&#9998;</button>
											<button type="button" class="process-icon-btn delete">&#128465;</button>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="process-pagination">
						<a href="#">&lt;</a>
						<a href="#" class="active">1</a>
						<a href="#">2</a>
						<a href="#">3</a>
						<a href="#">4</a>
						<a href="#">5</a>
						<a href="#">&gt;</a>
					</div>
				</section>
			</div>
		</div>
	</div>

</body>
</html>

