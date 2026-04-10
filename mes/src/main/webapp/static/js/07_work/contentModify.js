window.addEventListener("load", () => {
    init();
});

let debounceTimer;

function init() {
    bind();
}

function bind() {

}

function clampNumber(el) {
    let val = el.value;

    // 빈 값 허용 (지우는 동작 방해하지 않기)
    if (val === '') return '';

    let num = Number(val);
    if (isNaN(num)) return '';

    const min = el.min !== '' ? Number(el.min) : -Infinity;
    const max = el.max !== '' ? Number(el.max) : Infinity;

    if (num < min) return min;
    if (num > max) return max;

    return num;
}