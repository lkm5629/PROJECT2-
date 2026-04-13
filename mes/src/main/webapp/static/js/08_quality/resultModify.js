window.addEventListener("load", () => {
	init();
})

function init() {
	bind();
}

function bind() {
	const rows = document.querySelectorAll(".defList tbody tr");

	rows.forEach(row => {
		row.addEventListener("click", () => {
			openEditModal(row);
		});
	});
}


let selectedRow = null;   // 현재 선택된 row
let modalMode = "add";    // add | edit

/* =========================
   모달
========================= */
function openModal() {
    modalMode = "add";
	selectedRow = null;

	setModalMode("add");

	document.getElementById("defect").reset();

	document.getElementById("defectModal").style.display = "flex";
}

function closeModal() {
    document.getElementById("defectModal").style.display = "none";
    
    document.getElementById("defect").reset();

	// 상태 초기화
	selectedRow = null;
	modalMode = "add";

	setModalMode("add");
}


/* =========================
   숫자 인풋
========================= */
function clampNumber(el) {
    let val = el.value;

    // 빈 값 허용 (지우는 동작 방해하지 않기)
    if (val === '') return '';

    let num = Number(val);
    if (isNaN(num)) return '';

    const min = el.min !== '' ? Number(el.min) : -Infinity;
    const max = el.max !== '' ? Number(el.max) : Infinity;

    if (num < min) return min;
    if (num > max) return max;

    return num;
}



/* =========================
   불량내역 모달 보내기 조건
========================= */
function validateAndSubmitDefectAdd() {
	const defQty = document.querySelector("#defQty").value;
	const defType = document.querySelector("#defType").value;
	const defTypeText = document.querySelector("#defType option:checked").text;
	const solution = document.querySelector("#solution").value;
	const qcId = document.querySelector("input[name='qcId']").value;
	const disposeChk = document.querySelector("#defect input[name='dispose']");
	const dispose = disposeChk.checked ? 'Y' : '';
	
	console.log(disposeChk);
	console.log("checked:", disposeChk.checked);
	
    if (!defQty) {
    	alert("불량 수량을 입력하세요");
    	return;
    }
    if (!defType) {
    	alert("불량 유형을 선택하세요");
    	return;
    }
    if (!solution) {
    	alert("조치 내용을 입력하세요");
    	return;
    }

    // AJAX 요청
	fetch("/mes/qcresultmodify", {
		method: "POST",
		headers: {
			"Content-Type": "application/x-www-form-urlencoded"
		},
		body: new URLSearchParams({
			cmd: "defectAdd",
			defQty: defQty,
			defType: defType,
			solution: solution,
			qcId: qcId,
			dispose: dispose
		})
	})
	.then(res => res.json())
	.then(data => {
		// 성공 시 테이블 반영
		addRowToTable(data.defectId, defQty, defTypeText, solution, dispose);
		
		// 실시간 반영
		updateSummary();

		// 모달 닫기
		closeModal();

		// 입력 초기화
		document.getElementById("defect").reset();
	})
	.catch(err => {
		console.error(err);
		alert("저장 실패");
	});
}

function addRowToTable(defectId, defQty, defTypeText, solution, dispose) {
	const tbody = document.querySelector(".defList tbody");

	// "내용 없음" 제거
	const emptyRow = tbody.querySelector("td[colspan]");
	if (emptyRow) {
		emptyRow.parentElement.remove();
	}

	const tr = document.createElement("tr");
	tr.setAttribute("data-id", defectId);

	tr.innerHTML = `
		<td>${defQty}</td>
		<td>${defTypeText}</td>
		<td class="defListSol">${solution}</td>
		<td>${dispose}</td>
	`;
	
	tr.addEventListener("click", () => {
		openEditModal(tr);
	});

	tbody.appendChild(tr);
}

