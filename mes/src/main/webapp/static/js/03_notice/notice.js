window.addEventListener("load", function(){
	bind();
});

function bind(){

	// ──────────────────────────────────────────
	// notice_list
	// ──────────────────────────────────────────
	function searchNotice() {
		document.getElementById("searchForm").submit();
	}

	// ──────────────────────────────────────────
	// notice_detail
	// ──────────────────────────────────────────
	function submitDelete() {
		if (confirm("삭제하시겠습니까?")) {
			document.getElementById("deleteForm").submit();
		}
	}

	// ──────────────────────────────────────────
	// notice_register: TinyMCE 에디터
	// ──────────────────────────────────────────
	var editorEl = document.getElementById('noticeRegContent');
	if (editorEl) {
		tinymce.init({
			selector: '#noticeRegContent',
			language: 'ko_KR',
			height: 300,
			menubar: false,
			plugins: 'lists link',
			toolbar: 'bold italic underline strikethrough | forecolor backcolor | alignleft aligncenter alignright | bullist numlist | link | removeformat',
			setup: function(editor) {
				// 등록 버튼 클릭 시 TinyMCE 내용 저장 후 submit
				document.getElementById('noticeSubmitBtn').addEventListener('click', function(e) {
					e.preventDefault();

					var content = editor.getContent();
					if (editor.getContent({format: 'text'}).trim().length === 0) {
						alert('내용을 입력하세요.');
						return;
					}

					// TinyMCE가 textarea에 자동으로 값을 넣어주지만 명시적으로 세팅
					editor.save();
					document.getElementById('noticeRegisterForm').submit();
				});
			}
		});
	}
}