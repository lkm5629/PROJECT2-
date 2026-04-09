window.addEventListener("load", () => {
	init();
})

function init() {
	bind();
}

function bind() {
	reset();
	addWo();
}

function reset() {
	console.log("reset 실행");
	const resetBtn = document.querySelector(".reset");
	
	resetBtn.addEventListener("click", resetPartial);
}

function resetPartial() {
	console.log("실행");
    const form = document.querySelector("form");
    
    // status 초기화 (전체보기)
    form.querySelector("select[name='status']").value = "0";

    // 날짜 초기화
    form.querySelector("input[name='startDate']").value = "";
    form.querySelector("input[name='endDate']").value = "";
    
    const keyword = form.querySelector("input[name='keyword']").value;

    location.href = `/mes/worklist`;
}

function addWo() {
	const addBtn = document.querySelector(".addBtn");
	
	addBtn.addEventListener ("click", () => {
		window.location.href = "/mes/workadd";
	})
}
