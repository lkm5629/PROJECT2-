<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.util.*" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>작업지시</title>

<link rel="stylesheet" href="/mes/static/css/P00_common/common.css">

<link rel="stylesheet" href="/mes/static/css/P00_layout/header.css">
<script src="/mes/static/js/00_layout/header.js"></script>

<link rel="stylesheet" href="/mes/static/css/P00_layout/snb.css">
<script src="/mes/static/js/00_layout/snb.js"></script>
    
<link rel="stylesheet" href="/mes/static/css/P07_work/main.css">

</head>
<body>

	<%@ include file="/WEB-INF/views/P00_layout/header.jsp" %>
	
	<div class="layout">
		<div class="snbContent">
			<%@ include file="/WEB-INF/views/P00_layout/snb.jsp" %>
		</div>
		<div class="content">
			<!-- 작업공간 -->
		</div>
	</div>
	
</body>
</html>