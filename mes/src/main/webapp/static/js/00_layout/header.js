window.addEventListener("DOMContentLoaded", () => {
    init_header();
})

function init_header() {
    bind_header();
}

function bind_header() {
    logoMainMove_header();
    // controlUserBtn_header();
    moveUserPage_header();
    toggleAlarm_header();
    closeAlarmBox_header();
    deleteAlarm_header();
    moveMyPage_header();
}


/* =========================
   1. 로고 → 메인 이동
========================= */
function logoMainMove_header() {
    const logo = document.querySelector(".headerLeft");

    if (!logo) return;

    logo.addEventListener("click", () => {
        location.href = "/mes/dashboard";
    });
}


/* =========================
   2. 사용자 버튼 권한 처리
========================= */
function controlUserBtn_header() {
    const userBtn = document.querySelector(".userBtn_header");

    if (!userBtn) return;

    const auth = document.body.dataset.auth;

    if (Number(auth) <= 2) {
        userBtn.style.display = "none";
    }
}


/* =========================
   2-1. 사용자관리 페이지 이동
========================= */
function moveUserPage_header() {
    const userBtn = document.querySelector(".userBtn_header");

    if (!userBtn) return;

    userBtn.addEventListener("click", () => {
        location.href = "/mes/permission";
    });
}


/* =========================
   3. 알림 토글
========================= */
function toggleAlarm_header() {
    const alarmBtn = document.querySelector(".alarmBtn_header");
    const alarmBox = document.getElementById("alarmBox_header");

    if (!alarmBtn || !alarmBox) return;

    alarmBtn.addEventListener("click", () => {
        alarmBox.style.display =
            alarmBox.style.display === "block" ? "none" : "block";
    });
}


/* =========================
   4. 알림 닫기 버튼
========================= */
function closeAlarmBox_header() {
    const closeBtn = document.getElementById("closeAlarm_header");
    const alarmBox = document.getElementById("alarmBox_header");

    if (!closeBtn || !alarmBox) return;

    closeBtn.addEventListener("click", () => {
        alarmBox.style.display = "none";
    });
}


/* =========================
   5. 알림 삭제
========================= */
function deleteAlarm_header() {
    const delBtns = document.querySelectorAll(".delAlarm_header");

    if (!delBtns.length) return;

    delBtns.forEach(btn => {
        btn.addEventListener("click", (e) => {
            const item = e.target.closest(".alarm-item_header");
            if (item) item.remove();
        });
    });
}


/* =========================
   6. 마이페이지 이동
========================= */
function moveMyPage_header() {
    const profileBtn = document.querySelector(".profile_header");

    if (!profileBtn) return;

    profileBtn.addEventListener("click", () => {
        location.href = "/mes/mypage";
    });
}
