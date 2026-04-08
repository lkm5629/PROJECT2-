<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.util.*" %>

<div class="snb">

    <ul class="snb-menu">

        <li class="snb-item"><span class="menu-label_snb" id="dashboard_snb">대시보드</span></li>

        <li class="snb-item"><span class="menu-label_snb" id="plan_snb">생산관리</span></li>

        <li class="snb-item"><span class="menu-label_snb" id="wo_snb">작업관리</span></li>

        <li class="snb-item"><span class="menu-label_snb" id="qc_snb">품질관리</span></li>

        <li class="snb-item">
            <span class="menu-label_snb" id="stock_snb">재고관리</span>
            <ul class="snb-sub">
                <li class="snb-sub-item"><span class="menu-label_snb" id="ioIn_snb">입고 등록</span></li>
                <li class="snb-sub-item"><span class="menu-label_snb" id="ioOut_snb">출고 등록</span></li>
            </ul>
        </li>

        <li class="snb-item"><span class="menu-label_snb" id="eq_snb">설비관리</span></li>

        <li class="snb-item">
            <span class="menu-label_snb" id="report_snb">리포팅</span>
            <ul class="snb-sub">
                <li class="snb-sub-item"><span class="menu-label_snb" id="defReport_snb">부적합 보고서</span></li>
            </ul>
        </li>

        <li class="snb-item"><span class="menu-label_snb" id="masterData_snb">기준정보</span></li>

        <li class="snb-item"><span class="menu-label_snb" id="notice_snb">공지사항</span></li>

        <li class="snb-item"><span class="menu-label_snb" id="suggestion_snb">건의사항</span></li>

        <li class="snb-item"><span class="menu-label_snb" id="mypage_snb">마이페이지</span></li>

        <li class="snb-item"><span class="menu-label_snb" id="userManage_snb">사용자 관리</span></li>

    </ul>

</div>
