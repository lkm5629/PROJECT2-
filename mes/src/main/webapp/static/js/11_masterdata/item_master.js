const edit = document.querySelector('.icon-btn.edit');
const edit_item_modal = document.querySelector('.edit_item_modal');
//수정 아이콘 클릭 시 수정 모달 띄움
edit.addEventListener('click', function () {
    edit_item_modal.style.display = 'block';
});
