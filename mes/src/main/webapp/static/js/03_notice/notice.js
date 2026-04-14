window.addEventListener("load", function(){
	bind()
})

function bind(){
	// notice_list.js

function searchNotice() {
    document.getElementById("searchForm").submit();
}




// notice_detail.js
 
function submitDelete() {
    if (confirm("삭제하시겠습니까?")) {
        document.getElementById("deleteForm").submit();
    }
}
}