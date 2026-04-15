const process_modal_btn = document.querySelector('.process-primary-btn'); // 공정 단계 등록
const process_step_modal = document.querySelector('.process-step-modal');

// 공정 단계 등록 버튼 클릭 시
process_modal_btn.addEventListener('click', function() {
	console.log('클릭 됨');
	process_step_modal.style.display = 'flex';
});

// 공정명 검색 시 검색 필터
const process_search_text = document.querySelector('.process-search input');//검색창 인풋
const process_search_btn = document.querySelector('.process-primary-btn.small');//검색 버튼
const process_rows = document.querySelectorAll('.process-table tbody tr');//공정 목록 줄들

process_search_btn.addEventListener('click', function() {
	const keyword = process_search_text.value.trim();

	process_rows.forEach(function(row) {
		const processNameCell = row.querySelector('td:nth-child(2)');
		const processNameText = processNameCell.textContent.trim();

		if (keyword === "" || processNameText.includes(keyword)) {
			row.style.display = "";
		} else {
			row.style.display = "none";
		}
	});
});

//공정 취소 클릭 시
const process_step_cancel_btn = document.querySelector('.process-step-cancel-btn');
process_step_cancel_btn.addEventListener('click',function(){
	process_step_modal.style.display = 'none';
});

//공정 수정 클릭 시
const process_edit_modal = document.querySelector('.process-edit-modal');
const process_icon_btn_edit = document.querySelectorAll('.process-table .process-icon-btn.edit');

const editProcessId = document.getElementById('editProcessId');
const editProcessName = document.getElementById('editProcessName');
const editProcessInfo = document.getElementById('editProcessInfo');
const editProcessModalTitleText = document.getElementById('editProcessModalTitleText');
const closeProcessEditModal = document.getElementById('closeProcessEditModal');

process_icon_btn_edit.forEach(function(btn) {
	btn.addEventListener('click', function() {
		editProcessId.value = btn.dataset.processId;
		editProcessName.value = btn.dataset.processName;
		editProcessInfo.value = btn.dataset.processInfo;
		editProcessModalTitleText.textContent = btn.dataset.processName;

		process_edit_modal.style.display = 'flex';
	});
});

closeProcessEditModal.addEventListener('click', function() {
	process_edit_modal.style.display = 'none';
});
