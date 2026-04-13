window.addEventListener("load", function(){
	bind()
})

function bind(){

    document.getElementById('btnSearch').addEventListener('click', function() {
        const gId     = document.getElementById('filterGId').value;
        const keyword = document.getElementById('filterKeyword').value;
        const size    = document.getElementById('size').value;
        location.href = '/mes/stock?page=1&size=' + size
            + '&filterGId=' + encodeURIComponent(gId)
            + '&filterKeyword=' + encodeURIComponent(keyword);
    });

    document.getElementById('filterGId').addEventListener('change', function() {
        document.getElementById('btnSearch').click();
    });

    document.getElementById('size').addEventListener('change', function() {
        document.getElementById('btnSearch').click();
    });

    // ========== 안전재고 수정 모달 ==========
    const safeNoModal = document.getElementById('safeNoModal');

    document.querySelectorAll('.btn-edit-safe').forEach(function(btn) {
        btn.addEventListener('click', function() {
            document.getElementById('modal_stock_id').value  = this.dataset.stockId;
            document.getElementById('modal_safe_no').value   = this.dataset.safeNo;
            
            safeNoModal.showModal();
        });
    });

    document.getElementById('btnSafeNoCancel').addEventListener('click', function() {
        safeNoModal.close();
    });

    document.getElementById('btnSafeNoSubmit').addEventListener('click', function() {
        const stockId = document.getElementById('modal_stock_id').value;
        const safeNo  = document.getElementById('modal_safe_no').value;

        if (safeNo === '' || isNaN(safeNo) || Number(safeNo) < 0) {
            alert('올바른 수량을 입력하세요.');
            return;
        }

        fetch('/mes/stock', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: 'action=updateSafeNo&stock_id=' + encodeURIComponent(stockId)
                + '&safe_no=' + encodeURIComponent(safeNo)
        })
        .then(function(res) { return res.json(); })
        .then(function(data) {
            if (data.result === 'ok') {
                safeNoModal.close();
                location.reload();
            } else {
                alert('수정에 실패했습니다.');
            }
        })
        .catch(function() {
            alert('오류가 발생했습니다.');
        });
    });




}