window.addEventListener("load", () => {
    setLayoutHeight();
    window.addEventListener("resize", setLayoutHeight);
});

function init() {
	bind();
}

function bind() {
	moveMenu();
}

function setLayoutHeight() {
    const header = document.querySelector(".headerContainer");
    const layout = document.querySelector(".layout");

    if (!header || !layout) return;

    const headerHeight = header.offsetHeight;

    layout.style.height = `calc(100vh - ${headerHeight}px)`;
}

function moveMenu() {
    const menus = document.querySelectorAll(".menu-label");

    if (!menus.length) return;

    menus.forEach(menu => {
        menu.addEventListener("click", () => {
            const id = menu.id;

            switch (id) {
                case "dashboard":
                    location.href = "/mes/dashboard";
                    break;

                case "plan":
                    location.href = "/mes/plan";
                    break;

                case "wo":
                    location.href = "/mes/workorder";
                    break;

                case "qc":
                    location.href = "/mes/qc";
                    break;

                case "stock":
                    location.href = "/mes/stock";
                    break;

                case "ioIn":
                    location.href = "/mes/stock/in";
                    break;

                case "ioOut":
                    location.href = "/mes/stock/out";
                    break;

                case "eq":
                    location.href = "/mes/equipment";
                    break;

                case "report":
                    location.href = "/mes/report";
                    break;

                case "defReport":
                    location.href = "/mes/report/defect";
                    break;

                case "masterData":
                    location.href = "/mes/master";
                    break;

                case "notice":
                    location.href = "/mes/notice";
                    break;

                case "suggestion":
                    location.href = "/mes/suggestion";
                    break;

                case "mypage":
                    location.href = "/mes/user/mypage";
                    break;
                    
                case "userManage":
                	location.href = "/mes/user/userManage";
                	break;
            }
        });
    });
}