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




}