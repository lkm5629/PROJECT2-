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
<title>거래처 관리</title>

<link rel="stylesheet" href="/mes/static/css/P00_common/common.css">
<link rel="stylesheet" href="/mes/static/css/P00_layout/header.css">
<script src="/mes/static/js/00_layout/header.js"></script>

<link rel="stylesheet" href="/mes/static/css/P00_layout/snb.css">
<script src="/mes/static/js/00_layout/snb.js"></script>

<link rel="stylesheet" href="/mes/static/css/P07_work/main.css">
<link rel="stylesheet" href="/mes/static/css/P11_masterdata/vendor.css">
</head>
<body>

	<%@ include file="/WEB-INF/views/P00_layout/header.jsp"%>

	<div class="layout_snb">
		<div class="snbContent">
			<%@ include file="/WEB-INF/views/P00_layout/snb.jsp"%>
		</div>

		<div class="content">
			<div class="wrap">
				<h1 class="page-title">거래처 관리</h1>
				<p class="page-desc">공급업체 및 고객사 정보를 관리합니다.</p>

				<section class="summary-grid">
					<div class="summary-card">
						<h3>전체 거래처</h3>
						<div class="count">5</div>
					</div>
					<div class="summary-card">
						<h3>공급업체</h3>
						<div class="count">3</div>
					</div>
					<div class="summary-card">
						<h3>고객사</h3>
						<div class="count">2</div>
					</div>
				</section>

				<section class="panel">
					<div class="panel-top">
						<h2 class="panel-title">거래처 목록</h2>

						<div class="panel-actions">
							<div class="search-box">
								<span class="search-icon">🔍</span>
								<input type="text" placeholder="거래처명/담당자/연락처 검색..." />
							</div>
							<button class="add-btn">+ 거래처 등록</button>
						</div>
					</div>

					<div class="table-wrap">
						<table>
							<thead>
								<tr>
									<th>코드</th>
									<th>거래처명</th>
									<th>유형</th>
									<th>담당자</th>
									<th>취급목록</th>
								</tr>
							</thead>
							<tbody>
								<tr class="vendor_list">
									<td>V001</td>
									<td>OO부직포</td>
									<td><span class="badge supplier">공급업체</span></td>
									<td>김OO</td>
									<td>
										<div class="actions">
											<button type="button" class="icon-btn edit">✏</button>
											<button type="button" class="icon-btn delete">🗑</button>
										</div>
									</td>
								</tr>
								<tr class="vendor_list">
									<td>V002</td>
									<td>에탄올화학OO</td>
									<td><span class="badge supplier">공급업체</span></td>
									<td>이OO</td>
									<td>
										<div class="actions">
											<button type="button" class="icon-btn edit">✏</button>
											<button type="button" class="icon-btn delete">🗑</button>
										</div>
									</td>
								</tr>
								<tr class="vendor_list">
									<td>V003</td>
									<td>포장재산업OO</td>
									<td><span class="badge supplier">공급업체</span></td>
									<td>박OO</td>
									<td>
										<div class="actions">
											<button type="button" class="icon-btn edit">✏</button>
											<button type="button" class="icon-btn delete">🗑</button>
										</div>
									</td>
								</tr>
								<tr class="vendor_list">
									<td>V004</td>
									<td>OO약국체인</td>
									<td><span class="badge customer">고객사</span></td>
									<td>최OO</td>
									<td>
										<div class="actions">
											<button type="button" class="icon-btn edit">✏</button>
											<button type="button" class="icon-btn delete">🗑</button>
										</div>
									</td>
								</tr>
								<tr class="vendor_list">
									<td>V005</td>
									<td>OO병원</td>
									<td><span class="badge customer">고객사</span></td>
									<td>장OO</td>
									<td>
										<div class="actions">
											<button type="button" class="icon-btn edit">✏</button>
											<button type="button" class="icon-btn delete">🗑</button>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="pagination">
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

			<!-- 거래처 상세 모달 -->
			<div class="modal" id="detailModal">
				<div class="modal_popup">
					<h3>거래처 상세</h3>
					<br>

					<div class="modal_form_row">
						<div class="modal_form_group">
							<label>거래처 명</label>
							<input class="vendor_info" type="text" placeholder="OO부직포">
						</div>

						<div class="modal_form_group">
							<label>담당자 명</label>
							<input class="vendor_info" type="text" placeholder="김OO">
						</div>

						<div class="modal_form_group">
							<label>유형</label>
							<select class="vendor_info">
								<option>공급업체</option>
								<option>고객사</option>
							</select>
						</div>
					</div>

					<div class="modal_form_row">
						<div class="modal_form_group">
							<label>연락처</label>
							<input class="vendor_info" type="text" placeholder="041-1234-1234">
						</div>

						<div class="modal_form_group">
							<label>주소</label>
							<input class="vendor_info" type="text" placeholder="충청남도 천안시">
						</div>
					</div>

					<div class="detail_btn_area">
						<button type="button" class="close_btn">닫기</button>
						<button type="button" class="edit_btn" id="detailEditBtn">수정</button>
					</div>
				</div>
			</div>

			<!-- 거래처 등록 모달 -->
			<div class="add_vendor_modal">
				<div class="add_vendor_modal_popup">
					<h3 class="add_vendor_modal_title">거래처 등록</h3>

					<div class="add_modal_form_row">
						<div class="add_modal_form_group">
							<label>거래처 명</label>
							<input class="add_vendor_info" type="text" placeholder="거래처명을 입력하세요">
						</div>

						<div class="add_modal_form_group">
							<label>담당자 명</label>
							<input class="add_vendor_info" type="text" placeholder="담당자명을 입력하세요">
						</div>

						<div class="add_modal_form_group">
							<label>유형</label>
							<select class="add_vendor_info">
								<option>공급업체</option>
								<option>고객사</option>
							</select>
						</div>
					</div>

					<div class="add_modal_form_row">
						<div class="add_modal_form_group">
							<label>연락처</label>
							<input class="add_vendor_info" type="text" placeholder="연락처를 입력하세요">
						</div>

						<div class="add_modal_form_group">
							<label>주소</label>
							<input class="add_vendor_info" type="text" placeholder="주소를 입력하세요">
						</div>
					</div>

					<div class="add_vendor_btn_area">
						<button type="button" class="add_vendor_close_btn">닫기</button>
						<button type="button" class="add_vendor_save_btn">등록</button>
					</div>
				</div>
			</div>

			<!-- 거래처 수정 모달 -->
			<div class="edit_vendor_modal" id="editVendorModal">
				<div class="edit_vendor_modal_popup">
					<h3 class="edit_vendor_modal_title">거래처 수정</h3>

					<div class="edit_modal_form_row">
						<div class="edit_modal_form_group">
							<label>거래처 명</label>
							<input class="edit_vendor_info" type="text" value="OO부직포">
						</div>

						<div class="edit_modal_form_group">
							<label>담당자 명</label>
							<input class="edit_vendor_info" type="text" value="김OO">
						</div>

						<div class="edit_modal_form_group">
							<label>유형</label>
							<select class="edit_vendor_info">
								<option selected>공급업체</option>
								<option>고객사</option>
							</select>
						</div>
					</div>

					<div class="edit_modal_form_row">
						<div class="edit_modal_form_group">
							<label>연락처</label>
							<input class="edit_vendor_info" type="text" value="041-1234-1234">
						</div>

						<div class="edit_modal_form_group">
							<label>주소</label>
							<input class="edit_vendor_info" type="text" value="충청남도 천안시">
						</div>
					</div>

					<div class="edit_vendor_btn_area">
						<button type="button" class="edit_vendor_close_btn" id="editVendorCloseBtn">닫기</button>
						<button type="button" class="edit_vendor_save_btn" id="editVendorSaveBtn">수정</button>
					</div>
				</div>
			</div>

			<script src="/mes/static/js/11_masterdata/vendor.js"></script>
		</div>
	</div>

</body>
</html>