notify.notify('회원가입 폼', '회원가입 페이지입니다.');

$('#createAccount').click(function() {
	notify.notify('title', 'createAccount button is clicked');
});

$(document).ready(function() {
	$('form').validator();

});