function updateSummary() {
	const total = Number(document.getElementById("totalQty").value);

	let defSum = 0;
	let disposeSum = 0;
	let reworkSum = 0;

	document.querySelectorAll(".defList tbody tr").forEach(tr => {
		const qty = Number(tr.children[0].innerText);
		const dispose = tr.children[3].innerText.trim();

		if (!isNaN(qty)) {
			defSum += qty;

			if (dispose === 'Y') {
				disposeSum += qty;
			} else {
				reworkSum += qty;
			}
		}
	});

	const passQty = total - defSum;
	const inQty = total - disposeSum;

	// 값 반영
	document.getElementById("defSum").value = defSum;
	document.getElementById("passQty").value = passQty;
	document.getElementById("disposeQty").value = disposeSum;
	document.getElementById("reworkQty").value = reworkSum;
	document.getElementById("inQty").value = inQty;
}



/* =====================
	수정
===================== */
function openEditModal(row) {
	selectedRow = row;
	modalMode = "edit";

	setModalMode("edit");

	const defectId = row.dataset.id;
	console.log("defectId:", defectId);
	
	// row 데이터
	const qty = row.children[0].innerText;
	const typeText = row.children[1].innerText;
	const solution = row.children[2].innerText;
	const dispose = row.children[3].innerText.trim();
	
	// 모달에 값 넣기
	document.getElementById("defQty").value = qty;
	document.querySelector("#solution").value = solution;
	
	const select = document.querySelector("#defType");

	for (let option of select.options) {
		if (option.text === typeText) {
			option.selected = true;
			break;
		}
	}
	
	document.querySelector("#dispose").checked = (dispose === "Y");
	
	// 모달 열기
	document.getElementById("defectModal").style.display = "flex";
}

function updateDefect() {
	const defectId = selectedRow.dataset.id;

	const defQty = document.getElementById("defQty").value;
	const defTypeValue = document.getElementById("defType").value;
	const defTypeText = document.querySelector("#defType option:checked").text;
	const solution = document.getElementById("solution").value;
	const dispose = document.getElementById("dispose").checked ? 'Y' : '';

	fetch("/mes/qcresultmodify", {
		method: "POST",
		headers: {
			"Content-Type": "application/x-www-form-urlencoded"
		},
		body: new URLSearchParams({
			cmd: "defectUpdate",
			defectId: defectId,
			defQty: defQty,
			defType: defTypeValue,
			solution: solution,
			dispose: dispose
		})
	})
	.then(res => res.json())
	.then(data => {
		if (data.success) {
			// UI 반영
			selectedRow.children[0].innerText = defQty;
			selectedRow.children[1].innerText = defTypeText;
			selectedRow.children[2].innerText = solution;
			selectedRow.children[3].innerText = dispose;

			updateSummary();
			closeModal();
		} else {
			alert("수정 실패");
		}
	})
	.catch(err => {
		console.error(err);
		alert("에러 발생");
	});
}

function deleteDefect() {
	if (!confirm("삭제하시겠습니까?")) return;

	const defectId = selectedRow.dataset.id;

	fetch("/mes/qcresultmodify", {
		method: "POST",
		headers: {
			"Content-Type": "application/x-www-form-urlencoded"
		},
		body: new URLSearchParams({
			cmd: "defectDelete",
			defectId: defectId
		})
	})
	.then(res => res.json())
	.then(data => {
		if (data.success) {
			selectedRow.remove();

			updateSummary();
			closeModal();
		} else {
			alert("삭제 실패");
		}
	})
	.catch(err => {
		console.error(err);
		alert("에러 발생");
	});
}

function setModalMode(mode) {
	const addBtn = document.getElementById("addBtn");
	const modifyBtn = document.getElementById("defectModifyBtn");
	const deleteBtn = document.getElementById("defectDeleteBtn");

	if (mode === "add") {
		addBtn.style.display = "inline-block";
		modifyBtn.style.display = "none";
		deleteBtn.style.display = "none";
	} else {
		addBtn.style.display = "none";
		modifyBtn.style.display = "inline-block";
		deleteBtn.style.display = "inline-block";
	}
}