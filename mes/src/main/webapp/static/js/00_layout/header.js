window.addEventListener("load", () => {
    init();
})

function init() {
    bind();
}

function bind() {
    logoMainMove();
    // controlUserBtn();
    moveUserPage();
    toggleAlarm();
    closeAlarmBox();
    deleteAlarm();
    moveMyPage();
}


/* =========================
   1. 로고 → 메인 이동
========================= */
function logoMainMove() {
    const logo = document.querySelector(".headerLeft");

    if (!logo) return;

    logo.addEventListener("click", () => {
        location.href = "/mes"; // 필요 시 수정
    });
}


/* =========================
   2. 사용자 버튼 권한 처리
========================= */
function controlUserBtn() {
    const userBtn = document.querySelector(".userBtn");

    if (!userBtn) return;

    const auth = document.body.dataset.auth;

    if (Number(auth) <= 2) {
        userBtn.style.display = "none";
    }
}


/* =========================
   2-1. 사용자관리 페이지 이동
========================= */
function moveUserPage() {
    const userBtn = document.querySelector(".userBtn");

    if (!userBtn) return;

    userBtn.addEventListener("click", () => {
        location.href = "/mes/user/mypage"; // 필요 시 수정
    });
}


/* =========================
   3. 알림 토글
========================= */
function toggleAlarm() {
    const alarmBtn = document.querySelector(".alarmBtn");
    const alarmBox = document.getElementById("alarmBox");

    if (!alarmBtn || !alarmBox) return;

    alarmBtn.addEventListener("click", () => {
        alarmBox.style.display =
            alarmBox.style.display === "block" ? "none" : "block";
    });
}


/* =========================
   4. 알림 닫기 버튼
========================= */
function closeAlarmBox() {
    const closeBtn = document.getElementById("closeAlarm");
    const alarmBox = document.getElementById("alarmBox");

    if (!closeBtn || !alarmBox) return;

    closeBtn.addEventListener("click", () => {
        alarmBox.style.display = "none";
    });
}


/* =========================
   5. 알림 삭제
========================= */
function deleteAlarm() {
    const delBtns = document.querySelectorAll(".delAlarm");

    if (!delBtns.length) return;

    delBtns.forEach(btn => {
        btn.addEventListener("click", (e) => {
            const item = e.target.closest(".alarm-item");
            if (item) item.remove();
        });
    });
}


/* =========================
   6. 마이페이지 이동
========================= */
function moveMyPage() {
    const profileBtn = document.querySelector(".profile");

    if (!profileBtn) return;

    profileBtn.addEventListener("click", () => {
        location.href = "/mes/user/mypage"; // 필요 시 수정
    });
}
