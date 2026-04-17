<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.util.*" %>



<div class="snb">

    <ul class="snb-menu">

        <li class="snb-item"><span class="menu-label_snb hover" id="dashboard_snb">대시보드</span></li>

        <li class="snb-item"><span class="menu-label_snb hover" id="plan_snb">생산관리</span></li>

        <li class="snb-item"><span class="menu-label_snb hover" id="wo_snb">작업관리</span></li>

        <li class="snb-item"><span class="menu-label_snb hover" id="qc_snb">품질관리</span></li>

        <li class="snb-item"><span class="menu-label_snb hover" id="eq_snb">설비관리</span></li>

        <li class="snb-item">
            <span class="menu-label_snb hover" id="stock_snb">재고관리</span>
        </li>
        
        <li class="snb-item">
        	<span class="menu-label_snb hover" id="io_snb">입출고 관리</span>
        </li>

        <li class="snb-item">
            <span class="menu-label_snb hover" id="report_snb">리포팅</span>
            <ul class="snb-sub">
                <li class="snb-sub-item"><span class="menu-label_snb hover" id="defReport_snb">부적합 보고서</span></li>
            </ul>
        </li>

        <li class="snb-item"><span class="menu-label_snb" id="masterData_snb">기준정보</span>
        	<ul class="snb-sub">
                <li class="snb-sub-item"><span class="menu-label_snb hover" id="process_snb">공정관리</span></li>
                <li class="snb-sub-item"><span class="menu-label_snb hover" id="itemMaster_snb">품목마스터</span></li>
                <li class="snb-sub-item"><span class="menu-label_snb hover" id="BOM_snb">BOM 관리</span></li>
                <li class="snb-sub-item"><span class="menu-label_snb hover" id="vendor_snb">거래처관리</span></li>
            </ul>
        </li>

        <li class="snb-item"><span class="menu-label_snb hover" id="notice_snb">공지사항</span></li>

        <li class="snb-item"><span class="menu-label_snb hover" id="suggestion_snb">건의사항</span></li>

        <li class="snb-item"><span class="menu-label_snb hover" id="mypage_snb">마이페이지</span></li>

        <li class="snb-item" <c:if test="${(empty dto.auth) || dto.auth < 3}">style="display: none;"</c:if> >
        	<span class="menu-label_snb hover" id="userManage_snb">사용자 관리</span>
        	<ul class="snb-sub">
                <li class="snb-sub-item"><span class="menu-label_snb hover" id="join_snb">회원 추가</span></li>
            </ul>
        </li>

    </ul>

</div>




<!-- 모바일 -->
<div class="mobile-menu-overlay" id="mobileMenuOverlay">
    <div class="mobile-menu-panel">
        <div class="mobile-menu-header">
            <strong>메뉴</strong>
            <button type="button" id="mobileMenuClose" class="buttonSub">닫기</button>
        </div>

        <ul class="mobile-menu-list">
            <li><a href="/mes/dashboard">대시보드</a></li>
            <li><a href="/mes/prod">생산관리</a></li>
            <li><a href="/mes/worklist">작업관리</a></li>
            <li><a href="/mes/qclist">품질관리</a></li>
            <li><a href="/mes/equipment">설비관리</a></li>
            <li><a href="/mes/stock">재고관리</a></li>
            <li><a href="/mes/io">입출고관리</a></li>
            <li>
<!--                 <span>리포팅</span> -->
                <a href="/mes/reporting">리포팅</a>
                <ul>
                    <li><a href="/mes/defectreporting">부적합 보고서</a></li>
                </ul>
            </li>
            <li>
            	<span>기준정보</span>
            	<ul>
                    <li><a href="/mes/process">공정관리</a></li>
                    <li><a href="/mes/itemmaster">품목마스터</a></li>
                    <li><a href="/mes/bom">BOM 관리</a></li>
                    <li><a href="/mes/vendor">거래처관리</a></li>
                </ul>
            </li>
            <li><a href="/mes/notice">공지사항</a></li>
            <li><a href="/mes/suggestion">건의사항</a></li>
            <li><a href="/mes/mypage">마이페이지</a></li>
            <c:if test="${!(empty dto.auth) || dto.auth >= 3}">
            	<li>
            		<a href="/mes/permission">사용자 관리</a>
            		<ul>
	                    <li><a href="/mes/join">회원 추가</a></li>
	                </ul>
            	</li>
            </c:if> 
        </ul>
    </div>
</div>

<div class="mobile-fab-wrap">
    <button type="button" class="mobile-fab" id="scrollTopBtn">TOP</button>
    <button type="button" class="mobile-fab" id="mobileMenuBtn">메뉴</button>
</div>