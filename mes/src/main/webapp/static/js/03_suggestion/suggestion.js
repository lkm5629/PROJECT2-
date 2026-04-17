window.addEventListener("load", function(){
	bind();
});

function bind(){

	// ──────────────────────────────────────────
	// suggestion_register: TinyMCE 에디터
	// ──────────────────────────────────────────
	var editorEl = document.getElementById('suggestRegContent');
	if (editorEl) {
		tinymce.init({
			selector: '#suggestRegContent',
			language: 'ko_KR',
			height: 300,
			menubar: false,
			plugins: 'lists link',
			toolbar: 'bold italic underline strikethrough | forecolor backcolor | alignleft aligncenter alignright | bullist numlist | link | removeformat',
			setup: function(editor) {
				document.getElementById('suggestSubmitBtn').addEventListener('click', function(e) {
					e.preventDefault();

					if (editor.getContent({format: 'text'}).trim().length === 0) {
						alert('내용을 입력하세요.');
						return;
					}

					editor.save();
					document.getElementById('suggestRegisterForm').submit();
				});
			}
		});
	}
}