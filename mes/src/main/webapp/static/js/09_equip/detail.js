window.addEventListener("load", ()=> {
	init();
})

function init() {
	bind();
}

function bind() {
	const statusChange = document.querySelector("#statusChange");
	
	if(statusChange) {
		statusChange.addEventListener("click", sChange);
	}
	
}

function sChange() {
	console.log("statusChange 실행");
	document.getElementById('statusForm').submit();
}