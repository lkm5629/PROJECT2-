window.addEventListener("load", function () {
    bind();
    initDetailPage();
});

function bind() {

    /* ── size 변경 (목록 페이지에만 존재) ── */
    var sizeSelect = document.getElementById("sizeSelect");
    if (sizeSelect) {
        sizeSelect.addEventListener("change", function () {
            location.href = "list?page=1&size=" + this.value;
        });
    }

    /* ── 전체 체크박스 (목록 페이지에만 존재) ── */
    var chkAll = document.getElementById("chkAll");
    if (chkAll) {
        chkAll.addEventListener("change", function () {
            document.querySelectorAll("input[name='chk']")
                    .forEach(chk => chk.checked = this.checked);
        });
    }

    /* ── 오버레이 클릭 닫기 ── */
    document.querySelectorAll(".pp-modal-overlay").forEach(overlay => {
        overlay.addEventListener("click", function (e) {
            if (e.target === this) this.style.display = "none";
        });
    });

    /* ── 대분류 변경 → 소분류 갱신 ── */
    document.getElementById("regGroup").addEventListener("change", function () {
        const gId       = this.value;
        const subSelect = document.getElementById("regSubItem");

        subSelect.innerHTML = '<option value="">소분류 선택</option>';
        document.getElementById("regUnit").value = "";
        document.getElementById("regSpec").value = "";

        if (!gId) return;

        const items = itemDataMap[gId] || [];
        items.forEach(function (item) {
            const opt        = document.createElement("option");
            opt.value        = item.itemId;
            opt.textContent  = item.itemName;
            opt.dataset.unit = item.unit || "";
            opt.dataset.spec = item.spec || "";
            subSelect.appendChild(opt);
        });
    });

    /* ── 소분류 변경 → unit / spec 자동 세팅 ── */
    document.getElementById("regSubItem").addEventListener("change", function () {
        const selected = this.options[this.selectedIndex];
        document.getElementById("regUnit").value = selected.dataset.unit || "";
        document.getElementById("regSpec").value = selected.dataset.spec || "";
    });

    /* ── 담당자 검색 버튼 / 엔터 ── */
    document.getElementById("empSearchBtn").addEventListener("click", function () {
        fetchEmpList(document.getElementById("empSearchKeyword").value.trim(), 1);
    });
    document.getElementById("empSearchKeyword").addEventListener("keydown", function (e) {
        if (e.key === "Enter") fetchEmpList(this.value.trim(), 1);
    });

    /* ── 팝업 오버레이 클릭 닫기 ── */
    document.getElementById("empPopup").addEventListener("click", function (e) {
        if (e.target === this) closeEmpPopup();
    });

} // end bind()


/* ==========================================================
   등록 모달
   ========================================================== */
function openRegisterModal() {
    resetRegisterForm();
    document.getElementById("modalRegister").style.display = "flex";
}
function closeRegisterModal() {
    resetRegisterForm();
    document.getElementById("modalRegister").style.display = "none";
}
function resetRegisterForm() {
    document.getElementById("registerForm").reset();
    document.getElementById("regSubItem").innerHTML = '<option value="">소분류 선택</option>';
    document.getElementById("regUnit").value   = "";
    document.getElementById("regSpec").value   = "";
    document.getElementById("regEmpId").value  = "";
    document.getElementById("regEmpName").value = "";
}


/* ==========================================================
   수정 모달
   ========================================================== */
function openEditModal(planId, itemId, itemName, planQty,
                       planSdate, planEdate, status, empId, ename) {
    document.getElementById("editPlanId").value    = planId;
    document.getElementById("editQty").value       = planQty;
    document.getElementById("editStatus").value    = status;
    document.getElementById("editStartDate").value = String(planSdate).substring(0, 10);
    document.getElementById("editEndDate").value   = String(planEdate).substring(0, 10);
    setSelectValue("editProduct", itemId);
    setSelectValue("editEmp",     empId);
    document.getElementById("modalEdit").style.display = "flex";
}
function closeEditModal() {
    document.getElementById("editForm").reset();
    document.getElementById("modalEdit").style.display = "none";
}
function setSelectValue(selectId, value) {
    const sel = document.getElementById(selectId);
    for (let i = 0; i < sel.options.length; i++) {
        if (sel.options[i].value === String(value)) { sel.selectedIndex = i; break; }
    }
}


/* ==========================================================
   담당자 검색 팝업
   ========================================================== */
