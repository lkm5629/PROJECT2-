console.log(window.itemListForCode);//DB목록

const edit = document.querySelectorAll('.icon-btn.edit'); // 수정 아이콘
const edit_item_modal = document.querySelector('.edit_item_modal');
const edit_item_close_btn = document.querySelector('.edit_item_close_btn'); // 닫기 버튼
const edit_item_save_btn = document.querySelector('.edit_item_save_btn'); // 수정 버튼

const edit_item_id = document.getElementById("edit_item_id");
const edit_item_name = document.getElementById("edit_item_name");
const edit_g_id = document.getElementById("edit_g_id");
const edit_spec = document.getElementById("edit_spec");
const edit_unit = document.getElementById("edit_unit");

//   수정 아이콘(여러 개) 클릭 시 수정 모달 띄움
edit.forEach(function(btn){
    btn.addEventListener('click', function(){
        edit_item_id.value = btn.dataset.itemId;
        edit_item_name.value = btn.dataset.itemName;
        edit_g_id.value = btn.dataset.gId;
        edit_spec.value = btn.dataset.spec;
        edit_unit.value = btn.dataset.unit;
        
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

const edit_item_info = document.getElementById("edit_item_name");
const edit_item_search_btn = document.querySelector('#edit_item_search_btn');

// 품목 데이터 배열
const items = [
    "알콜솝 소(3X3) - 에탄올",
    "알콜솝 대(5X5) - 에탄올",
    "알콜솝 소(3X3) - 이소프로판올",
    "알콜솝 대(5X5) - 이소프로판올"
];

if (edit_item_search_btn) {
    edit_item_search_btn.addEventListener('click', function(){
        const keyword = edit_item_info.value.trim();
        const filtered = items.filter(function(item){
            return item.includes(keyword);
        });
        console.log(filtered);
    });
}


const btn_add = document.querySelector('.btn-add');//품목등록 버튼
const add_item_modal = document.querySelector('.add_item_modal');//품목등록 모달창
//품목등록 버튼을 클릭 했을 때
btn_add.addEventListener('click',function(){
	add_item_modal.style.display = 'flex';
});

//사용자가 등록 모달에서 품목 그룹을 선택하고
//품목명을 입력하면
//품목코드 input에 자동으로 값이 들어가게 만들기
const add_item_id = document.getElementById('add_item_id');//품목코드
const add_item_name = document.getElementById('add_item_name');//품목명
const add_g_id = document.getElementById('add_g_id');//품목그룹
const codeItems = window.itemListForCode || []; //DB 배열 저장해둘 곳

//DB 그룹숫자를 받아서 문자열 하나를 돌려주는 함수
//매개변수: 함수가 작업할 재료를 담는 변수
function gId_change(gId){
	if(gId === '30'){
		return "fin";
	}
	if(gId === '20'){
		return "semi";
	}
	if(gId === '10'){
		return "raw";
	}
	
};

//자동으로 코드 생성 됨 함수(브라우저에서)
//품목명 읽기
//품목 그룹 읽기
//둘 중 하나 비어 있으면 코드칸 비우고 끝
//같은 그룹 찾기
//숫자 뽑기
//다음 번호 계산
//prefix 구하기
//코드 결합
//add_item_id.value에 넣기
function updateItemCode() {
	const itemName = add_item_name.value.trim();
	const gId = add_g_id.value;

	if (itemName == "") {
		add_item_id.value = "";
		return;
	}
	if (gId == "") {
		add_item_id.value = "";
		return;
	}

	const sameGroupItems = codeItems.filter(function(item){
		return String(item.gId) === String(gId);
	});

	const numbers = sameGroupItems.map(function(item){
		return parseInt(item.itemId.split('_')[1], 10);
	});

	const maxNumber = Math.max(...numbers) + 1;
	const prefix = gId_change(gId);
	const newItemId = prefix + "_" + maxNumber;

	add_item_id.value = newItemId;
}

add_item_name.addEventListener("input", updateItemCode);
add_g_id.addEventListener("change", updateItemCode);

//품목 등록 닫기
const add_item_close_btn = document.querySelector('.add_item_close_btn');//닫기
const add_item_save_btn = document.querySelector('.add_item_save_btn');
add_item_close_btn.addEventListener('click', function(){
	add_item_modal.style.display = 'none';
});
add_item_save_btn.addEventListener('click', function(){
	add_item_modal.style.display = 'none';
});