<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.util.*" %>

<div class="snb">

    <ul class="snb-menu">

        <li class="snb-item"><span class="menu-label" id="dashboard">대시보드</span></li>

        <li class="snb-item"><span class="menu-label" id="plan">생산관리</span></li>

        <li class="snb-item"><span class="menu-label" id="wo">작업관리</span></li>

        <li class="snb-item"><span class="menu-label" id="qc">품질관리</span></li>

        <li class="snb-item">
            <span class="menu-label" id="stock">재고관리</span>
            <ul class="snb-sub">
                <li class="snb-sub-item"><span class="menu-label" id="ioIn">입고 등록</span></li>
                <li class="snb-sub-item"><span class="menu-label" id="ioOut">출고 등록</span></li>
            </ul>
        </li>

        <li class="snb-item"><span class="menu-label" id="eq">설비관리</span></li>

        <li class="snb-item">
            <span class="menu-label" id="report">리포팅</span>
            <ul class="snb-sub">
                <li class="snb-sub-item"><span class="menu-label" id="defReport">부적합 보고서</span></li>
            </ul>
        </li>

        <li class="snb-item"><span class="menu-label" id="masterData">기준정보</span></li>

        <li class="snb-item"><span class="menu-label" id="notice">공지사항</span></li>

        <li class="snb-item"><span class="menu-label" id="suggestion">건의사항</span></li>

        <li class="snb-item"><span class="menu-label" id="mypage">마이페이지</span></li>

        <li class="snb-item"><span class="menu-label" id="userManage">사용자 관리</span></li>

    </ul>

</div>