const EMP_PAGE_SIZE = 5;

function openEmpPopup() {
    document.getElementById("empSearchKeyword").value = "";
    document.getElementById("empPopup").style.display = "flex";
    fetchEmpList("", 1);
}
function closeEmpPopup() {
    document.getElementById("empPopup").style.display = "none";
}

function fetchEmpList(keyword, page) {
    const url = "/mes/prod/api/empSearch"
              + "?keyword=" + encodeURIComponent(keyword)
              + "&page="    + page
              + "&size="    + EMP_PAGE_SIZE;

    fetch(url)
        .then(function (res) { return res.json(); })
        .then(function (data) { renderEmpList(data, keyword, page); })
        .catch(function (err) { console.error("담당자 검색 오류:", err); });
}

function renderEmpList(data, keyword, page) {
    const tbody     = document.getElementById("empListBody");
    const pagingDiv = document.getElementById("empPaging");
    tbody.innerHTML = "";

    if (!data.list || data.list.length === 0) {
        tbody.innerHTML = '<tr><td colspan="3" class="emp-empty">검색 결과가 없습니다.</td></tr>';
        pagingDiv.innerHTML = "";
        return;
    }

    data.list.forEach(function (emp) {
        const tr     = document.createElement("tr");
        tr.className = "emp-row";
        tr.innerHTML =
            '<td>' + escHtml(emp.empId)    + '</td>' +
            '<td>' + escHtml(emp.ename)    + '</td>' +
            '<td>' + escHtml(emp.deptName) + '</td>';
        tr.addEventListener("click", function () { selectEmp(emp.empId, emp.ename); });
        tbody.appendChild(tr);
    });

    renderEmpPaging(data.totalCount, page, keyword);
}

function renderEmpPaging(total, current, keyword) {
    const pagingDiv = document.getElementById("empPaging");
    const totalPage = Math.max(1, Math.ceil(total / EMP_PAGE_SIZE));
    const section   = 5;
    const endSec    = Math.ceil(current / section) * section;
    const startSec  = endSec - section + 1;
    const realEnd   = Math.min(endSec, totalPage);

    let html = "";

    html += startSec > 1
        ? '<button class="emp-page-btn" onclick="fetchEmpList(\'' + escHtml(keyword) + '\',' + (startSec - 1) + ')">[이전]</button>'
        : '<button class="emp-page-btn" disabled>[이전]</button>';

    for (let i = startSec; i <= realEnd; i++) {
        html += i === current
            ? '<button class="emp-page-btn emp-page-btn-active">' + i + '</button>'
            : '<button class="emp-page-btn" onclick="fetchEmpList(\'' + escHtml(keyword) + '\',' + i + ')">' + i + '</button>';
    }

    html += realEnd < totalPage
        ? '<button class="emp-page-btn" onclick="fetchEmpList(\'' + escHtml(keyword) + '\',' + (realEnd + 1) + ')">[다음]</button>'
        : '<button class="emp-page-btn" disabled>[다음]</button>';

    pagingDiv.innerHTML = html;
}

function selectEmp(empId, ename) {
    document.getElementById("regEmpId").value   = empId;
    document.getElementById("regEmpName").value = ename;
    closeEmpPopup();
}

function escHtml(str) {
    if (!str) return "";
    return String(str)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;");
}

/* ==========================================================
   상세 페이지 초기화
   ========================================================== */
function initDetailPage() {
    /* 진행률 바 width 세팅 (style 속성 대신 data-width로 전달) */
    var fill = document.getElementById("dtlProgressFill");
    if (fill) {
        fill.style.width = (fill.dataset.width || 0) + "%";
    }
}

/* ==========================================================
   상세 페이지 — 수정 모달 열기/닫기
   ========================================================== */
function openEditModal() {
    document.getElementById("modalEdit").classList.remove("dtl-hidden");
    document.getElementById("modalEdit").style.display = "flex";
}
function closeEditModal() {
    document.getElementById("modalEdit").style.display = "none";
    document.getElementById("modalEdit").classList.add("dtl-hidden");
}

/* ==========================================================
   상세 페이지 — 삭제
   ========================================================== */
function deletePlan() {
    var planId = document.querySelector('#editForm input[name="planId"]').value;
    if (confirm("정말 삭제하시겠습니까?")) {
        location.href = "/mes/prod/delete?planId=" + planId;
    }
}