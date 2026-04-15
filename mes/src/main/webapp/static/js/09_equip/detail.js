window.addEventListener("click", ()=> {
	init();
})

function init() {
	bind();
}

function bind() {
	const statusChange = document.querySelector("#statusChange");
	
	if(statusChange) {
		statusChange.addEventListener("click", statusChange);
	}
	
}

function statusChange() {
	console.log("statusChange 실행");
	document.getElementById('statusForm').submit();
}