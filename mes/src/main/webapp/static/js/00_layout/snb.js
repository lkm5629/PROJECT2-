window.addEventListener("load", () => {
    init_snb();
});

function init_snb() {
	bind_snb();
}

function bind_snb() {
	moveMenu_snb();
	initMobileMenu();
    initScrollTop();
}

function moveMenu_snb() {
    const menus = document.querySelectorAll(".menu-label_snb");

    if (!menus.length) return;

    menus.forEach(menu => {
        menu.addEventListener("click", () => {
            const id = menu.id;

            switch (id) {
            
            	// 대시보드
                case "dashboard_snb":
                    location.href = "/mes/dashboard";
                    break;
				
				
				// 생산관리
                case "plan_snb":
                    location.href = "/mes/prod";
                    break;

                // 작업 관리
                case "wo_snb":
                    location.href = "/mes/worklist";
                    break;

                // 품질 관리
                case "qc_snb":
                    location.href = "/mes/qclist";
                    break;

                // 설비 관리
                case "eq_snb":
                    location.href = "/mes/equipment";
                    break;

                // 재고 관리
                case "stock_snb":
                    location.href = "/mes/stock";
                    break;

                // 입출고 관리
                case "io_snb":
                    location.href = "/mes/io";
                    break;

                // 리포팅
                case "report_snb":
                    location.href = "/mes/report";
                    break;

                // 부적합 보고서
                case "defReport_snb":
                    location.href = "/mes/report/defect";
                    break;

                // 기준정보
                // 공정관리
                case "process_snb":
                    location.href = "/mes/process";
                    break;

                // 품목 마스터
                case "itemMaster_snb":
                    location.href = "/mes/itemMaster";
                    break;
	
				// BOM 관리
                case "BOM_snb":
                    location.href = "/mes/BOM";
                    break;
				
				// 거래처 관리
                case "vendor_snb":
                    location.href = "/mes/vendor";
                    break;
				
				
				// 공지사항
                case "notice_snb":
                    location.href = "/mes/notice";
                    break;
				
				// 건의사항
                case "suggestion_snb":
                    location.href = "/mes/suggestion";
                    break;

				// 마이페이지
                case "mypage_snb":
                    location.href = "/mes/user/mypage";
                    break;
                    
				// 사용자 관리
                case "userManage_snb":
                	location.href = "/mes/user/userManage";
                	break;
            }
        });
    });
}

function initMobileMenu() {
    const openBtn = document.getElementById("mobileMenuBtn");
    const closeBtn = document.getElementById("mobileMenuClose");
    const overlay = document.getElementById("mobileMenuOverlay");

    // 요소 없으면 종료
    if (!openBtn || !closeBtn || !overlay) return;

    // 메뉴 열기
    openBtn.addEventListener("click", () => {
        overlay.classList.add("active");
        document.body.style.overflow = "hidden"; // 배경 스크롤 막기
    });

    // 메뉴 닫기 (닫기 버튼)
    closeBtn.addEventListener("click", () => {
        overlay.classList.remove("active");
        document.body.style.overflow = "";
    });

    // 메뉴 닫기 (바깥 클릭)
    overlay.addEventListener("click", (e) => {
        if (e.target === overlay) {
            overlay.classList.remove("active");
            document.body.style.overflow = "";
        }
    });
}

function initScrollTop() {
    const topBtn = document.getElementById("scrollTopBtn");

    if (!topBtn) return;

    topBtn.addEventListener("click", () => {
        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });
    });

}