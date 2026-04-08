window.addEventListener("DOMContentLoaded", () => {
    setLayoutHeight_snb();
    window.addEventListener("resize", setLayoutHeight_snb);
    init_snb();
});

function init_snb() {
	bind_snb();
}

function bind_snb() {
	moveMenu_snb();
}

function setLayoutHeight_snb() {
    const header = document.querySelector(".headerContainer");
    const layout = document.querySelector(".layout_snb");

    if (!header || !layout) return;

    const headerHeight = header.offsetHeight;

    layout.style.height = `calc(100vh - ${headerHeight}px)`;
    
    console.log(header)
    console.log(layout)
    console.log(headerHeight)
    console.log(layout.style.height)
}

function moveMenu_snb() {
    const menus = document.querySelectorAll(".menu-label_snb");

    if (!menus.length) return;

    menus.forEach(menu => {
        menu.addEventListener("click", () => {
            const id = menu.id;

            switch (id) {
                case "dashboard_snb":
                    location.href = "/mes/dashboard";
                    break;

                case "plan_snb":
                    location.href = "/mes/plan";
                    break;

                case "wo_snb":
                    location.href = "/mes/workorder";
                    break;

                case "qc_snb":
                    location.href = "/mes/qc";
                    break;

                case "stock_snb":
                    location.href = "/mes/stock";
                    break;

                case "ioIn_snb":
                    location.href = "/mes/stock/in";
                    break;

                case "ioOut_snb":
                    location.href = "/mes/stock/out";
                    break;

                case "eq_snb":
                    location.href = "/mes/equipment";
                    break;

                case "report_snb":
                    location.href = "/mes/report";
                    break;

                case "defReport_snb":
                    location.href = "/mes/report/defect";
                    break;

                case "masterData_snb":
                    location.href = "/mes/master";
                    break;

                case "notice_snb":
                    location.href = "/mes/notice";
                    break;

                case "suggestion_snb":
                    location.href = "/mes/suggestion";
                    break;

                case "mypage_snb":
                    location.href = "/mes/user/mypage";
                    break;
                    
                case "userManage_snb":
                	location.href = "/mes/user/userManage";
                	break;
            }
        });
    });
}