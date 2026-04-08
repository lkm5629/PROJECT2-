<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html lang="kr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="static/css/P00_common/common.css">
    <link rel="stylesheet" href="static/css/mypage.css">
</head>

<body>
    <header>
    헤더 자리.
    </header>
    <div class="dash-body">
    <div class="snb">snb</div>
    <div class="snb-bro">
    <div class="title-header">
    <h1>마이페이지</h1>
    <h7>제조 실행 시스템</h7>    
    </div>
	<div class="board-body">
	  <div class="box-type4 radius">
	  <div class="title-box">
	  <h3>사용자 정보</h3>
	  <span>기본 계정 및 계정 설정</span>
	  <form method="get" >
	  </form>
	  </div>
	  <div class="mp-tool">
	  <span>이름</span>
	  <input type="text" name="mp-name" class="input-3 radius">
	  <span>이메일</span>
	  <input type="text" name="mp-name" class="input-3 radius">
	  <span>부서</span>
	  <input type="text" name="mp-name" class="input-3 radius">
	  <span>연락처</span>
	  <input type="text" name="mp-name" class="input-3 radius">
	  <span>가입일</span>
	  <input type="text" name="mp-name" readonly="readonly" class="input-3 radius">
	  <span> </span>
	  <input type="submit" name="mp-name" value="정보 수정" class="input-3 radius buttonMain">
	  </div>
	  </div>
	  <div class="box-type4 radius">
	  <div class="title-box">
	  <h3>내 작업</h3>
	  <span>내 작업 지시서 보여주기</span>
	  </div>
	  </div>
	</div>
    </div>
    </div>


</body>

</html>