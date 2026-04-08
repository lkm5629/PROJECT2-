//공통코드 목록
const vendorList = document.querySelectorAll('.vendor_list')
//모달창
const modal = document.querySelector('.modal')
//모달 닫기
const closeBtn = document.querySelector('.close_btn')

//거래처 목록 클릭 시
vendorList.forEach(function (vendor) {
    vendor.addEventListener('click', function () {
        console.log("클릭됨")
        //모달 블럭으로 화면에 띄움
        modal.style.display = 'block'
    });
});

//닫기 버튼 클릭 시
closeBtn.addEventListener('click', function () {
    //디스플레이 none처리
    modal.style.display = 'none'
});

//모달 창 검정 배경 클릭 시
modal.addEventListener('click', function (event) {
    // 만약 모달 검정배경을 클릭 할 시
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});

//거래처 등록
const add_btn = document.querySelector('.add-btn')//거래처 등록 버튼
const add_vendor_modal = document.querySelector('.add_vendor_modal')//모달창 전체
const add_vendor_close_btn = document.querySelector('.add_vendor_close_btn')//닫기
const add_vendor_save_btn = document.querySelector('.add_vendor_save_btn')//등록
//거래처 등록 버튼 누르면
add_btn.addEventListener('click', function(){
    add_vendor_modal.style.display = 'block';
})

//닫기 버튼 눌렀을 시
add_vendor_close_btn.addEventListener('click', function(){
    add_vendor_modal.style.display = 'none'
})

//등록 버튼 눌렀을 시
add_vendor_save_btn.addEventListener('click', function(){
    add_vendor_modal.style.display = 'none'
})

//모달 창 검정 배경 클릭 시
add_vendor_modal.addEventListener('click', function (event) {
    // 만약 모달 검정배경을 클릭 할 시
    if (event.target === add_vendor_modal) {
        add_vendor_modal.style.display = 'none';
    }
});

const edit_btn = document.querySelector('.edit_btn')//수정
const edit_vendor_modal = document.querySelector('.edit_vendor_modal')
//수정 버튼 클릭 시
edit_btn.addEventListener('click',function(){
    edit_vendor_modal.style.display='block'
})
