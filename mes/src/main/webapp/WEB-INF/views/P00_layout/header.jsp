<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.util.*" %>


<div class="headerContainer">
    <div class="headerLeft headerContent">
        <div class="logo_header">
            <img id="logo_header" src="/mes/static/images/logo.png">
        </div>
        <div class="pageName_header">
            MES
        </div>
    </div>

    <div class="headerRight headerContent">
        <button type="button" class="userBtn_header buttonMain">
            <svg width="18" height="29" viewBox="0 0 31 29" fill="none"
                xmlns="http://www.w3.org/2000/svg">
                <path
                    d="M10.8654 10.8654C13.5897 10.8654 15.7981 8.65694 15.7981 5.93269C15.7981 3.20844 13.5897 1 10.8654 1C8.14118 1 5.93274 3.20844 5.93274 5.93269C5.93274 8.65694 8.14118 10.8654 10.8654 10.8654Z"
                    stroke="white" stroke-width="3" stroke-linecap="round"
                    stroke-linejoin="round" />
                <path
                    d="M20.7308 27.3077H1V25.1154C1 22.4989 2.03939 19.9896 3.8895 18.1395C5.73962 16.2894 8.24892 15.25 10.8654 15.25C13.4818 15.25 15.9911 16.2894 17.8413 18.1395C19.6914 19.9896 20.7308 22.4989 20.7308 25.1154V27.3077Z"
                    stroke="white" stroke-width="3" stroke-linecap="round"
                    stroke-linejoin="round" />
                <path
                    d="M19.6346 1C20.9429 1 22.1975 1.51969 23.1226 2.44475C24.0476 3.36981 24.5673 4.62446 24.5673 5.93269C24.5673 7.24092 24.0476 8.49557 23.1226 9.42063C22.1975 10.3457 20.9429 10.8654 19.6346 10.8654"
                    stroke="white" stroke-width="3" stroke-linecap="round"
                    stroke-linejoin="round" />
                <path
                    d="M23.1426 15.666C25.0101 16.3764 26.6178 17.6371 27.7531 19.2813C28.8884 20.9255 29.4976 22.8757 29.5003 24.8737V27.3072H26.2118"
                    stroke="white" stroke-width="3" stroke-linecap="round"
                    stroke-linejoin="round" />
            </svg>

            사용자 관리
        </button>

        <button type="button" class="alarmBtn_header">
            <svg width="20" height="20" viewBox="0 0 47 47" fill="none"
                xmlns="http://www.w3.org/2000/svg">
                <path
                    d="M23.5 1.5C27.3509 1.5 31.0442 3.02978 33.7672 5.75281C36.4902 8.47584 38.02 12.1691 38.02 16.02C38.02 32.1646 43.9092 35.3462 45.5 35.3462H1.5C3.12462 35.3462 8.98 32.1308 8.98 16.02C8.98 12.1691 10.5098 8.47584 13.2328 5.75281C15.9558 3.02978 19.6491 1.5 23.5 1.5V1.5Z"
                    stroke="#000001" stroke-width="3" stroke-linecap="round"
                    stroke-linejoin="round" />
                <path
                    d="M18.4231 41.54C18.7157 42.6638 19.3728 43.6588 20.2916 44.369C21.2103 45.0792 22.3388 45.4645 23.5 45.4645C24.6613 45.4645 25.7897 45.0792 26.7085 44.369C27.6272 43.6588 28.2844 42.6638 28.5769 41.54"
                    stroke="#000001" stroke-width="3" stroke-linecap="round"
                    stroke-linejoin="round" />
            </svg>
        </button>

        <div id="alarmBox_header" class="alarm-box_header">
            <div class="alarm-header">
                <span>알림 내역</span>
                <button id="closeAlarm_header">
                    <svg width="11" height="11" viewBox="0 0 11 11" fill="none"
                        xmlns="http://www.w3.org/2000/svg">
                        <path d="M10.5 0.5L0.5 10.5" stroke="#000001" stroke-linecap="round"
                            stroke-linejoin="round" />
                        <path d="M0.5 0.5L10.5 10.5" stroke="#000001" stroke-linecap="round"
                            stroke-linejoin="round" />
                    </svg>
                </button>
            </div>

            <ul class="alarm-list_header">
                <li class="alarm-item_header">
                    <div>
                        [작업지시] 새로운 작업 내역이 업로드되었습니다.
                    </div>
                    <button class="delAlarm_header">
                        <svg width="9" height="9" viewBox="0 0 11 11" fill="none"
                            xmlns="http://www.w3.org/2000/svg">
                            <path d="M10.5 0.5L0.5 10.5" stroke="#000001" stroke-linecap="round"
                                stroke-linejoin="round" />
                            <path d="M0.5 0.5L10.5 10.5" stroke="#000001" stroke-linecap="round"
                                stroke-linejoin="round" />
                        </svg>
                    </button>
                </li>
                <li class="alarm-item_header">
                    <div>
                        [재고] 부족한 재고가 발생하였습니다.
                    </div>
                    <button class="delAlarm_header">
                        <svg width="9" height="9" viewBox="0 0 11 11" fill="none"
                            xmlns="http://www.w3.org/2000/svg">
                            <path d="M10.5 0.5L0.5 10.5" stroke="#000001" stroke-linecap="round"
                                stroke-linejoin="round" />
                            <path d="M0.5 0.5L10.5 10.5" stroke="#000001" stroke-linecap="round"
                                stroke-linejoin="round" />
                        </svg>
                    </button>
                </li>
                <li class="alarm-item_header">
                    <div>
                        [출하] 오늘 출하 일정이 있습니다.
                    </div>
                    <button class="delAlarm_header">
                        <svg width="9" height="9" viewBox="0 0 11 11" fill="none"
                            xmlns="http://www.w3.org/2000/svg">
                            <path d="M10.5 0.5L0.5 10.5" stroke="#000001" stroke-linecap="round"
                                stroke-linejoin="round" />
                            <path d="M0.5 0.5L10.5 10.5" stroke="#000001" stroke-linecap="round"
                                stroke-linejoin="round" />
                        </svg>
                    </button>
                </li>
            </ul>
        </div>

        <button type="button" class="profile_header">
            ${ dto.ename } (${ dto.empid })
        </button>
    </div>
</div>
