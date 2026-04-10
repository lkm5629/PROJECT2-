window.addEventListener('load', function() {
    bind()
})

function bind() {
    const myModal = document.getElementById('myModal')
    const btnRegister = document.querySelector(".btn-register")
    const btnCancel = document.getElementById('btnCancel')

    // 모달 열기 (오늘 날짜 세팅 포함)
    btnRegister.addEventListener('click', function(e) {
        e.preventDefault()
        e.stopPropagation()
        const today = new Date().toISOString().split('T')[0]
        document.getElementById('io_time').value = today
        myModal.showModal()
    })

    // 모달 바깥 클릭 시 닫기
    myModal.addEventListener('click', function(e) {
        const rect = myModal.getBoundingClientRect()
        const isOutside =
            e.clientX < rect.left ||
            e.clientX > rect.right ||
            e.clientY < rect.top  ||
            e.clientY > rect.bottom
        if(isOutside) myModal.close()
    })

    // 취소 버튼
    btnCancel.addEventListener('click', function() {
        myModal.close()
    })

    // 페이지당 표시 건수 변경
    const sizeSelect = document.getElementById('size')
    sizeSelect.addEventListener('change', function() {
        location.href = "/mes/stockcontroller?page=1&size=" + sizeSelect.value
    })
    
    
    const itemSelect = document.getElementById('item_id')
itemSelect.addEventListener('change', function() {
    const selected = this.options[this.selectedIndex]
    document.querySelector("[name='spec']").value = selected.dataset.spec || ''
    document.querySelector("[name='unit']").value = selected.dataset.unit || ''
})
}