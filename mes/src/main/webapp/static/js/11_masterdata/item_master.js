const edit = document.querySelectorAll('.icon-btn.edit'); // 수정 아이콘
const edit_item_modal = document.querySelector('.edit_item_modal');
const edit_item_close_btn = document.querySelector('.edit_item_close_btn'); // 닫기 버튼
const edit_item_save_btn = document.querySelector('.edit_item_save_btn'); // 수정 버튼

// 수정 아이콘(여러 개) 클릭 시 수정 모달 띄움
edit.forEach(function(btn){
    btn.addEventListener('click', function(){
        edit_item_modal.style.display = 'flex';
    });
});

// 수정 모달 안 수정 버튼 클릭 시
edit_item_save_btn.addEventListener('click', function () {
    console.log("수정 클릭됨");
    edit_item_modal.style.display = 'none';
});

// 수정 모달 안 닫기 버튼 클릭 시
edit_item_close_btn.addEventListener('click', function(){
	console.log("닫기 클릭됨");
    edit_item_modal.style.display = 'none';
});

const edit_item_info = document.getElementById("edit_item_info");
const edit_item_search_btn = document.querySelector('#edit_item_search_btn')//검색 버튼

//품목 데이터 배열
const items  = 
	[
	"알콜솝 소(3X3) - 에탄올",
	"알콜솝 대(5X5) - 에탄올",
	"알콜솝 소(3X3) - 이소프로판올",
	"알콜솝 대(5X5) - 이소프로판올"
	];
 
//수정 모달안에서 검색 버튼 눌렀을 시
edit_item_search_btn.addEventListener('click',function(){
	//입력한 글자가 포함된 품목만 찾기
	const keyword = edit_item_info.value.trim();
	const filtered = items .filter(items =>{
		return items.includes(keyword)
	});
	console.log(filtered)
});

const btn_add = document.querySelector('.btn-add');//품목등록 버튼
const add_item_modal = document.querySelector('.add_item_modal');//품목등록 모달창
//품목등록 버튼을 클릭 했을 때
btn_add.addEventListener('click',function(){
	add_item_modal.style.display = 'flex';
});

