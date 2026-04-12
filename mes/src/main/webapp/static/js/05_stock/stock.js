document.addEventListener('DOMContentLoaded', function () {

   
    const inSelectWrap   = document.getElementById('in_select_wrap');
    const itemWrap       = document.getElementById('item_wrap');
    
    const itemSelect     = document.getElementById('item_id');
    const allItemOptions = Array.from(itemSelect.options).slice(1);
    
    const lotSearchModal  = document.getElementById('lotSearchModal');
const lotSearchBody   = document.getElementById('lotSearchBody');
const lotKeywordInput = document.getElementById('lotKeyword');
const empSearchModal  = document.getElementById('empSearchModal');
const empSearchBody   = document.getElementById('empSearchBody');
const empKeywordInput = document.getElementById('empKeyword');



    // ── 입고 항목 선택 → 자재/LOT 자동입력 ──────────────────
    

    // ── 대분류 변경 → 소분류 필터링 (AJAX 없이 HTML 옵션 필터) ──
    document.getElementById('g_id').addEventListener('change', function () {
        const selectedGid = this.value;
        itemSelect.innerHTML = '<option value="">소분류 선택</option>';
        document.getElementById('spec').value = '';
        document.getElementById('unit').value = '';

        if (!selectedGid) return;

        allItemOptions.forEach(function (opt) {
            if (opt.dataset.gid === selectedGid) {
                itemSelect.appendChild(opt.cloneNode(true));
            }
        });
    });

    // ── 소분류 선택 → 규격/단위 자동입력 ────────────────────
    itemSelect.addEventListener('change', function () {
        const opt = this.options[this.selectedIndex];
        document.getElementById('spec').value = opt.dataset.spec || '';
        document.getElementById('unit').value = opt.dataset.unit || '';
    });

    // 입고 등록 버튼
document.querySelector('.btn-register-in').addEventListener('click', function () {

const inReasonOptions = `
    <option value="">사유 선택</option>
    <option value="구매입">구매입</option>
    <option value="생산입">생산입</option>
    <option value="QC통과">QC통과</option>
`;
document.getElementById('modalTitle').textContent = '입고 등록';
 document.getElementById('item_id_hidden').disabled = true;  // 입고 시 비활성화
    document.getElementById('lot_id_hidden').disabled  = true;
     document.getElementById('io_reason_label').textContent = '입고사유';
    document.getElementById('io_reason').innerHTML = inReasonOptions;
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('io_time').value = today;
    document.querySelector('select[name="io_type"]').value = '0';
    inSelectWrap.style.display = 'none';
    itemWrap.style.display     = 'block';
    clearItemFields();
    document.getElementById('myModal').showModal();
});

// 출고 등록 버튼
document.querySelector('.btn-register-out').addEventListener('click', function () {

	const outReasonOptions = `
    <option value="">사유 선택</option>
    <option value="폐기">폐기</option>
    <option value="자재출">자재출</option>
    <option value="매출출">매출출</option>
    <option value="QC검사">QC검사</option>
`;
document.getElementById('modalTitle').textContent = '출고 등록';
	 document.getElementById('item_id_hidden').disabled = false; // 출고 시 활성화
    document.getElementById('lot_id_hidden').disabled  = false;
     document.getElementById('io_reason_label').textContent = '출고사유';
    document.getElementById('io_reason').innerHTML = outReasonOptions;
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('io_time').value = today;
    document.querySelector('select[name="io_type"]').value = '1';
    inSelectWrap.style.display = 'block';
    itemWrap.style.display     = 'none';
    clearItemFields();
    document.getElementById('myModal').showModal();
});

    // ── 모달 닫기 ────────────────────────────────────────────
    document.getElementById('btnCancel').addEventListener('click', function () {
        document.getElementById('myModal').close();
    });

    // ── 자재 필드 초기화 ─────────────────────────────────────
    function clearItemFields() {
        document.getElementById('item_id_hidden').value          = '';
        document.getElementById('lot_id_hidden').value           = '';
        document.getElementById('spec').value                    = '';
        document.getElementById('unit').value                    = '';
        document.getElementById('item_name_display').textContent = '';
        
    }

    // ── 페이지당 건수 변경 → 목록 재조회 ────────────────────
    document.getElementById('size').addEventListener('change', function () {
        location.href = '/mes/iocontroller?page=1&size=' + this.value;
    });
    
    // LOT 검색 팝업 열기
document.getElementById('btnLotSearch').addEventListener('click', function () {
    lotKeywordInput.value = '';
    lotSearchBody.innerHTML = '<tr><td colspan="8">검색어를 입력하세요</td></tr>';
    lotSearchModal.showModal();
});

// LOT 검색 실행
document.getElementById('btnLotKeywordSearch').addEventListener('click', function () {
    fetchLotList(lotKeywordInput.value);
});

// 엔터키로도 검색
lotKeywordInput.addEventListener('keydown', function (e) {
    if (e.key === 'Enter') fetchLotList(this.value);
});

// LOT 목록 AJAX 조회
function fetchLotList(keyword) {
    fetch('/mes/iocontroller?action=getLotList&keyword=' + encodeURIComponent(keyword))
        .then(res => res.json())
        .then(data => {
            if (data.length === 0) {
                lotSearchBody.innerHTML = '<tr><td colspan="8">검색 결과 없음</td></tr>';
                return;
            }
            lotSearchBody.innerHTML = '';
            data.forEach(function (lot) {
                const tr = document.createElement('tr');
                tr.style.cursor = 'pointer';
                tr.innerHTML =
                    '<td>' + lot.lot_id     + '</td>' +
                    '<td>' + lot.item_id    + '</td>' +
                    '<td>' + lot.item_name  + '</td>' +
                    '<td>' + lot.spec       + '</td>' +
                    '<td>' + lot.unit       + '</td>' +
                    '<td>' + lot.lot_qty    + '</td>' +
                    '<td>' + (lot.expiry_date || '-') + '</td>' +
                    '<td><button type="button" class="btn-lot-select">선택</button></td>';

                // 행의 선택 버튼 클릭 시 자동입력
          tr.querySelector('.btn-lot-select').addEventListener('click', function () {
    document.getElementById('lot_id_display').value          = lot.lot_id;
    document.getElementById('lot_id_hidden').value           = lot.lot_id;
    document.getElementById('item_id_hidden').value          = lot.item_id;
    document.getElementById('item_name_display').textContent = lot.item_name;
    document.getElementById('spec').value                    = lot.spec;
    document.getElementById('unit').value                    = lot.unit;
    document.getElementById('lot_qty').value                 = lot.lot_qty;
    lotSearchModal.close();
});
                lotSearchBody.appendChild(tr);
            });
        })
        .catch(function (err) {
            console.error('LOT 검색 실패:', err);
        });
}

// LOT 검색 팝업 닫기
document.getElementById('btnLotSearchCancel').addEventListener('click', function () {
    lotSearchModal.close();
});

// 필터 작업자 검색 버튼
document.getElementById('btnFilterEmpSearch').addEventListener('click', function () {
    empKeywordInput.value = '';
    empSearchBody.innerHTML = '<tr><td colspan="4">검색어를 입력하세요</td></tr>';
    empSearchModal.dataset.mode = 'filter';
    empSearchModal.showModal();
});

// 작업자 검색 실행
document.getElementById('btnEmpKeywordSearch').addEventListener('click', function () {
    fetchUserList(empKeywordInput.value);
});

// 엔터키로도 검색
empKeywordInput.addEventListener('keydown', function (e) {
    if (e.key === 'Enter') fetchUserList(this.value);
});

function fetchUserList(keyword) {
    fetch('/mes/iocontroller?action=getUserList&keyword=' + encodeURIComponent(keyword))
        .then(res => res.json())
        .then(data => {
            if (data.length === 0) {
                empSearchBody.innerHTML = '<tr><td colspan="4">검색 결과 없음</td></tr>';
                return;
            }
            empSearchBody.innerHTML = '';
            data.forEach(function (user) {
                const tr = document.createElement('tr');
                tr.innerHTML =
                    '<td>' + user.emp_id  + '</td>' +
                    '<td>' + user.ename   + '</td>' +
                    '<td>' + user.dept_no + '</td>' +
                    '<td><button type="button" class="btn-lot-select">선택</button></td>';

                tr.querySelector('.btn-lot-select').addEventListener('click', function () {
                    if (empSearchModal.dataset.mode === 'filter') {
                        document.getElementById('filterEmp').value   = user.ename;
                        document.getElementById('filterEmpId').value = user.emp_id;
                    } else {
                        document.getElementById('empName').value       = user.ename;
                        document.getElementById('emp_id_hidden').value = user.emp_id;
                    }
                    empSearchModal.close();
                });
                empSearchBody.appendChild(tr);
            });
        })
        .catch(function (err) {
            console.error('작업자 검색 실패:', err);
        });
}

document.getElementById('btnEmpSearchCancel').addEventListener('click', function () {
    empSearchModal.close();
});

// 검색 버튼 클릭 시 필터 파라미터 붙여서 이동
document.getElementById('btnSearch').addEventListener('click', function () {

    const params = new URLSearchParams();
    params.set('page', '1');
    params.set('size', document.getElementById('size').value);

    const ioType   = document.getElementById('filterIoType').value;
    const vendorId = document.getElementById('filterVendorId').value;
    const gId      = document.getElementById('filterGId').value;
    const itemId   = document.getElementById('filterItemId').value;
    const dateFrom = document.getElementById('filterDateFrom').value;
    const dateTo   = document.getElementById('filterDateTo').value;
    const keyword  = document.getElementById('filterKeyword').value;
    const empId = document.getElementById('filterEmpId').value;

    if (ioType)   params.set('filterIoType',   ioType);
    if (vendorId) params.set('filterVendorId', vendorId);
    if (gId)      params.set('filterGId',      gId);
    if (itemId)   params.set('filterItemId',   itemId);
    if (dateFrom) params.set('filterDateFrom', dateFrom);
    if (dateTo)   params.set('filterDateTo',   dateTo);
    if (keyword)  params.set('filterKeyword',  keyword);
    if (empId) params.set('filterEmpId', empId);
    

    location.href = '/mes/iocontroller?' + params.toString();
});

// 필터바 대분류 변경 → 소분류 필터링
const filterItemSelect    = document.getElementById('filterItemId');
const allFilterItemOptions = Array.from(filterItemSelect.options).slice(1);

document.getElementById('filterGId').addEventListener('change', function () {
    const selectedGid = this.value;
    filterItemSelect.innerHTML = '<option value="">자재 소분류</option>';
    if (!selectedGid) {
        allFilterItemOptions.forEach(opt => filterItemSelect.appendChild(opt.cloneNode(true)));
        return;
    }
    allFilterItemOptions.forEach(function (opt) {
        if (opt.dataset.gid === selectedGid) {
            filterItemSelect.appendChild(opt.cloneNode(true));
        }
    });
});

// 페이지네이션 링크에 필터 파라미터 유지는 JSP에서 처리


// 필터 작업자 검색 버튼
document.getElementById('btnFilterEmpSearch').addEventListener('click', function () {
    empKeywordInput.value = '';
    empSearchBody.innerHTML = '<tr><td colspan="4">검색어를 입력하세요</td></tr>';
    // 선택 시 필터 input에 입력되도록 콜백 구분
    empSearchModal.dataset.mode = 'filter';
    empSearchModal.showModal();
});



});