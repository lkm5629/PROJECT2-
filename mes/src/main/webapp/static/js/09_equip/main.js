window.addEventListener("load", () => {
	init();
})

function init() {
	bind();
}

function bind() {
	reset();
}

function reset() {
	console.log("reset 실행");
	const resetBtn = document.querySelector(".reset");
	
	resetBtn.addEventListener("click", resetPartial);
}

function resetPartial() {
    const form = document.querySelector("form");
    
    // status 초기화 (전체보기)
    form.querySelector("select[name='status']").value = "전체";

    const keyword = form.querySelector("input[name='keyword']").value;

    location.href = `/mes/equipment`;
}