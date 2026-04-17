const add_item_modal = document.querySelector('.add_item_modal');
const bom_add_btn = document.querySelector('.bom-add-btn');
const add_item_close_btn = document.querySelector('.add_item_close_btn');
const add_item_save_btn = document.querySelector('.add_item_save_btn');

if (bom_add_btn && add_item_modal) {
    bom_add_btn.addEventListener('click', function () {
        add_item_modal.style.display = 'flex';
    });
}

if (add_item_close_btn && add_item_modal) {
    add_item_close_btn.addEventListener('click', function () {
        add_item_modal.style.display = 'none';
    });
}

if (add_item_save_btn && add_item_modal) {
    add_item_save_btn.addEventListener('click', function () {
        add_item_modal.style.display = 'none';
    });
}

const edit_item_modal = document.querySelector('.edit_item_modal');
const bom_edit_btn = document.querySelectorAll('.bom-edit-btn');
const edit_item_id = document.getElementById('edit_item_id');
const edit_item_name = document.getElementById('edit_item_name');
const edit_g_id = document.getElementById('edit_g_id');
const edit_item_close_btn = document.querySelector('.edit_item_close_btn');

bom_edit_btn.forEach(function (btn) {
    btn.addEventListener('click', function (event) {
        event.stopPropagation();

        if (edit_item_id) {
            edit_item_id.value = btn.dataset.bomId || '';
        }
        if (edit_item_name) {
            edit_item_name.value = btn.dataset.itemName || '';
        }
        if (edit_g_id) {
            edit_g_id.value = btn.dataset.gId || '';
        }
        if (edit_item_modal) {
            edit_item_modal.style.display = 'flex';
        }
    });
});

if (edit_item_close_btn && edit_item_modal) {
    edit_item_close_btn.addEventListener('click', function () {
        edit_item_modal.style.display = 'none';
    });
}

const add_bom_id = document.getElementById('add_bom_id');
const add_item_name = document.getElementById('add_item_name');
const next_bom_id = document.getElementById('next_bom_id');

function updateBomId() {
    if (!add_bom_id || !add_item_name || !next_bom_id) {
        return;
    }

    const itemName = add_item_name.value.trim();

    if (itemName === '') {
        add_bom_id.value = '';
        return;
    }

    add_bom_id.value = next_bom_id.value;
}

if (add_item_name) {
    add_item_name.addEventListener('input', updateBomId);
}
