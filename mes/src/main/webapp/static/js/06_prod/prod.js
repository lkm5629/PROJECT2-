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
                    .forEach(function (chk) { chk.checked = chkAll.checked; });
        });
        /* 개별 체크박스 변경 시 chkAll 상태 갱신 */
        document.addEventListener("change", function (e) {
            if (e.target && e.target.name === "chk") {
                var all     = document.querySelectorAll("input[name='chk']");
                var checked = document.querySelectorAll("input[name='chk']:checked");
                chkAll.checked = all.length > 0 && all.length === checked.length;
            }
        });
    }

    /* ── 오버레이 클릭 닫기 ── */
    document.querySelectorAll(".pp-modal-overlay").forEach(function (overlay) {
        overlay.addEventListener("click", function (e) {
            if (e.target === this) this.style.display = "none";
        });
    });

    /* ── 대분류 변경 → 소분류 갱신 ── */
    var regGroup = document.getElementById("regGroup");
    if (regGroup) {
        regGroup.addEventListener("change", function () {
            updateSubItemOptions(this, document.getElementById("regSubItem"));
        });
    }

    /* ── 소분류 변경 → unit / spec 자동 세팅 ── */
    var regSubItem = document.getElementById("regSubItem");
    if (regSubItem) {
        regSubItem.addEventListener("change", function () {
            var selected = this.options[this.selectedIndex];
            document.getElementById("regUnit").value = selected.dataset.unit || "";
            document.getElementById("regSpec").value = selected.dataset.spec || "";
        });
    }

    /* ── 날짜 유효성: 시작일 > 종료일 방지 ── */
    bindDateValidation("regStartDate", "regEndDate");

    /* ── 담당자 검색 버튼 / 엔터 ── */
    var empSearchBtn = document.getElementById("empSearchBtn");
    if (empSearchBtn) {
        empSearchBtn.addEventListener("click", function () {
            fetchEmpList(document.getElementById("empSearchKeyword").value.trim(), 1);
        });
    }
    var empSearchKeyword = document.getElementById("empSearchKeyword");
    if (empSearchKeyword) {
        empSearchKeyword.addEventListener("keydown", function (e) {
            if (e.key === "Enter") fetchEmpList(this.value.trim(), 1);
        });
    }

    /* ── 팝업 오버레이 클릭 닫기 ── */
    var empPopup = document.getElementById("empPopup");
    if (empPopup) {
        empPopup.addEventListener("click", function (e) {
            if (e.target === this) closeEmpPopup();
        });
    }

} // end bind()


/* ==========================================================
   날짜 유효성
   ========================================================== */
function bindDateValidation(startId, endId) {
    var startEl = document.getElementById(startId);
    var endEl   = document.getElementById(endId);
    if (!startEl || !endEl) return;
    startEl.addEventListener("change", function () {
        if (endEl.value && this.value > endEl.value) {
            alert("시작일은 종료일보다 늦을 수 없습니다.");
            this.value = "";
        }
    });
    endEl.addEventListener("change", function () {
        if (startEl.value && this.value < startEl.value) {
            alert("종료일은 시작일보다 빠를 수 없습니다.");
            this.value = "";
        }
    });
}


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
    document.getElementById("regUnit").value    = "";
    document.getElementById("regSpec").value    = "";
    document.getElementById("regEmpId").value   = "";
    document.getElementById("regEmpName").value = "";
}


/* ==========================================================
   수정 모달  (list용 / detail용 통합)
   - list에서는 openEditModal(planId, itemId, ...) 형태로 호출
   - detail에서는 openEditModal() 파라미터 없이 호출
   ========================================================== */
function openEditModal(planId, itemId, itemName, planQty,
                       planSdate, planEdate, status, empId, ename) {

    var modal = document.getElementById("modalEdit");
    if (!modal) return;

    /* ── list 페이지 (파라미터 전달) ── */
    if (planId !== undefined) {
        document.getElementById("editPlanId").value    = planId;
        document.getElementById("editQty").value       = planQty;
        document.getElementById("editStatus").value    = status;
        document.getElementById("editStartDate").value = String(planSdate).substring(0, 10);
        document.getElementById("editEndDate").value   = String(planEdate).substring(0, 10);
        setSelectValue("editProduct", itemId);
        setSelectValue("editEmp",     empId);

    /* ── detail 페이지 (파라미터 없음 → DTL_ITEM_ID 사용) ── */
    } else {
        var currentItemId = (typeof DTL_ITEM_ID !== "undefined") ? DTL_ITEM_ID : "";
        if (currentItemId) {
            var foundGId = "";
            Object.keys(itemDataMap).forEach(function (gId) {
                itemDataMap[gId].forEach(function (item) {
                    if (item.itemId === String(currentItemId)) foundGId = gId;
                });
            });
            if (foundGId) {
                setSelectValue("regGroup", foundGId);
                updateSubItemOptions(
                    document.getElementById("regGroup"),
                    document.getElementById("regSubItem"),
                    currentItemId
                );
            }
        }
    }

    modal.classList.remove("dtl-hidden");
    modal.style.display = "flex";
}

