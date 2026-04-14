<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html lang="kr">

<head>
<meta charset="EUC-KR">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link rel="stylesheet" href="/mes/static/css/P00_common/common.css">

<link rel="stylesheet" href="/mes/static/css/P00_layout/header.css">
<script src="/mes/static/js/00_layout/header.js"></script>

<link rel="stylesheet" href="/mes/static/css/P00_layout/snb.css">
<script src="/mes/static/js/00_layout/snb.js"></script>
<link rel="stylesheet" href="static/css/dashboard.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

</head>

<body>

	<%@ include file="/WEB-INF/views/P00_layout/header.jsp"%>

	<div class="layout_snb">
		<div class="snbContent">
			<%@ include file="/WEB-INF/views/P00_layout/snb.jsp"%>
		</div>
		<div class="content">


			<div class="snb-bro">
				<div class="title-box">
					<h1>알콜스왑 MES</h1>
					<h7>제조 실행 시스템</h7>
				</div>
				<div class="board-box">
				
				    <div class="box-type4 radius">						
						<div class="weather">
							
						</div>
					</div>
					
				    <div class="box-type4 radius">
						<div class="chart-1 weather" >
							<h3>품질 기준</h3>
							<h4>성상 & 포장상태 : 육안 검사 결과 (합격/불합격)</h4>
							<h4>알콜함량 & 성분분석 : 63% ~ 77%, 5mL 이상</h4>
							<h4>미생물 한도 시험 : 오염이 없어야함.</h4>
							<h4>포장밀봉상태 : 누설시험 진행</h4>
						</div>
					</div>
					
				    <div class="box-type4 radius">
						<div class="chart-1 weather">
						<h3>클린룸 상태</h3>
							<h4>미세먼지 : 452,000</h4>
							<h4>온도 : 21 도</h4>
							<h4>습도 : 53 %</h4>
							<h4>차압 : 15 Pa</h4>
						</div>
					</div>
				
					<div class="box-type1 radius">
						<h3>일별 생산 현황 (완제품별)</h3>
						<div class="chart-1">
							<canvas id="deily-pro-chart-1"></canvas>
						</div>
					</div>

					<div class="box-type1 radius">
						<div style="width: 100%; display : flex; justify-content : center;">						
						<h3 class="semi">불량 유형별 분포 (공정별)</h3>
						<div style="width : 680px;">
							<canvas id="defect-type-chart"></canvas>
						</div>
						</div>
					</div>

					<div class="box-type3 radius">
						<h3>알림</h3>
						<div class="short-box">
						</div>
					</div>
					<div class="box-type3 radius">
						<h3>공지사항</h3>
						<div class="short-box">
						<c:forEach var="n" items="${ notice }" >
	                         <div><a>${ n.boardno }  :  ${ n.title }</a></div><br>
	                    </c:forEach>
						</div>
					</div>
					<div class="box-type3 radius">
						<h3>건의사항</h3>
						<div class="short-box">
						</div>
					</div>
				</div>
			</div>


		</div>
	</div>



	<script>
	// 불량 원인 담을 배열 바구니 생성
	const defect_type = [];
	
	//db에서 받아온 리스트를 반복문으로 바구니에 차례대로 넣고있음.
	<c:forEach var="d" items="${ list }" >
	 defect_type.push("${ d.dtype_name }")
	</c:forEach>
	 
	 //이제 집계를 할 json바구니 생성
	 const counts = {};
	 
	 //반복문으로 배열 바구니에 있는걸 json에서 개수 카운팅하기
	 //불량 원인이 없으면 새로 생성하고, 있으면, 개수 1 추가하는 로직.
	 defect_type.forEach(c => {
		 counts[c] = (counts[c] || 0) +1;
	 })
	 
	 //확인.
	 console.log('defect_type : ', defect_type)
	 console.log('counts : ', counts)
	 
	 //위에 있는 차트 그려질 곳 특정
	 const defectChart = document.querySelector('#defect-type-chart').getContext('2d');;
	 
	 //차트 그리기 그릴곳, 조건들?
	 new Chart(defectChart, {
		 type: 'bar',
		 data: {
			 labels: Object.keys(counts),
			 datasets: [{
				 label : '불량 유형별 발생 건수',
				 data : Object.values(counts),
				 backgroundColor: [
					 'rgba(255, 99, 132, 0.8)', // 막대 색상들 (자동 반복됨)
	                  'rgba(54, 162, 235, 0.8)',
	                  'rgba(255, 206, 86, 0.8)',
	                  'rgba(75, 192, 192, 0.8)',
	                  'rgba(153, 102, 255, 0.8)'
				 ],
				 borderColor: 'rgba(0, 0, 0, 0.5)',
				 borderWidth: 1
			 }]
		 },
		 options: {
			 responsive: true,
			 scales: {
				 y: {
					 beginAtZero: true,
		             ticks: {stepSize: 1}
				 }
			 }
		 }
	 });
	 
	
	 
	 
	 
	 
	 //공공데이터 날씨 가져오기.
	
	window.addEventListener('load', bind);
	async function bind() {
		
		//오늘 날짜 자동 가져오기
		const today = new Date();
		// 연도 가져오기 + 숫자 문자로 바꾸기(달 가져오기(js에서는 0월부터 시작. 그래서 +1)).2자리로
		// 만들기.(5월이면05로. 2자리, 1자리면 0추가)
		const sysdate = today.getFullYear() +
		                String(today.getMonth()+1).padStart(2, '0') +
		                String(today.getDate()).padStart(2, '0');
		
	    const nyear = today.getFullYear();
	    const nmonth = String(today.getMonth()+1).padStart(2, '0');
	    const ndate = String(today.getDate()).padStart(2, '0');
		
		//현재 시간에서 1시간 빼기
		today.setHours(today.getHours()-1);
		
		//시간을 2자리 문자열로
        const hours = String(today.getHours()).padStart(2, '0');
		
		//시간을 2자리 문자열로
        const nowHours = String(today.getHours() + 2).padStart(2, '0');
		
		//기상청 형식에 맞게 뒤에 '00' 붙이기
		const base_time = hours + '00';
		
		//기상청 형식에 맞게 뒤에 '00' 붙이기
		const now_time = nowHours + '00';
		
		//공공 데이터 포털의 내 키
		const key = '3229955cd446f5d563c0d814551c748992c0192563f925e1ea342f6108780f20';
	    
		//공공 데이터 포털 주소
		let url = 'http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst';
		
		//천안시 동남구 좌표
		const nx = '63';
		const ny = '110';
		
		//각 추가사항 기입
		 url += '?serviceKey='+key;
	     url += '&numOfRows=1000';
	     url += '&pageNo=1';
	     url += '&dataType=JSON';
	     url += '&base_date='+sysdate;
	     url += '&base_time='+base_time;
	     url += '&nx='+nx;
	     url += '&ny='+ny;
	     
	     try {
	    	 //fetch 호출
	    	 const response = await fetch(url);
	    	 
	    	 if(!response.ok) {
	    		 throw new Error('네트워크 응답에 문제가 있습니다.');
	    	 }
	    	 
	    	 const data = await response.json();
	    	 
	    	 //데이터 추출
	    	 const item = data.response.body.items.item;
	    	 
	    	 let weatherResult = {
	    			 temp: '',
	    			 humidity: '',
	    			 wind: '',    			 
	    			 rain: ''   			 
	    	 }
	    	 console.log('item : ', item)
	    	 console.log('weatherResult : ', weatherResult);
	    	 
	    	 
	    	 
	    	 item.forEach(function(m, index) {
	    		 
	    		 if( now_time === m.fcstTime ) {
	    		 if (m.category === 'T1H') weatherResult.temp = m.fcstValue;
	    		 if (m.category === 'REH') weatherResult.humidity = m.fcstValue;
	    		 if (m.category === 'WSD') weatherResult.wind = m.fcstValue;
	    		 if (m.category === 'RN1') weatherResult.rain = m.fcstValue;
	    			 
	    		 }
	    	 });
	    	 
	    	 console.log('weatherResult : ', weatherResult);
	    	 
	    	 console.log(data);
	    	 //화면에 뿌려주기
	    	 
	    	 const weather = document.querySelector('.weather');
	    	 if(weather) {
	    		 weather.innerHTML = `
	    		 
	    		 <h3>현재 : \${ nyear }년 \${ nmonth }월 \${ ndate }일 \${ nowHours } 시</h3>
	    		 <h4>현재 온도 : \${ weatherResult.temp } 도</h4>
	    		 <h4>현재 습도 : \${ weatherResult.humidity } %</h4>
	    		 <h4>현재 풍속 : \${ weatherResult.wind } m/s</h4>
	    		 <h4>현재 강수량 : \(${ weatherResult.rain!= null? weatherResult.rain : '-' } ) mm</h4>
	    		 
	    		 `;
	    	 }
	    	 
	    	 console.log("천안 동남구 날씨 데이터:", weatherResult);
	    	 
	     } catch(error) {
	    	 console.log("데이터 가져오는 중 오류 발생 :", error);
	     }
	     
	}
	
    
    </script>


</body>

</html>