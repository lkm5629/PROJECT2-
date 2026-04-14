window.addEventListener("load", function(){
	bind()
})

function bind(){
document.querySelectorAll('.pp-link').forEach(function(td) {
    td.addEventListener('click', function() {
        location.href = '/production/detail?planId=' + this.dataset.planId;
    });
});


}