function closeEditModal() {
    var modal = document.getElementById("modalEdit");
    if (!modal) return;
    var form = document.getElementById("editForm");
    if (form) form.reset();
    modal.style.display = "none";
    modal.classList.add("dtl-hidden");
}

function setSelectValue(selectId, value) {
    var sel = document.getElementById(selectId);
    if (!sel) return;
    for (var i = 0; i < sel.options.length; i++) {
        if (sel.options[i].value === String(value)) { sel.selectedIndex = i; break; }
    }
}


/* ==========================================================
   담당자 검색 팝업
   ========================================================== */
var EMP_PAGE_SIZE = 5;

function openEmpPopup() {
    document.getElementById("empSearchKeyword").value = "";
    document.getElementById("empPopup").style.display = "flex";
    fetchEmpList("", 1);
}
function closeEmpPopup() {
    document.getElementById("empPopup").style.display = "none";
}

function fetchEmpList(keyword, page) {
    var url = "/mes/prod/api/empSearch"
            + "?keyword=" + encodeURIComponent(keyword)
            + "&page="    + page
            + "&size="    + EMP_PAGE_SIZE;

    fetch(url)
        .then(function (res) { return res.json(); })
        .then(function (data) { renderEmpList(data, keyword, page); })
        .catch(function (err) { console.error("담당자 검색 오류:", err); });
}

function renderEmpList(data, keyword, page) {
    var tbody     = document.getElementById("empListBody");
    var pagingDiv = document.getElementById("empPaging");
    tbody.innerHTML = "";

    if (!data.list || data.list.length === 0) {
        tbody.innerHTML = '<tr><td colspan="3" class="emp-empty">검색 결과가 없습니다.</td></tr>';
        pagingDiv.innerHTML = "";
        return;
    }

    data.list.forEach(function (emp) {
        var tr     = document.createElement("tr");
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
    var pagingDiv = document.getElementById("empPaging");
    var totalPage = Math.max(1, Math.ceil(total / EMP_PAGE_SIZE));
    var section   = 5;
    var endSec    = Math.ceil(current / section) * section;
    var startSec  = endSec - section + 1;
    var realEnd   = Math.min(endSec, totalPage);

    var html = "";

    html += startSec > 1
        ? '<button class="emp-page-btn" onclick="fetchEmpList(\'' + escHtml(keyword) + '\',' + (startSec - 1) + ')">[이전]</button>'
        : '<button class="emp-page-btn" disabled>[이전]</button>';

    for (var i = startSec; i <= realEnd; i++) {
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
   소분류 옵션 채우기 (공통)
   ========================================================== */
function updateSubItemOptions(groupSelect, subSelect, targetItemId) {
    var gId = groupSelect.value;
    subSelect.innerHTML = '<option value="">소분류 선택</option>';
    var unitEl = document.getElementById("regUnit");
    var specEl = document.getElementById("regSpec");
    if (unitEl) unitEl.value = "";
    if (specEl) specEl.value = "";

    if (!gId) return;

    var items = (typeof itemDataMap !== "undefined") ? (itemDataMap[gId] || []) : [];
    items.forEach(function (item) {
        var opt        = document.createElement("option");
        opt.value        = item.itemId;
        opt.textContent  = item.itemName;
        opt.dataset.unit = item.unit || "";
        opt.dataset.spec = item.spec || "";
        subSelect.appendChild(opt);
    });

    if (targetItemId) {
        for (var i = 0; i < subSelect.options.length; i++) {
            if (subSelect.options[i].value === String(targetItemId)) {
                subSelect.selectedIndex = i;
                if (unitEl) unitEl.value = subSelect.options[i].dataset.unit || "";
                if (specEl) specEl.value = subSelect.options[i].dataset.spec || "";
                break;
            }
        }
    }
}


/* ==========================================================
   상세 페이지 초기화
   ========================================================== */
function initDetailPage() {
    var fill = document.getElementById("dtlProgressFill");
    if (fill) {
        fill.style.width = (fill.dataset.width || 0) + "%";
    }
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