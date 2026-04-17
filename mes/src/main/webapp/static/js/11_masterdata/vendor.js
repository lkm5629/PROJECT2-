document.addEventListener('DOMContentLoaded', function() {
    const btnAdd = document.querySelector('.btn-add');
    const addVendorModal = document.getElementById('addVendorModal');
    const addVendorCloseBtn = document.getElementById('cancelAddVendorModal');

    const editButtons = document.querySelectorAll('.icon-btn.edit');
    const editVendorModal = document.querySelector('.edit_vendor_modal');
    const editVendorCloseBtn = document.querySelector('.edit_vendor_close_btn');

    const editVendorId = document.getElementById('edit_vendor_id');
    const editVendorType = document.getElementById('edit_vendor_type');
    const editVendorName = document.getElementById('edit_vendor_name');
    const editPhoneNo = document.getElementById('edit_phone_no');
    const editAddr = document.getElementById('edit_addr');
    const editEmpId = document.getElementById('edit_emp_id');

    if (btnAdd && addVendorModal) {
        btnAdd.addEventListener('click', function() {
            addVendorModal.style.display = 'flex';
        });
    }

    if (addVendorCloseBtn && addVendorModal) {
        addVendorCloseBtn.addEventListener('click', function() {
            addVendorModal.style.display = 'none';
        });
    }

    editButtons.forEach(function(btn) {
        btn.addEventListener('click', function() {
            if (editVendorId) {
                editVendorId.value = btn.dataset.vendorId || '';
            }
            if (editVendorType) {
                editVendorType.value = btn.dataset.vendorType || '';
            }
            if (editVendorName) {
                editVendorName.value = btn.dataset.vendorName || '';
            }
            if (editPhoneNo) {
                editPhoneNo.value = btn.dataset.phoneNo || '';
            }
            if (editAddr) {
                editAddr.value = btn.dataset.addr || '';
            }
            if (editEmpId) {
                editEmpId.value = btn.dataset.empId || '';
            }

            if (editVendorModal) {
                editVendorModal.style.display = 'flex';
            }
        });
    });

    if (editVendorCloseBtn && editVendorModal) {
        editVendorCloseBtn.addEventListener('click', function() {
            editVendorModal.style.display = 'none';
        });
    }

    window.addEventListener('click', function(event) {
        if (addVendorModal && event.target === addVendorModal) {
            addVendorModal.style.display = 'none';
        }

        if (editVendorModal && event.target === editVendorModal) {
            editVendorModal.style.display = 'none';
        }
    });
});
